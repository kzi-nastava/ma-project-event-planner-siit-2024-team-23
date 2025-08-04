package com.example.fusmobilni.requests.register.fast;

import com.example.fusmobilni.responses.location.LocationResponse;

public class FastRegisterRequest {

    public String firstName;

    public String surname;
    public String email;

    public String password;

    public String repeatedPassword;

    public String hash;
    public String phone;
    public LocationResponse location;

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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public LocationResponse getLocation() {
        return location;
    }

    public void setLocation(LocationResponse location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public FastRegisterRequest(String email, String firstName, String hash, LocationResponse location, String password, String phone, String repeatedPassword, String surname) {
        this.email = email;
        this.firstName = firstName;
        this.hash = hash;
        this.location = location;
        this.password = password;
        this.phone = phone;
        this.repeatedPassword = repeatedPassword;
        this.surname = surname;
    }
}
