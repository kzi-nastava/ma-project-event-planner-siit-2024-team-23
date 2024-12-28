package com.example.fusmobilni.fragments.admin;

import android.content.ClipData;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.adapters.admin.AdminApprovalAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentReviewApprovalBinding;
import com.example.fusmobilni.fragments.dialogs.FailiureDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SuccessDialogFragment;
import com.example.fusmobilni.responses.items.ItemReviewsResponse;
import com.example.fusmobilni.viewModels.admin.ReviewApprovalViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewApprovalFragment extends Fragment {

    private FragmentReviewApprovalBinding _binding;
    private SpinnerDialogFragment _loader;
    private FailiureDialogFragment _failure;
    private SuccessDialogFragment _success;
    private ReviewApprovalViewModel _viewModel;
    ViewPager viewPager;
    private TabLayout tabLayout;

    public ReviewApprovalFragment() {
        // Required empty public constructor
    }

    public static ReviewApprovalFragment newInstance(String param1, String param2) {
        ReviewApprovalFragment fragment = new ReviewApprovalFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _binding = FragmentReviewApprovalBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        _viewModel = new ViewModelProvider(requireParentFragment()).get(ReviewApprovalViewModel.class);
        _viewModel.setTest(true);
        initializeDialogs();
        fetchPendingReviews();
        initializeTabs();
        return root;
    }

    void initializeTabs() {
        tabLayout = _binding.tabLayout;
        viewPager = _binding.viewPager;
        ArrayList<String> arrayList = new ArrayList<>(0);
        arrayList.add("Items");
        arrayList.add("Events");

        tabLayout.setupWithViewPager(viewPager);
        prepareViewPager(viewPager, arrayList);
    }

    private void prepareViewPager(ViewPager viewPager, ArrayList<String> arrayList) {
        AdminApprovalAdapter adapter = new AdminApprovalAdapter(getParentFragmentManager());
        adapter.setContext(this.getContext());
        ItemReviewApprovalFragment fragment = new ItemReviewApprovalFragment();
        adapter.addFragment(fragment, arrayList.get(0));
        EventReviewApprovalFragment eventReviewApprovalFragment = new EventReviewApprovalFragment();
        adapter.addFragment(eventReviewApprovalFragment,arrayList.get(1));

        viewPager.setAdapter(adapter);
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

    void openFailiureWindow(String message) {
        if (_loader != null) {
            _loader.dismiss();
        }
        Bundle args = new Bundle();
        args.putString("Title", "Failiure");
        args.putString("Message", message);
        _failure.setArguments(args);
        _failure.show(getParentFragmentManager(), "failiure_dialog");
    }

    private void initializeDialogs() {
        _loader = new SpinnerDialogFragment();
        _loader.setCancelable(false);
        _success = new SuccessDialogFragment();
        _failure = new FailiureDialogFragment();
    }


    public void fetchPendingReviews() {
        _loader.show(getFragmentManager(), "loading_spinner");
        Call<ItemReviewsResponse> call = ClientUtils.itemsService.findPendingReviews();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ItemReviewsResponse> call, Response<ItemReviewsResponse> response) {
                if (!response.isSuccessful()) {
                    openFailiureWindow("Failed to load reviews");

                    return;
                }

                _viewModel.setItemReviews(response.body().getItemReviews());
                _loader.dismiss();


            }

            @Override
            public void onFailure(Call<ItemReviewsResponse> call, Throwable t) {
                openFailiureWindow("Failed to load reviews");
            }
        });
    }
}