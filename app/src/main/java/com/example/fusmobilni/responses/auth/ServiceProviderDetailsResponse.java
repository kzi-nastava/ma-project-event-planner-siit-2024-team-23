package com.example.fusmobilni.responses.auth;

import com.example.fusmobilni.responses.location.LocationResponse;

public class ServiceProviderDetailsResponse {
    Long id;
    String firstName;
    String lastName;
    String email;
    String image;
    LocationResponse companyLocation;

    public ServiceProviderDetailsResponse(LocationResponse companyLocation, String email, String firstName, Long id, String image, String lastName) {
        this.companyLocation = companyLocation;
        this.email = email;
        this.firstName = firstName;
        this.id = id;
        this.image = image;
        this.lastName = lastName;
    }

    public LocationResponse getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(LocationResponse companyLocation) {
        this.companyLocation = companyLocation;
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
}
