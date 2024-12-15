package com.example.fusmobilni.model.event;

import androidx.annotation.NonNull;

import com.example.fusmobilni.model.items.category.OfferingsCategory;

import java.util.List;

public class EventType {
    private final Long id;
    private String name;
    private String description;
    private List<OfferingsCategory> suggestedCategories;

    private boolean isActive=true;
    public EventType(Long id,String name, String description, List<OfferingsCategory> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        suggestedCategories = categories;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<OfferingsCategory> getSuggestedCategories() {
        return suggestedCategories;
    }

    public void setSuggestedCategories(List<OfferingsCategory> suggestedCategories) {
        this.suggestedCategories = suggestedCategories;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
    public boolean getActive(){ return isActive ;}

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
