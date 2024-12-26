package com.example.fusmobilni.responses.items.notifications;

public class ItemReviewNotificationUnreadCountResponse {
    public int unreadCount;

    public ItemReviewNotificationUnreadCountResponse(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}
