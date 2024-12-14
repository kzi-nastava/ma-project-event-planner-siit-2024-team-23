package com.example.fusmobilni.responses.items.services.filter;

import com.example.fusmobilni.responses.items.CategoryResponse;
import com.example.fusmobilni.responses.location.LocationResponse;

public class ServicePaginationResponse {
    public Long id;
    public String name;
    public String description;
    public Double price;

    public CategoryResponse category;
    public LocationResponse location;

    public ServicePaginationResponse(CategoryResponse category, String description, Long id, LocationResponse location, String name, Double price) {
        this.category = category;
        this.description = description;
        this.id = id;
        this.location = location;
        this.name = name;
        this.price = price;
    }

    public CategoryResponse getCategory() {
        return category;
    }

    public void setCategory(CategoryResponse category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocationResponse getLocation() {
        return location;
    }

    public void setLocation(LocationResponse location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}