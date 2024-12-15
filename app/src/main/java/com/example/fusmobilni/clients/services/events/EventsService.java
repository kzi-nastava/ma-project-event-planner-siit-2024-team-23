package com.example.fusmobilni.clients.services.events;

import com.example.fusmobilni.requests.events.event.CreateEventRequest;
import com.example.fusmobilni.responses.events.EventTypeResponse;
import com.example.fusmobilni.responses.events.EventTypesResponse;
import com.example.fusmobilni.responses.events.GetEventResponse;
import com.example.fusmobilni.responses.events.filter.EventLocationsResponse;
import com.example.fusmobilni.responses.events.filter.EventsPaginationResponse;
import com.example.fusmobilni.responses.events.home.EventsHomeResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface EventsService {

    @GET("events/top-five-events")
    Call<EventsHomeResponse> findTopFiveEvents(@Query("city") String city);

    @GET("events/browse")
    Call<EventsPaginationResponse> findFilteredAndPaginated(
            @Query("page") int page,
            @Query("pageSize") int pageSize
    );

    @GET("events/browse")
    Call<EventsPaginationResponse> findFilteredAndPaginated(
            @Query("page") int page,
            @Query("pageSize") int pageSize,
            @QueryMap Map<String, String> options
    );

    @GET("event-types")
    Call<EventTypesResponse> findEventTypesForEvents();

    @GET("events/locations")
    Call<EventLocationsResponse> findEventLocations();

    @POST("events")
    Call<GetEventResponse> create(
            @Body CreateEventRequest request
            );
}
