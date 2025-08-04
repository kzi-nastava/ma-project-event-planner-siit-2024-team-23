package com.example.fusmobilni.requests.events.review;

public class EventReviewCreateRequest {
    public String content;
    public int rating;
    public Long userId;
    public Long eventId;

    public EventReviewCreateRequest(String content, Long eventId, int rating, Long userId) {
        this.content = content;
        this.eventId = eventId;
        this.rating = rating;
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
