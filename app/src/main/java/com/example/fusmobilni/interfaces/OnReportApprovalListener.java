package com.example.fusmobilni.interfaces;

import com.example.fusmobilni.responses.communication.reports.ReportAdminResponse;

public interface OnReportApprovalListener {
    void onApprove(ReportAdminResponse report);

    void onDismiss(ReportAdminResponse report);
}
