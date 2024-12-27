package com.example.fusmobilni.clients.services.items.reviews.notifications;

import com.example.fusmobilni.responses.notifications.NotificationUnreadCountResponse;
import com.example.fusmobilni.responses.notifications.NotificationsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NotificationsService {
    @GET("notifications/{userId}")
    Call<NotificationsResponse> findAllByUserId(@Path("userId") Long userId);

    @GET("notifications/read/{userId}")
    Call<Void> readAllByUserId(@Path("userId") Long userId);

    @GET("notifications/{userId}/number-unread")
    Call<NotificationUnreadCountResponse> countUnreadByUserId(@Path("userId") Long userId);
}
