package com.example.fusmobilni.responses.communication.reports;

public class ReportAdminResponse {
    public Long id;
    public String reason;
    public String verdict;
    public String date;
    public ReportUserResponse reporter;
    public ReportUserResponse reported;

    public ReportAdminResponse(String date, Long id, String reason, ReportUserResponse reported, ReportUserResponse reporter, String verdict) {
        this.date = date;
        this.id = id;
        this.reason = reason;
        this.reported = reported;
        this.reporter = reporter;
        this.verdict = verdict;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ReportUserResponse getReported() {
        return reported;
    }

    public void setReported(ReportUserResponse reported) {
        this.reported = reported;
    }

    public ReportUserResponse getReporter() {
        return reporter;
    }

    public void setReporter(ReportUserResponse reporter) {
        this.reporter = reporter;
    }

    public String getVerdict() {
        return verdict;
    }

    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }
}
