package com.example.fusmobilni.fragments.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.admin.ReportApprovalAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentReportApprovalBinding;
import com.example.fusmobilni.databinding.FragmentSucessDialogBinding;
import com.example.fusmobilni.fragments.dialogs.FailiureDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SuccessDialogFragment;
import com.example.fusmobilni.interfaces.OnReportApprovalListener;
import com.example.fusmobilni.requests.communication.reports.ReportVerdict;
import com.example.fusmobilni.requests.communication.reports.UpdateReportStateRequest;
import com.example.fusmobilni.responses.communication.reports.ReportAdminResponse;
import com.example.fusmobilni.responses.communication.reports.ReportsAdminResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportApprovalFragment extends Fragment {


    private FragmentReportApprovalBinding _binding;
    private ReportApprovalAdapter adapter;

    private SpinnerDialogFragment _loader;
    private FailiureDialogFragment _failure;
    private SuccessDialogFragment _success;

    public ReportApprovalFragment() {
        // Required empty public constructor
    }

    public static ReportApprovalFragment newInstance(String param1, String param2) {
        ReportApprovalFragment fragment = new ReportApprovalFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _binding = FragmentReportApprovalBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        initializeDialogs();
        fetchReports();
        return root;
    }

    private void fetchReports() {
        _loader.show(getFragmentManager(), "loading_spinner");
        Call<ReportsAdminResponse> call = ClientUtils.reportsService.findAllPendingReports();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ReportsAdminResponse> call, Response<ReportsAdminResponse> response) {
                if (!response.isSuccessful()) {
                    openFailiureWindow("Didn't fetch");
                    return;
                }
                _loader.dismiss();
                adapter = new ReportApprovalAdapter(response.body().getReports(), new OnReportApprovalListener() {
                    @Override
                    public void onApprove(ReportAdminResponse report) {
                        approveReport(report);
                    }

                    @Override
                    public void onDismiss(ReportAdminResponse report) {
                        dismissReport(report);
                    }
                });
                _binding.reportsRecycleView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ReportsAdminResponse> call, Throwable t) {
                openFailiureWindow("Didn't fetch");
            }
        });
    }

    void approveReport(ReportAdminResponse report) {
        _loader.show(getFragmentManager(), "loading_spinner");
        Call<Void> call = ClientUtils.reportsService.updateReportState(report.getId(), new UpdateReportStateRequest(ReportVerdict.JUSTIFIED));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    openFailiureWindow("Failed to approve report");
                    return;
                }
                openSuccessWindow("Report approved");
                adapter.removeReport(report);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                openFailiureWindow("Failed to approve report");
            }
        });
    }

    void dismissReport(ReportAdminResponse report) {
        _loader.show(getFragmentManager(), "loading_spinner");
        Call<Void> call = ClientUtils.reportsService.updateReportState(report.getId(), new UpdateReportStateRequest(ReportVerdict.INCONCLUSIVE));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    openFailiureWindow("Failed to dismiss report");
                    return;
                }
                openSuccessWindow("Report dismissed");
                adapter.removeReport(report);

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                openFailiureWindow("Failed to dismiss report");
            }
        });
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

    void openSuccessWindow(String message) {
        if (_loader != null) {
            _loader.dismiss();
        }
        Bundle args = new Bundle();
        args.putString("Title", "Sucess");
        args.putString("Message", message);
        _success.setArguments(args);
        _success.show(getParentFragmentManager(), "sucess_dialog");
    }

    private void initializeDialogs() {
        _loader = new SpinnerDialogFragment();
        _loader.setCancelable(false);
        _failure = new FailiureDialogFragment();
        _success = new SuccessDialogFragment();
    }

}