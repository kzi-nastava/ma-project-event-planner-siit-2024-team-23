package com.example.fusmobilni.model;

import android.net.Uri;

public class DummyService {

    private String title;
    private String description;
    private String eventType;
    private String category;
    private double price;
    private boolean isAvailable;

    public DummyService(String title, String description) {
        this.title = title;
        this.description = description;
        this.isAvailable = true;
        this.price = 500;
        this.category = "Sport";
        this.eventType = "Svadba";
    }

    public DummyService(String title, String description, String category, String eventType, boolean isAvailable, double price) {
        this.eventType = eventType;
        this.description = description;
        this.title = title;
        this.price = price;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
