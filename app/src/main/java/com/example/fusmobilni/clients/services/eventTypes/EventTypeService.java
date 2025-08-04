package com.example.fusmobilni.clients.services.eventTypes;

import com.example.fusmobilni.model.event.eventTypes.EventType;
import com.example.fusmobilni.requests.eventTypes.EventTypeCreateRequest;
import com.example.fusmobilni.requests.eventTypes.EventTypeUpdateRequest;
import com.example.fusmobilni.requests.eventTypes.GetEventTypesResponse;
import com.example.fusmobilni.responses.events.GetEventTypesWithCategoriesResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EventTypeService {

    @Headers({
            "User-agent: Mobile-Android",
            "Content-Type: application/json"
    })
    @GET("event-types")
    Call<GetEventTypesResponse> findAll();

    @GET("event-types")
    Call<GetEventTypesWithCategoriesResponse> findAllWIthSuggestedCategories();

    @POST("event-types")
    Call<EventType> createEventType(@Body EventTypeCreateRequest request);

    @PUT("event-types/{id}")
    Call<EventType> updateEventType(@Path("id") Long id, @Body EventTypeUpdateRequest request);
}
