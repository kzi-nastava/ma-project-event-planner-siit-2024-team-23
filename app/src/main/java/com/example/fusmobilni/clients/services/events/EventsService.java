package com.example.fusmobilni.clients.services.events;

import com.example.fusmobilni.responses.events.EventsHomeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EventsService {

    @GET("events/top-five-events")
    Call<EventsHomeResponse> findTopFiveEvents(@Query("city") String city);
}
