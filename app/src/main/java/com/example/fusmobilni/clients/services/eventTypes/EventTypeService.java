package com.example.fusmobilni.clients.services.eventTypes;

import com.example.fusmobilni.requests.eventTypes.GetEventTypesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface EventTypeService {

    @Headers({
            "User-agent: Mobile-Android",
            "Content-Type: application/json"
    })
    @GET("event-types")
    Call<GetEventTypesResponse> findAll();


}
