package com.example.fusmobilni.responses.location;

public class LocationResponse {
    public String city;
    public String street;
    public String streetNumber;
    public Double latitude;
    public Double longitude;

    public LocationResponse(String city, Double latitude, Double longitude, String street, String streetNumber) {
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }
}
