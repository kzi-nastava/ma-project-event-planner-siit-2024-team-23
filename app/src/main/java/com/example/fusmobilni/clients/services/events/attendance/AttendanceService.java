package com.example.fusmobilni.clients.services.events.attendance;

import com.example.fusmobilni.responses.events.attendance.HasAttendedResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

public interface AttendanceService {
    @GET("attendance/{eventId}/user/{userId}")
    Call<HasAttendedResponse> checkIfUserHasAttended(@Path("eventId") Long eventId, @Path("userId") Long userId);

    @POST("attendance/{eventId}")
    @Streaming
    Call<Void> bookEvent(@Path("eventId") Long id);
}
