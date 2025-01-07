package com.example.fusmobilni.clients.services.events;

import com.example.fusmobilni.model.event.AgendaActivity;
import com.example.fusmobilni.requests.events.event.AgendaActivitiesResponse;
import com.example.fusmobilni.requests.events.event.CreateAgendaActivityRequest;
import com.example.fusmobilni.requests.events.event.CreateEventRequest;
import com.example.fusmobilni.requests.events.event.EventComponentReservationRequest;
import com.example.fusmobilni.requests.items.BuyItemRequest;
import com.example.fusmobilni.responses.events.EventDetailsResponse;
import com.example.fusmobilni.responses.events.EventTypeResponse;
import com.example.fusmobilni.responses.events.EventTypesResponse;
import com.example.fusmobilni.responses.events.GetEventByIdResponse;
import com.example.fusmobilni.responses.events.GetEventResponse;
import com.example.fusmobilni.responses.events.components.AgendaActivityResponse;
import com.example.fusmobilni.responses.events.components.EventComponentsResponse;
import com.example.fusmobilni.responses.events.filter.EventLocationsResponse;
import com.example.fusmobilni.responses.events.filter.EventsPaginationResponse;
import com.example.fusmobilni.responses.events.home.EventsHomeResponse;
import com.example.fusmobilni.responses.events.statistics.EventStatisticsResponse;

import java.sql.Blob;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;

public interface EventsService {

    @GET("events/top-five-events")
    Call<EventsHomeResponse> findTopFiveEvents(@Query("userId") Long userId);

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


    @POST("events/{eventId}/components/service-reservation")
    Call<Void> createComponentWithReservation(@Path("eventId") Long eventId, @Body EventComponentReservationRequest request);

    @GET("events/{id}")
    Call<EventDetailsResponse> findEventById(@Path("id") Long id);

    @GET("events/{id}/image")
    @Streaming
    @Headers("Accept: image/jpeg")
    Call<ResponseBody> findEventImage(@Path("id") Long id);

    @GET("events/{id}/event-details/pdf")
    @Streaming
    Call<ResponseBody> downloadEventPdf(@Path("id") Long id);

    @GET("statistics/{eventId}")
    Call<EventStatisticsResponse> findStatisticsForEvent(@Path("eventId") Long id);

    @GET("events/{id}/event-statistics/pdf")
    @Streaming
    Call<ResponseBody> downloadEventStatisticsPdf(@Path("id") Long id);
}
