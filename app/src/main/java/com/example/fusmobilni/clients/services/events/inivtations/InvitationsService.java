package com.example.fusmobilni.clients.services.events.inivtations;

import com.example.fusmobilni.requests.events.invitations.InvitationsCreateRequest;
import com.example.fusmobilni.responses.events.invitations.InvitationsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InvitationsService {
    @POST("events/{eventId}/invitations")
    Call<InvitationsResponse> create(@Path("eventId") Long eventId, @Body InvitationsCreateRequest request);
}
