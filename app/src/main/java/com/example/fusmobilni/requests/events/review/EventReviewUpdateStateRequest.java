package com.example.fusmobilni.requests.events.review;


public class EventReviewUpdateStateRequest {
    public Long id;
    public AcceptanceState state;

    public EventReviewUpdateStateRequest(Long id, AcceptanceState state) {
        this.id = id;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AcceptanceState getState() {
        return state;
    }

    public void setState(AcceptanceState state) {
        this.state = state;
    }
}
