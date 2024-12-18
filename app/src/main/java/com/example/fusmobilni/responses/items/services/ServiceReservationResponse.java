package com.example.fusmobilni.responses.items.services;

import com.example.fusmobilni.responses.items.CategoryResponse;

public class ServiceReservationResponse {
    public Long id;
    public String name;
    public String description;
    public Double price;
    public String image;
    public CategoryResponse category;

    public ServiceReservationResponse(CategoryResponse category, String description, Long id, String name, Double price,String image) {
        this.category = category;
        this.description = description;
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
