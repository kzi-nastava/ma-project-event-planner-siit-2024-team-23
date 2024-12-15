package com.example.fusmobilni.clients.services.serviceOfferings;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceOfferingReservationService {
    @GET("events/{eventId}/reservations/reserved/{serviceId}")
    Call<Boolean> checkIfReservationTimeTaken(
            @Path("eventId") Long eventId,
            @Path("serviceId") Long serviceId,
            @Query("startTime") String startTime,
            @Query("endTime") String endTime
    );
}
