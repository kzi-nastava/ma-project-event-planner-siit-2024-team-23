package com.example.fusmobilni.responses.notifications;

import java.util.List;

public class NotificationsResponse {
    public List<NotificationResponse> notifications;

    public NotificationsResponse(List<NotificationResponse> notifications) {
        this.notifications = notifications;
    }

    public List<NotificationResponse> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationResponse> notifications) {
        this.notifications = notifications;
    }
}
