package com.example.fusmobilni.requests.communication.reports;

public class UpdateReportStateRequest {
    public ReportVerdict verdict;

    public UpdateReportStateRequest(ReportVerdict verdict) {
        this.verdict = verdict;
    }

    public ReportVerdict getVerdict() {
        return verdict;
    }

    public void setVerdict(ReportVerdict verdict) {
        this.verdict = verdict;
    }
}
