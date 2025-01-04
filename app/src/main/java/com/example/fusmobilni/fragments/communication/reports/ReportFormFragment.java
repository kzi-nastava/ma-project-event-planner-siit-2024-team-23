package com.example.fusmobilni.fragments.communication.reports;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentReportFormBinding;
import com.example.fusmobilni.fragments.dialogs.FailiureDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SuccessDialogFragment;
import com.example.fusmobilni.requests.communication.reports.CreateReportRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportFormFragment extends Fragment {


    private FragmentReportFormBinding _binding;
    private Long reportedId;

    private SpinnerDialogFragment _loader;
    private SuccessDialogFragment _success;
    private FailiureDialogFragment _failure;

    public ReportFormFragment() {
        // Required empty public constructor
    }

    public static ReportFormFragment newInstance(String param1, String param2) {
        ReportFormFragment fragment = new ReportFormFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reportedId = getArguments().getLong("reportedId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentReportFormBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        initializeDialogs();
        _binding.submitReviewButton.setOnClickListener(v -> {
            sendReport();
        });
        return root;
    }

    private void sendReport() {
        CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();
        if (prefs == null || prefs.getUser() == null) {
            return;
        }

        _loader.show(getFragmentManager(), "loading_spinner");

        String content = _binding.editTextTextMultiLine.getText().toString();
        CreateReportRequest request = new CreateReportRequest(content, reportedId, prefs.getUser().getId());
        Call<Void> call = ClientUtils.reportsService.createReport(request);
        call.enqueue(new Callback<  >() {
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