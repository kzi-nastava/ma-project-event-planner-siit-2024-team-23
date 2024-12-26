package com.example.fusmobilni.responses.items.notifications;

import java.util.List;

public class ItemReviewNotificationsResponse {
    public List<ItemReviewNotificationResponse> notifications;

    public ItemReviewNotificationsResponse(List<ItemReviewNotificationResponse> notifications) {
        this.notifications = notifications;
    }

    public List<ItemReviewNotificationResponse> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<ItemReviewNotificationResponse> notifications) {
        this.notifications = notifications;
    }
}
