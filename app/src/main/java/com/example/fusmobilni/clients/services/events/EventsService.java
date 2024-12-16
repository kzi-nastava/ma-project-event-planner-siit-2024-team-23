package com.example.fusmobilni.clients.services.events;

import com.example.fusmobilni.model.event.AgendaActivity;
import com.example.fusmobilni.requests.events.event.AgendaActivitiesResponse;
import com.example.fusmobilni.requests.events.event.CreateAgendaActivityRequest;
import com.example.fusmobilni.requests.events.event.CreateEventRequest;
import com.example.fusmobilni.requests.items.BuyItemRequest;
import com.example.fusmobilni.responses.events.EventTypeResponse;
import com.example.fusmobilni.responses.events.EventTypesResponse;
import com.example.fusmobilni.responses.events.GetEventByIdResponse;
import com.example.fusmobilni.responses.events.GetEventResponse;
import com.example.fusmobilni.responses.events.components.AgendaActivityResponse;
import com.example.fusmobilni.responses.events.components.EventComponentsResponse;
import com.example.fusmobilni.responses.events.filter.EventLocationsResponse;
import com.example.fusmobilni.responses.events.filter.EventsPaginationResponse;
import com.example.fusmobilni.responses.events.home.EventsHomeResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
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
    @GET("events/{id}")
    Call<GetEventByIdResponse> findById(@Path("id") Long id);

    @POST("events/{id}/event-components")
    Call<Void> createEventComponent(@Path("id") Long id,
                                    @Body BuyItemRequest request);

    @DELETE("events/{id}/event-components/{componentId}")
    Call<Void> removeComponentFromEvent(@Path("id") Long id,
                                        @Path("componentId") Long componentId);
    @GET("events/{id}/event-components")
    Call<EventComponentsResponse> findComponentsByEventId(@Path("id") Long id);

    @GET("events/{eventId}/agenda-activities")
    Call<AgendaActivitiesResponse> findAllAgendasByEventId(@Path("eventId") Long eventId);

    @DELETE("events/{eventId}/agenda-activities/{id}")
    Call<Void> deleteAgenda(@Path("eventId") Long eventId, @Path("id") Long id);

    @POST("events/{eventId}/agenda-activities")
    Call<AgendaActivityResponse> createAgenda(@Path("eventId") Long eventId, @Body CreateAgendaActivityRequest request);
}
