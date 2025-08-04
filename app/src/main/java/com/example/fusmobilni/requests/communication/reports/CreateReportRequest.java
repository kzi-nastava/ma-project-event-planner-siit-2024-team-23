package com.example.fusmobilni.requests.communication.reports;

public class CreateReportRequest {
    public String reason;
    public Long reportedUserId;
    public Long reporterUserId;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(Long reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    public Long getReporterUserId() {
        return reporterUserId;
    }

    public void setReporterUserId(Long reporterUserId) {
        this.reporterUserId = reporterUserId;
    }

    public CreateReportRequest(String reason, Long reportedUserId, Long reporterUserId) {
        this.reason = reason;
        this.reportedUserId = reportedUserId;
        this.reporterUserId = reporterUserId;
    }
}
