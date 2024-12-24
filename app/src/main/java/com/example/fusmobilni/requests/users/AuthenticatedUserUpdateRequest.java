package com.example.fusmobilni.requests.users;


public class AuthenticatedUserUpdateRequest {
    private String firstName;

    private String surname;

    private String city;

    private String address;
    private String streetNumber;
    private String longitude;
    private String latitude;

    private String phoneNumber;

    public AuthenticatedUserUpdateRequest(String firstName, String surname, String city, String address, String phoneNumber,
                                          String streetNumber, String latitude, String longitude) {
        this.firstName = firstName;
        this.surname = surname;
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.streetNumber = streetNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
