package com.example.fusmobilni.responses.items.services.filter;

import com.example.fusmobilni.responses.location.LocationResponse;

import java.util.List;

public class ServiceLocationsResponse {
    public List<LocationResponse> locations;

    public ServiceLocationsResponse(List<LocationResponse> locations) {
        this.locations = locations;
    }

    public List<LocationResponse> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationResponse> locations) {
        this.locations = locations;
    }
}
