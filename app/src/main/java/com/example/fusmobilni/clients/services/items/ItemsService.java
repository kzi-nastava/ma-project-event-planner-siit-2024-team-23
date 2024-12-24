package com.example.fusmobilni.clients.services.items;

import com.example.fusmobilni.model.items.item.ItemDetails;
import com.example.fusmobilni.requests.events.event.GetItemsByCategoryAndPrice;
import com.example.fusmobilni.requests.items.ItemReviewCreateRequest;
import com.example.fusmobilni.requests.items.ItemReviewUpdateStateRequest;
import com.example.fusmobilni.responses.events.GetItemsResponse;
import com.example.fusmobilni.responses.items.IsBoughtItemResponse;
import com.example.fusmobilni.responses.items.ItemReviewResponse;
import com.example.fusmobilni.responses.items.ItemReviewsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ItemsService {
    @POST("items")
    Call<GetItemsResponse> findAllByCategoryAndPrice(@Body GetItemsByCategoryAndPrice request);

    @GET("items/{id}")
    Call<ItemDetails> findById(@Path("id") Long id);

    @GET("items/{id}/checkBought/{organizerId}")
    Call<IsBoughtItemResponse> checkIfBought(@Path("id") Long id, @Path("organizerId") Long organizerId);

    @POST("items/reviews")
    Call<Void> submitReview(@Body ItemReviewCreateRequest request);

    @GET("items/reviews/pending")
    Call<ItemReviewsResponse> findPendingReviews();

    @PUT("items/reviews/adminApproval")
    Call<ItemReviewResponse> updateReviewState(@Body ItemReviewUpdateStateRequest request);
}
