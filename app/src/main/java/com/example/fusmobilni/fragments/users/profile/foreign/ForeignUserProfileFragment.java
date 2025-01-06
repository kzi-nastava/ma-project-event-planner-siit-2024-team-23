package com.example.fusmobilni.fragments.users.profile.foreign;

import static com.example.fusmobilni.adapters.AdapterUtils.convertToUrisFromBase64;

import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.admin.AdminApprovalAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentForeignUserProfileBinding;
import com.example.fusmobilni.fragments.communication.blocks.BlockModalFragment;
import com.example.fusmobilni.fragments.dialogs.FailiureDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SuccessDialogFragment;
import com.example.fusmobilni.interfaces.BlockDialogResultListener;
import com.example.fusmobilni.requests.communication.blocks.BlockCreateRequest;
import com.example.fusmobilni.responses.users.UserProfileResponse;
import com.example.fusmobilni.viewModels.users.ForeignProfileViewModel;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForeignUserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForeignUserProfileFragment extends Fragment implements BlockDialogResultListener {

    private FragmentForeignUserProfileBinding _binding;
    private SpinnerDialogFragment _loader;
    private Long userId;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private UserProfileResponse profileResponse;
    private ForeignProfileViewModel viewModel;

    private boolean blockState = false;
    private BlockModalFragment blockDialog;
    private SuccessDialogFragment _success;
    private FailiureDialogFragment _failure;

    public ForeignUserProfileFragment() {
        // Required empty public constructor
    }

    public static ForeignUserProfileFragment newInstance(String param1, String param2) {
        ForeignUserProfileFragment fragment = new ForeignUserProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getLong("userId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentForeignUserProfileBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        viewModel = new ViewModelProvider(getParentFragment()).get(ForeignProfileViewModel.class);
        viewModel.resetReviews();
        initializeDialogs();
        fetchUserProfile();
        return root;
    }

    public void fetchUserProfile() {
        _loader.show(requireActivity().getSupportFragmentManager(), "loading_spinner");
        CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();
        Long requesterId = (prefs != null && prefs.getUser() != null) ? prefs.getUser().getId() : null;
        Call<UserProfileResponse> call = ClientUtils.userService.findUserProfile(userId, requesterId);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                profileResponse = response.body();
                _loader.dismiss();

                blockState = profileResponse.isBlocked();
                initializeProfile();
                initializeTabs();
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {

            }
        });
    }

    void initializeProfile() {
        try {
            _binding.profilePicture.setImageURI(convertToUrisFromBase64(getContext(), profileResponse.getUserAvatar()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
        _binding.userEmail.setText(profileResponse.getEmail());
        _binding.userName.setText(profileResponse.getFirstName() + " " + profileResponse.getLastName());
        initializeBlockAndReportControls();
        shouldMainControlsBeVisible();
    }

    void initializeBlockAndReportControls() {
        ImageView overflow = _binding.overflowMenuIcon;
        overflow.setOnClickListener(v -> {
            showPopupMenu(v);
        });
        setReportAndBlockControlsVisibility();
    }

    private void shouldMainControlsBeVisible() {
        CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();

        boolean loggedIn = prefs != null && prefs.getUser() != null;
        boolean gradable = profileResponse.isGradable();
        boolean notSameUser = prefs != null && prefs.getUser() != null && !prefs.getUser().getId().equals(profileResponse.getId());

        _binding.messageButton.setVisibility(loggedIn && notSameUser ? View.VISIBLE : View.GONE);
        _binding.gradeButton.setVisibility(loggedIn && notSameUser && gradable ? View.VISIBLE : View.GONE);
    }

    private void showPopupMenu(View anchor) {
        ContextThemeWrapper themeWrapper = new ContextThemeWrapper(requireContext(), R.style.CustomPopupMenuStyle);

        PopupMenu popupMenu = new PopupMenu(themeWrapper, anchor);
        MenuInflater inflater = popupMenu.getMenuInflater();
        if (!blockState) {
            inflater.inflate(R.menu.block_report_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_block) {
                    blockDialog.show(getFragmentManager(), "block_dialog");
                    popupMenu.dismiss();
                    return true;

                } else if (item.getItemId() == R.id.action_report) {
                    Navigation.findNavController(anchor).navigate(R.id.action_to_report_form, createReportBundle());
                    popupMenu.dismiss();
                    return true;
                } else {
                    return false;
                }
            });
        } else {
            inflater.inflate(R.menu.ublock_report_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_unblock) {
                    blockDialog.show(getFragmentManager(), "block_dialog");
                    popupMenu.dismiss();
                    return true;

                } else if (item.getItemId() == R.id.action_report) {
                    Navigation.findNavController(anchor).navigate(R.id.action_to_report_form, createReportBundle());
                    popupMenu.dismiss();
                    return true;
                } else {
                    return false;
                }
            });
        }


        popupMenu.show();
    }

    private void setReportAndBlockControlsVisibility() {
        CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();
        boolean shouldBeVisible = prefs != null && prefs.getUser() != null;

        boolean notSameUser = prefs != null && prefs.getUser() != null && !prefs.getUser().getId().equals(profileResponse.getId());
        _binding.overflowMenuIcon.setVisibility(shouldBeVisible && notSameUser ? View.VISIBLE : View.GONE);
    }

    private Bundle createReportBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong("reportedId", profileResponse.getId());
        return bundle;
    }

    void initializeTabs() {
        tabLayout = _binding.profileTabs;
        viewPager = _binding.viewPager;
        ArrayList<String> arrayList = new ArrayList<>(0);
        switch (profileResponse.role) {
            case "EVENT_ORGANIZER": {
                arrayList.add("Reviews");
                arrayList.add("Event reviews");
                tabLayout.setupWithViewPager(viewPager);
                prepareViewPagerEventOrganizer(viewPager, arrayList);
                break;
            }
            case "SERVICE_PROVIDER": {
                arrayList.add("Reviews");
                arrayList.add("Item reviews");
                tabLayout.setupWithViewPager(viewPager);
                prepareViewPagerServiceProvider(viewPager, arrayList);
                break;
            }
            default: {
                arrayList.add("Reviews");
                tabLayout.setupWithViewPager(viewPager);
                prepareViewPagerBasicUser(viewPager, arrayList);
                break;
            }
        }

    }

    private void prepareViewPagerBasicUser(ViewPager viewPager, ArrayList<String> arrayList) {
        AdminApprovalAdapter adapter = new AdminApprovalAdapter(getParentFragmentManager());
        adapter.setContext(this.getContext());
        UserProfileUserReviewsFragment fragment = new UserProfileUserReviewsFragment();
        adapter.addFragment(fragment, arrayList.get(0));
        viewPager.setAdapter(adapter);
        viewModel.setUserReviews(profileResponse.getUserReviews());
    }

    private void prepareViewPagerServiceProvider(ViewPager
                                                         viewPager, ArrayList<String> arrayList) {
        AdminApprovalAdapter adapter = new AdminApprovalAdapter(getParentFragmentManager());
        adapter.setContext(this.getContext());
        UserProfileUserReviewsFragment fragment = new UserProfileUserReviewsFragment();
        adapter.addFragment(fragment, arrayList.get(0));
        UserProfileItemReviewsFragment eventReviewApprovalFragment = new UserProfileItemReviewsFragment();
        adapter.addFragment(eventReviewApprovalFragment, arrayList.get(1));

        viewPager.setAdapter(adapter);
        viewModel.setUserReviews(profileResponse.getUserReviews());
        viewModel.setItemReviews(profileResponse.getItemReviews());
    }

    private void prepareViewPagerEventOrganizer(ViewPager
                                                        viewPager, ArrayList<String> arrayList) {
        AdminApprovalAdapter adapter = new AdminApprovalAdapter(getParentFragmentManager());
        adapter.setContext(this.getContext());
        UserProfileUserReviewsFragment fragment = new UserProfileUserReviewsFragment();
        adapter.addFragment(fragment, arrayList.get(0));
        UserProfileEventReviewsFragment eventReviewApprovalFragment = new UserProfileEventReviewsFragment();
        adapter.addFragment(eventReviewApprovalFragment, arrayList.get(1));

        viewPager.setAdapter(adapter);
        viewModel.setUserReviews(profileResponse.getUserReviews());
        viewModel.setEventReviews(profileResponse.getEventReviews());
    }

    private void initializeDialogs() {
        _loader = new SpinnerDialogFragment();
        _loader.setCancelable(false);
        blockDialog = new BlockModalFragment();
        _success = new SuccessDialogFragment();
        _failure = new FailiureDialogFragment();
        blockDialog.setTargetFragment(this, 0); // Set this fragment as the target
    }

    void blockUser() {
        CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();
        if (prefs == null || prefs.getUser() == null) {
            return;
        }
        BlockCreateRequest request = new BlockCreateRequest(profileResponse.getId(), prefs.getUser().getId());
        Call<Object> call = ClientUtils.blockService.createBlock(request);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!response.isSuccessful()) {
                    openFailureWindow("Failed to block user");
                    return;
                }
                openSuccessWindow("User blocked");
                blockState = true;
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                openFailureWindow("Failed to block user");

            }
        });
    }

    void unblockUser() {
        CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();
        if (prefs == null || prefs.getUser() == null) {
            return;
        }

        Call<Object> call = ClientUtils.blockService.unblock(profileResponse.getId(), prefs.getUser().getId());
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!response.isSuccessful()) {
                    openFailureWindow("Failed to unblock user");
                    return;
                }
                openSuccessWindow("User unblocked");
                blockState = false;
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                openFailureWindow("Failed to unblock user");

            }
        });

    }

    void openSuccessWindow(String message) {
        if (_loader != null) {
            _loader.dismiss();
        }
        Bundle args = new Bundle();
        args.putString("Title", "Success");
        args.putString("Message", message);
        _success.setArguments(args);
        _success.show(getParentFragmentManager(), "success_dialog");
    }

    void openFailureWindow(String message) {
        if (_loader != null) {
            _loader.dismiss();
        }
        Bundle args = new Bundle();
        args.putString("Title", "Failiure");
        args.putString("Message", message);
        _failure.setArguments(args);
        _failure.show(getParentFragmentManager(), "failiure_dialog");
    }

    @Override
    public void onBlockDialogResult(boolean result) {
        if (result) {
            if (!blockState) {
                blockUser();
            } else {
                unblockUser();
            }
        }
    }
}