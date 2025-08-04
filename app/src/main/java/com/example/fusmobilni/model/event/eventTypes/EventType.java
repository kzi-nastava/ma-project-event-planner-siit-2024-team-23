package com.example.fusmobilni.model.event.eventTypes;

import androidx.annotation.NonNull;

import com.example.fusmobilni.model.items.category.OfferingsCategory;

import java.util.List;

public class EventType {
    private final Long id;
    private String name;
    private String description;
    private SuggestedCategories suggestedCategories;

    private EventTypeStatus isActive;
    public EventType(Long id,String name, String description, SuggestedCategories categories) {
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

    public SuggestedCategories getSuggestedCategories() {
        return suggestedCategories;
    }

    public void setSuggestedCategories(SuggestedCategories suggestedCategories) {
        this.suggestedCategories = suggestedCategories;
    }

    public EventTypeStatus isActive() {
        return isActive;
    }

    public void setActive(EventTypeStatus active) {
        isActive = active;
    }
    public EventTypeStatus getActive(){ return isActive ;}

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
