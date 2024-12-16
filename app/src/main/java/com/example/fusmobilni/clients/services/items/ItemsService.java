package com.example.fusmobilni.clients.services.items;

import com.example.fusmobilni.model.items.item.ItemDetails;
import com.example.fusmobilni.requests.events.event.GetItemsByCategoryAndPrice;
import com.example.fusmobilni.responses.events.GetItemsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ItemsService {
    @POST("items")
    Call<GetItemsResponse> findAllByCategoryAndPrice(@Body GetItemsByCategoryAndPrice request);

    @GET("items/{id}")
    Call<ItemDetails> findById(@Path("id") Long id);
}
