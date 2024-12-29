package com.example.fusmobilni.responses.events.review;

public class EventReviewUserResponse {
    public Long id;
    public String firstName;
    public String lastName;
    public String avatar;

    public EventReviewUserResponse(String lastName, Long id, String firstName, String avatar) {
        this.lastName = lastName;
        this.id = id;
        this.firstName = firstName;
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
