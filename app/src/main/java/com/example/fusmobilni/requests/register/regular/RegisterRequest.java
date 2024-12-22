package com.example.fusmobilni.requests.register.regular;

import com.example.fusmobilni.model.enums.UserType;
import com.example.fusmobilni.responses.location.LocationResponse;

public class RegisterRequest {
    public String firstName;
    public String surname;
    public String email;
    public String password;
    public String phoneNumber;
    public String city;
    public String address;
    public String streetNumber;
    public String latitude;
    public String longitude;
    public UserType role;
    public String companyName;
    public String companyDescription;

    public RegisterRequest(String firstName, String surname, String email, String password, String phone,
                           String city, String address, String streetNumber, String latitude, String longitude,
                           UserType role, String companyName, String companyDescription) {
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phone;
        this.city = city;
        this.address = address;
        this.streetNumber = streetNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.role = role;
        this.companyName = companyName;
        this.companyDescription = companyDescription;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phoneNumber;
    }

    public void setPhone(String phone) {
        this.phoneNumber = phone;
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

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
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
