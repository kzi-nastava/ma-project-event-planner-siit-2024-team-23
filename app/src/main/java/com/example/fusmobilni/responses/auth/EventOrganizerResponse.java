package com.example.fusmobilni.responses.auth;

public class EventOrganizerResponse {
    public Long id;
    public  String firstName;
    public  String lastName;
    public String email;
    public String role;
    public  boolean isActive;
    public  boolean isSuspended;
    public  String avatar;

    public EventOrganizerResponse(String avatar, String email, String firstName, Long id, boolean isActive, boolean isSuspended, String lastName, String role) {
        this.avatar = avatar;
        this.email = email;
        this.firstName = firstName;
        this.id = id;
        this.isActive = isActive;
        this.isSuspended = isSuspended;
        this.lastName = lastName;
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
