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

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.admin.AdminApprovalAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentForeignUserProfileBinding;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.responses.users.UserProfileResponse;
import com.example.fusmobilni.viewModels.users.ForeignProfileViewModel;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForeignUserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForeignUserProfileFragment extends Fragment {

    private FragmentForeignUserProfileBinding _binding;
    private SpinnerDialogFragment _loader;
    private Long userId;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private UserProfileResponse profileResponse;
    private ForeignProfileViewModel viewModel;

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

        Call<UserProfileResponse> call = ClientUtils.userService.findUserProfile(userId);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                profileResponse = response.body();
                _loader.dismiss();
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
    }

    void initializeBlockAndReportControls() {
        ImageView overflow = _binding.overflowMenuIcon;
        overflow.setOnClickListener(v -> {
            showPopupMenu(v);
        });
    }

    private void showPopupMenu(View anchor) {
        ContextThemeWrapper themeWrapper = new ContextThemeWrapper(requireContext(), R.style.CustomPopupMenuStyle);

        PopupMenu popupMenu = new PopupMenu(themeWrapper, anchor);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.block_report_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_block) {
                Navigation.findNavController(anchor).navigate(R.id.action_to_block_form);
                popupMenu.dismiss();
                return true;

            } else if (item.getItemId() == R.id.action_report) {
                Navigation.findNavController(anchor).navigate(R.id.action_to_report_form);
                popupMenu.dismiss();
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
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

    }
}