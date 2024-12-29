package com.example.fusmobilni.clients.services.events.attendance;

import com.example.fusmobilni.responses.events.attendance.HasAttendedResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AttendanceService {
    @GET("attendance/{eventId}/user/{userId}")
    Call<HasAttendedResponse> checkIfUserHasAttended(@Path("eventId") Long eventId, @Path("userId") Long userId);
}
