package com.example.fusmobilni.clients.services.items;

import com.example.fusmobilni.requests.events.event.GetItemsByCategoryAndPrice;
import com.example.fusmobilni.responses.events.GetItemsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ItemsService {
    @POST("items")
    Call<GetItemsResponse> findAllByCategoryAndPrice(@Body GetItemsByCategoryAndPrice request);
}
