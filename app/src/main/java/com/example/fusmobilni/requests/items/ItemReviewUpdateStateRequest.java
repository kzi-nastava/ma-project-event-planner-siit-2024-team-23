package com.example.fusmobilni.requests.items;

public class ItemReviewUpdateStateRequest {
    Long reviewId;
    AcceptanceState state;

    public ItemReviewUpdateStateRequest(Long reviewId, AcceptanceState state) {
        this.reviewId = reviewId;
        this.state = state;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public AcceptanceState getState() {
        return state;
    }

    public void setState(AcceptanceState state) {
        this.state = state;
    }
}
