package com.example.fusmobilni.requests.users.favorites;

public class FavoriteEventRequest {
    private Long eventId;
    private Long userId;

    public FavoriteEventRequest(Long eventId, Long userId) {
        this.eventId = eventId;
        this.userId = userId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
