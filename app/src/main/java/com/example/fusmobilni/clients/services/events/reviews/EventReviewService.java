package com.example.fusmobilni.clients.services.events.reviews;

import com.example.fusmobilni.requests.events.review.EventReviewUpdateStateRequest;
import com.example.fusmobilni.responses.events.review.EventReviewResponse;
import com.example.fusmobilni.responses.events.review.EventReviewsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

public interface EventReviewService {
    @GET("admin/events/reviews")
    Call<EventReviewsResponse> findAllPendingReviews();

    @PUT("admin/events/reviews/adminApproval")
    Call<EventReviewsResponse> updateEventReviewState(@Body EventReviewUpdateStateRequest request);
}
