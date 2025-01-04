package com.example.fusmobilni.clients.services.communication.reports;

import com.example.fusmobilni.requests.communication.reports.CreateReportRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReportsService {
    @POST("reports")
    Call<Void> createReport(@Body CreateReportRequest request);
}
