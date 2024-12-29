package com.example.fusmobilni.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.adapters.items.reviews.ItemReviewApprovalAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentItemReviewApprovalBinding;
import com.example.fusmobilni.fragments.dialogs.FailiureDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SuccessDialogFragment;
import com.example.fusmobilni.fragments.items.reviews.OnItemReviewActionListener;
import com.example.fusmobilni.requests.items.review.AcceptanceState;
import com.example.fusmobilni.requests.items.review.ItemReviewUpdateStateRequest;
import com.example.fusmobilni.responses.items.ItemReviewResponse;
import com.example.fusmobilni.viewModels.admin.ReviewApprovalViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemReviewApprovalFragment extends Fragment {

    private FragmentItemReviewApprovalBinding _binding;
    private ReviewApprovalViewModel _viewModel;
    private ItemReviewApprovalAdapter _adapter;
    private SpinnerDialogFragment _loader;
    private FailiureDialogFragment _failure;
    private SuccessDialogFragment _success;

    public ItemReviewApprovalFragment() {
        // Required empty public constructor
    }


    private void initializeDialogs() {
        _loader = new SpinnerDialogFragment();
        _loader.setCancelable(false);
        _success = new SuccessDialogFragment();
        _failure = new FailiureDialogFragment();
    }

    public static ItemReviewApprovalFragment newInstance(String param1, String param2) {
        ItemReviewApprovalFragment fragment = new ItemReviewApprovalFragment();
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

        _binding = FragmentItemReviewApprovalBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        initializeDialogs();
        _viewModel = new ViewModelProvider(requireParentFragment()).get(ReviewApprovalViewModel.class);
        _viewModel.getItemReviews().observe(getViewLifecycleOwner(), v -> {
            _adapter = new ItemReviewApprovalAdapter(_viewModel.getItemReviews().getValue(), new OnItemReviewActionListener() {
                @Override
                public void onApprove(ItemReviewResponse review) {
                    approveReview(review);
                }

                @Override
                public void onDecline(ItemReviewResponse review) {
                    declineReview(review);
                }


            });
            _binding.itemReviewRecycler.setAdapter(_adapter);
        });
        return root;
    }

    public void approveReview(ItemReviewResponse review) {
        _loader.show(getFragmentManager(), "loading_spinner");
        ItemReviewUpdateStateRequest request = new ItemReviewUpdateStateRequest(review.getId(), AcceptanceState.ACCEPTED);
        Call<ItemReviewResponse> call = ClientUtils.itemsService.updateReviewState(request);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ItemReviewResponse> call, Response<ItemReviewResponse> response) {
                if (response.isSuccessful()) {
                    openSuccessWindow("Review approved");
                    _viewModel.removeItemReview(response.body());
                } else {
                    openFailiureWindow("Failed to approve");
                }
            }

            @Override
            public void onFailure(Call<ItemReviewResponse> call, Throwable t) {
                openFailiureWindow("Failed to approve");
            }
        });
    }

    public void declineReview(ItemReviewResponse review) {
        _loader.show(getFragmentManager(), "loading_spinner");
        ItemReviewUpdateStateRequest request = new ItemReviewUpdateStateRequest(review.getId(), AcceptanceState.DECLINED);
        Call<ItemReviewResponse> call = ClientUtils.itemsService.updateReviewState(request);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ItemReviewResponse> call, Response<ItemReviewResponse> response) {
                if (response.isSuccessful()) {
                    openSuccessWindow("Review declined");
                    _viewModel.removeItemReview(response.body());
                } else {
                    openFailiureWindow("Failed to decline");
                }
            }

            @Override
            public void onFailure(Call<ItemReviewResponse> call, Throwable t) {
                openFailiureWindow("Failed to decline");
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
}