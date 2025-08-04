package com.example.fusmobilni.fragments.events.event.reviews;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.adapters.shared.ReviewFormAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentEventReviewFormBinding;
import com.example.fusmobilni.fragments.dialogs.FailiureDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SuccessDialogFragment;
import com.example.fusmobilni.requests.events.review.EventReviewCreateRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventReviewFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventReviewFormFragment extends Fragment {
    private FragmentEventReviewFormBinding _binding;
    private String _eventName;
    private Long _eventId;
    private SpinnerDialogFragment _loader;
    private SuccessDialogFragment _success;
    private FailiureDialogFragment _failure;
    private Long _userId;
    private ReviewFormAdapter _adapter;

    public EventReviewFormFragment() {
        // Required empty public constructor
    }

    public static EventReviewFormFragment newInstance(String param1, String param2) {
        EventReviewFormFragment fragment = new EventReviewFormFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _userId = getArguments().getLong("userId");
            _eventId = getArguments().getLong("eventId");
            _eventName = getArguments().getString("eventName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _binding = FragmentEventReviewFormBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        _binding.textViewEventName.setText(_eventName);
        _adapter = new ReviewFormAdapter();
        _binding.starsRecycler.setAdapter(_adapter);
        initializeDialogs();

        _binding.submitReviewButton.setOnClickListener(v -> {
            sendReview();
        });
        return root;
    }

    void sendReview() {
        _loader.show(getFragmentManager(), "loading_spinner");
        String content = _binding.editTextTextMultiLine.getText().toString();
        EventReviewCreateRequest request = new EventReviewCreateRequest(content, _eventId, _adapter.getAmount(), _userId);
        Call<Void> call = ClientUtils.eventReviewService.submitReview(request);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {

                    openSuccessWindow("Your review has been submitted");
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {

                        Navigation.findNavController(getView()).navigateUp();
                    }, 1500);
                } else {
                    openFailureWindow("Failed to submit review");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                openFailureWindow("Failed to submit review");
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

    private void initializeDialogs() {
        _loader = new SpinnerDialogFragment();
        _loader.setCancelable(false);
        _success = new SuccessDialogFragment();
        _failure = new FailiureDialogFragment();
    }
}