package com.example.fusmobilni.responses.users.reviews;

public class UserReviewUserResponse {
    public Long id;
    public String firstName;
    public String lastName;
    public String role;
    public String userImage;

    public UserReviewUserResponse(String firstName, Long id, String lastName, String role, String userImage) {
        this.firstName = firstName;
        this.id = id;
        this.lastName = lastName;
        this.role = role;
        this.userImage = userImage;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
