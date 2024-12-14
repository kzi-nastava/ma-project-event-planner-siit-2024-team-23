package com.example.fusmobilni.responses;

public class FastRegisterInvitationResponse {
    public Long id;
    public Object event;
    public String hash;
    public String email;
    public String state;

    public FastRegisterInvitationResponse(String email, Object event, String hash, Long id, String state) {
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
