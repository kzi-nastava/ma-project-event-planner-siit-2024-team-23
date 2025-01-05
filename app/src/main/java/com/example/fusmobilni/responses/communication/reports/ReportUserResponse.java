package com.example.fusmobilni.responses.communication.reports;

public class ReportUserResponse {
    public Long id;
    public String firstName;
    public String lastName;
    public String userImage;

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

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public ReportUserResponse(String firstName, Long id, String lastName, String userImage) {
        this.firstName = firstName;
        this.id = id;
        this.lastName = lastName;
        this.userImage = userImage;
    }
}
