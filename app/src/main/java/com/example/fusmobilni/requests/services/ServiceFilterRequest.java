package com.example.fusmobilni.requests.services;

import java.util.ArrayList;

public class ServiceFilterRequest {
    public String name;
    public double minPrice;
    public double maxPrice;
    public Long categoryId;
    public ArrayList<Long> eventTypeIds;
    public boolean availabilityEnabled;
    public boolean availability;

    public ServiceFilterRequest(String name, double minPrice, double maxPrice,
                                Long categoryId, ArrayList<Long> eventTypeIds, boolean availabilityEnabled,
                                boolean availability) {
        this.name = name;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.categoryId = categoryId;
        this.eventTypeIds = eventTypeIds;
        this.availabilityEnabled = availabilityEnabled;
        this.availability = availability;
    }

    public ServiceFilterRequest() {
        this.name="";
        this.minPrice = 0;
        this.maxPrice = 100000;
        this.categoryId = null;
        this.eventTypeIds = new ArrayList<>();
        this.availabilityEnabled = false;
        this.availability = false;

    }
}
