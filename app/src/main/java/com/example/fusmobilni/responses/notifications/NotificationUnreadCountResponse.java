package com.example.fusmobilni.responses.notifications;

public class NotificationUnreadCountResponse {
    public int unreadCount;

    public NotificationUnreadCountResponse(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}
