package com.example.fusmobilni.responses.items.services;

import com.example.fusmobilni.fragments.items.service.DurationType;
import com.example.fusmobilni.responses.items.CategoryResponse;


public class ServiceReservationResponse {
    public Long id;
    public String name;
    public String description;
    public Double price;
    public String image;
    public CategoryResponse category;
    public DurationType durationType;
    int duration;
    int minDuration;
    int maxDuration;

    public ServiceReservationResponse(CategoryResponse category, String description, int duration, DurationType durationType, Long id, String image, int maxDuration, int minDuration, String name, Double price) {
        this.category = category;
        this.description = description;
        this.duration = duration;
        this.durationType = durationType;
        this.id = id;
        this.image = image;
        this.maxDuration = maxDuration;
        this.minDuration = minDuration;
        this.name = name;
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public DurationType getDurationType() {
        return durationType;
    }

    public void setDurationType(DurationType durationType) {
        this.durationType = durationType;
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }

    public int getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(int minDuration) {
        this.minDuration = minDuration;
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
