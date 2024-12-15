package com.example.fusmobilni.responses.geoCoding;

public class GeoCodingResponse {
    public String lat;
    public String lon;

    public GeoCodingResponse(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
