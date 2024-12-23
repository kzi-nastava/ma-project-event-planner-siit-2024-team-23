package com.example.fusmobilni.responses.users;

import com.example.fusmobilni.model.enums.UserType;

public class UserInfoPreviewResponse {
    private Long id;
    private String firstName;
    private String surname;
    private String email;
    private UserType userRole;
    private boolean isActive;
    private boolean isSuspended;
    private String address;
    private String streetNumber;
    private String city;
    private String phoneNumber;
    private String companyName;
    private String companyDescription;

    public UserInfoPreviewResponse(Long id, String firstName, String surname, String email,
                            UserType userRole, boolean isActive, boolean isSuspended, String address,
                            String city, String phoneNumber, String companyName,
                            String companyDescription, String streetNumber) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.userRole = userRole;
        this.isActive = isActive;
        this.isSuspended = isSuspended;
        this.address = address;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.companyDescription = companyDescription;
        this.streetNumber = streetNumber;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserRole() {
        return userRole;
    }

    public void setUserRole(UserType userRole) {
        this.userRole = userRole;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }
}
