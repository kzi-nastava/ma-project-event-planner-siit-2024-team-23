package com.example.fusmobilni.responses.events.invitations;

public class InvitationResponse {
    Long id;
    Object event;
    String hash;
    String email;
    String state;

    public InvitationResponse(String email, Object event, String hash, Long id, String state) {
        this.email = email;
        this.event = event;
        this.hash = hash;
        this.id = id;
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getEvent() {
        return event;
    }

    public void setEvent(Object event) {
        this.event = event;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
