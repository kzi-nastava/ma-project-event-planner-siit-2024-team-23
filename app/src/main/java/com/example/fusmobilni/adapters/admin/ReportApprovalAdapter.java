package com.example.fusmobilni.adapters.admin;

import static com.example.fusmobilni.adapters.AdapterUtils.convertToUrisFromBase64;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.interfaces.OnReportApprovalListener;
import com.example.fusmobilni.responses.communication.reports.ReportAdminResponse;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportApprovalAdapter extends RecyclerView.Adapter<ReportApprovalAdapter.ReportsApprovalViewHolder> {

    private List<ReportAdminResponse> reports = new ArrayList<>();
    private OnReportApprovalListener listener;

    public ReportApprovalAdapter(OnReportApprovalListener listener) {
        reports = new ArrayList<>();
        this.listener = listener;
    }

    public ReportApprovalAdapter(List<ReportAdminResponse> reports, OnReportApprovalListener listener) {
        this.reports = reports;
        this.listener = listener;
    }

    public void removeReport(ReportAdminResponse report) {
        reports.removeIf(item -> item.getId().equals(report.getId()));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReportsApprovalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_report_approve, parent, false);
        return new ReportsApprovalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportsApprovalViewHolder holder, int position) {
        ReportAdminResponse report = reports.get(position);
        holder.reporterName.setText(report.getReporter().getFirstName() + " " + report.getReporter().getLastName());
        try {
            holder.reporterImage.setImageURI(convertToUrisFromBase64(holder.reporterImage.getContext(), report.getReporter().getUserImage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        holder.reportedName.setText(report.getReported().getFirstName() + " " + report.getReported().getLastName());
        try {
            holder.reportedImage.setImageURI(convertToUrisFromBase64(holder.reportedImage.getContext(), report.getReported().getUserImage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        holder.reason.setText(report.getReason());

        holder.suspendButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onApprove(report);
            }
        });
        holder.dismissButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDismiss(report);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public static class ReportsApprovalViewHolder extends RecyclerView.ViewHolder {
        ImageView reportedImage;
        TextView reportedName;
        TextView reason;
        ImageView reporterImage;
        TextView reporterName;
        MaterialButton suspendButton;
        MaterialButton dismissButton;

        public ReportsApprovalViewHolder(@NonNull View itemView) {
            super(itemView);
            this.reason = itemView.findViewById(R.id.textVIewReportReason);
            this.reportedImage = itemView.findViewById(R.id.reportedImage);
            this.reportedName = itemView.findViewById(R.id.textViewReportedName);
            this.reporterImage = itemView.findViewById(R.id.reporterImage);
            this.reporterName = itemView.findViewById(R.id.textViewReporterName);
            this.suspendButton = itemView.findViewById(R.id.suspendButton);
            this.dismissButton = itemView.findViewById(R.id.dismissButton);
        }
    }
}
