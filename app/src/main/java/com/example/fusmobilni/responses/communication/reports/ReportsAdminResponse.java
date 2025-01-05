package com.example.fusmobilni.responses.communication.reports;

import java.util.List;

public class ReportsAdminResponse {
    List<ReportAdminResponse> reports;

    public ReportsAdminResponse(List<ReportAdminResponse> reports) {
        this.reports = reports;
    }

    public List<ReportAdminResponse> getReports() {
        return reports;
    }

    public void setReports(List<ReportAdminResponse> reports) {
        this.reports = reports;
    }
}
