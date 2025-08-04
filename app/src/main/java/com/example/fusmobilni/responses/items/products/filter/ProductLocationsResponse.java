package com.example.fusmobilni.responses.items.products.filter;

import com.example.fusmobilni.responses.location.LocationResponse;

import java.util.List;

public class ProductLocationsResponse {
    public List<LocationResponse> locations;

    public ProductLocationsResponse(List<LocationResponse> locations) {
        this.locations = locations;
    }

    public List<LocationResponse> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationResponse> locations) {
        this.locations = locations;
    }
}
