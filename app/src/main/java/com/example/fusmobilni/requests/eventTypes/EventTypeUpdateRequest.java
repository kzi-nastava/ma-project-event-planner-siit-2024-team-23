package com.example.fusmobilni.requests.eventTypes;

import com.example.fusmobilni.model.event.eventTypes.EventType;
import com.example.fusmobilni.model.event.eventTypes.EventTypeStatus;

import java.util.ArrayList;

public class EventTypeUpdateRequest {
    String name;

    String description;

    ArrayList<Long> suggestedCategoryIds;
    EventTypeStatus isActive;

    public EventTypeUpdateRequest(String name, String description, ArrayList<Long> suggestedCategoryIds, EventTypeStatus isActive) {
        this.name = name;
        this.description = description;
        this.suggestedCategoryIds = suggestedCategoryIds;
        this.isActive = isActive;
    }

    public EventTypeStatus getIsActive() {
        return isActive;
    }

    public void setIsActive(EventTypeStatus isActive) {
        this.isActive = isActive;
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

    public ArrayList<Long> getSuggestedCategoryIds() {
        return suggestedCategoryIds;
    }

    public void setSuggestedCategoryIds(ArrayList<Long> suggestedCategoryIds) {
        this.suggestedCategoryIds = suggestedCategoryIds;
    }
}
