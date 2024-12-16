package com.example.fusmobilni.requests.items;

import java.time.LocalTime;

public class BuyItemRequest {
    public Long itemId;
    public Long eventId;
    public String startTime;
    public String endTime;

    public BuyItemRequest(Long itemId, double estimatedBudget, String endTime, String startTime, Long eventId) {
        this.itemId = itemId;
        this.estimatedBudget = estimatedBudget;
        this.endTime = endTime;
        this.startTime = startTime;
        this.eventId = eventId;
    }

    public double estimatedBudget;
}
