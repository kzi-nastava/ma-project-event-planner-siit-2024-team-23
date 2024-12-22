package com.example.fusmobilni.responses.items;

public class EventOrganizerReviewResponse {
    Long id;
    String firstName;
    String lastName;
    String email;
    String role;
    String image;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public EventOrganizerReviewResponse(String email, String firstName, Long id, String image, String lastName, String role) {
        this.email = email;
        this.firstName = firstName;
        this.id = id;
        this.image = image;
        this.lastName = lastName;
        this.role = role;
    }
}
