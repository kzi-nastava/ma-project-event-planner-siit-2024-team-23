package com.example.fusmobilni.responses.events.review;

import java.util.List;

public class EventReviewsResponse {
    public List<EventReviewResponse> reviews;

    public EventReviewsResponse(List<EventReviewResponse> reviews) {
        this.reviews = reviews;
    }

    public List<EventReviewResponse> getReviews() {
        return reviews;
    }

    public void setReviews(List<EventReviewResponse> reviews) {
        this.reviews = reviews;
    }
}
