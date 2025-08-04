package com.example.fusmobilni.clients.services.communication.blocks;

import com.example.fusmobilni.requests.communication.blocks.BlockCreateRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BlockService {
    @POST("blocks")
    Call<Object> createBlock(@Body BlockCreateRequest request);

    @DELETE("blocks/unblock/{blockedId}/{blockerId}")
    Call<Object> unblock(@Path("blockedId") Long blockedId, @Path("blockerId") Long blockerId);
}
