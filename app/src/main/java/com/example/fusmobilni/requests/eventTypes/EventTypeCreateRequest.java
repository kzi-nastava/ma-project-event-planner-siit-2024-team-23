package com.example.fusmobilni.requests.eventTypes;

import java.util.ArrayList;

public class EventTypeCreateRequest {
    String name;

    String description;

    ArrayList<Long> suggestedCategoryIds;

    public EventTypeCreateRequest(String name, String description, ArrayList<Long> suggestedCategoryIds) {
        this.name = name;
        this.description = description;
        this.suggestedCategoryIds = suggestedCategoryIds;
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
