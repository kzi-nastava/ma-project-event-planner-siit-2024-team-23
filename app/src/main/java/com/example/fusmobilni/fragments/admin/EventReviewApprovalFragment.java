package com.example.fusmobilni.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.adapters.events.reviews.EventReviewApprovalAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentEventReviewApprovalBinding;
import com.example.fusmobilni.fragments.dialogs.FailiureDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SuccessDialogFragment;
import com.example.fusmobilni.fragments.events.event.reviews.OnEventReviewActionListener;
import com.example.fusmobilni.requests.events.review.AcceptanceState;
import com.example.fusmobilni.requests.events.review.EventReviewUpdateStateRequest;
import com.example.fusmobilni.responses.events.review.EventReviewResponse;
import com.example.fusmobilni.viewModels.admin.ReviewApprovalViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventReviewApprovalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventReviewApprovalFragment extends Fragment {


    private FragmentEventReviewApprovalBinding _binding;
    private ReviewApprovalViewModel _viewModel;
    private EventReviewApprovalAdapter _adapter;
    private SpinnerDialogFragment _loader;
    private FailiureDialogFragment _failure;
    private SuccessDialogFragment _success;

    public EventReviewApprovalFragment() {
        // Required empty public constructor
    }

    public static EventReviewApprovalFragment newInstance(String param1, String param2) {
        EventReviewApprovalFragment fragment = new EventReviewApprovalFragment();
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
        _binding = FragmentEventReviewApprovalBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();

        initializeDialogs();
        _viewModel = new ViewModelProvider(requireParentFragment()).get(ReviewApprovalViewModel.class);
        _viewModel.getEventReviews().observe(getViewLifecycleOwner(), v -> {
            _adapter = new EventReviewApprovalAdapter(_viewModel.getEventReviews().getValue(), new OnEventReviewActionListener() {
                @Override
                public void onApprove(EventReviewResponse review) {
                    approveReview(review);
                }

                @Override
                public void onDecline(EventReviewResponse review) {
                    declineReview(review);
                }
            });
            _binding.eventReviewRecycler.setAdapter(_adapter);
        });

        return root;
    }

    public void approveReview(EventReviewResponse review) {
        _loader.show(getFragmentManager(), "loading_spinner");
        EventReviewUpdateStateRequest request = new EventReviewUpdateStateRequest(review.getId(), AcceptanceState.ACCEPTED);
        Call<EventReviewResponse> call = ClientUtils.eventReviewService.updateEventReviewState(request);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<EventReviewResponse> call, Response<EventReviewResponse> response) {
                if (response.isSuccessful()) {
                    openSuccessWindow("Review approved");
                    _viewModel.removeEventReview(response.body());
                } else {
                    openFailiureWindow("Failed to approve");
                }
            }

            @Override
            public void onFailure(Call<EventReviewResponse> call, Throwable t) {
                openFailiureWindow("Failed to approve");
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

    public void declineReview(EventReviewResponse review) {
        _loader.show(getFragmentManager(), "loading_spinner");
        EventReviewUpdateStateRequest request = new EventReviewUpdateStateRequest(review.getId(), AcceptanceState.DECLINED);
        Call<EventReviewResponse> call = ClientUtils.eventReviewService.updateEventReviewState(request);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<EventReviewResponse> call, Response<EventReviewResponse> response) {
                if (response.isSuccessful()) {
                    openSuccessWindow("Review declined");
                    _viewModel.removeEventReview(response.body());
                } else {
                    openFailiureWindow("Failed to decline");
                }
            }

            @Override
            public void onFailure(Call<EventReviewResponse> call, Throwable t) {
                openFailiureWindow("Failed to decline");
            }
        });
    }

    private void initializeDialogs() {
        _loader = new SpinnerDialogFragment();
        _loader.setCancelable(false);
        _success = new SuccessDialogFragment();
        _failure = new FailiureDialogFragment();
    }

}