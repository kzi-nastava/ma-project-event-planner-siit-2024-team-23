package com.example.fusmobilni.clients.services.items.reviews.notifications;

import com.example.fusmobilni.responses.items.notifications.ItemReviewNotificationUnreadCountResponse;
import com.example.fusmobilni.responses.items.notifications.ItemReviewNotificationsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ItemReviewNotificationsService {
    @GET("notifications/item-reviews/{userId}")
    Call<ItemReviewNotificationsResponse> findAllByUserId(@Path("userId") Long userId);

    @GET("notifications/item-reviews/read/{userId}")
    Call<Void> readAllByUserId(@Path("userId") Long userId);

    @GET("notifications/item-reviews/{userId}/number-unread")
    Call<ItemReviewNotificationUnreadCountResponse> countUnreadByUserId(@Path("userId") Long userId);
}
