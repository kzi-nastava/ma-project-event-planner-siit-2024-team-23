package com.example.fusmobilni.requests.events.invitations;

public class InvitationCreateRequest {
    public Long eventId;
    public String email;
    public String state;

    public InvitationCreateRequest(String email, Long eventId, String state) {
        this.email = email;
        this.eventId = eventId;
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
