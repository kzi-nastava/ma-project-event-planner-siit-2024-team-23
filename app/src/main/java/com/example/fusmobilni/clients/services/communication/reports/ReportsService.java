package com.example.fusmobilni.clients.services.communication.reports;

import com.example.fusmobilni.requests.communication.reports.CreateReportRequest;
import com.example.fusmobilni.requests.communication.reports.UpdateReportStateRequest;
import com.example.fusmobilni.responses.communication.reports.ReportsAdminResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReportsService {
    @POST("reports")
    Call<Void> createReport(@Body CreateReportRequest request);

    @GET("reports/pendingReports")
    Call<ReportsAdminResponse> findAllPendingReports();

    @PUT("reports/reportVerdict/{reportId}")
    Call<Void> updateReportState(@Path("reportId") Long reportId, @Body UpdateReportStateRequest request);
}
