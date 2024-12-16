package com.example.fusmobilni.responses.events.components;

import com.example.fusmobilni.model.items.category.OfferingsCategory;

public class EventComponentResponse {
    public Long id;
    public OfferingsCategory category;
    public double estimatedBudget;
    public String itemName;
    public double actualPrice;

    public EventComponentResponse(Long id, double actualPrice, String itemName, double estimatedBudget, OfferingsCategory category) {
        this.id = id;
        this.actualPrice = actualPrice;
        this.itemName = itemName;
        this.estimatedBudget = estimatedBudget;
        this.category = category;
    }
}
