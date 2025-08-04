package com.example.fusmobilni.requests.events.event;

public class EventComponentReservationRequest {
    public Long categoryId;

    public EventComponentReservationRequest(Long categoryId, Double estimatedBudget, Long serviceId, Double actualItemPrice, String itemName, String itemDescription, String startTime, String endTime) {
        this.categoryId = categoryId;
        this.estimatedBudget = estimatedBudget;
        this.serviceId = serviceId;
        this.actualItemPrice = actualItemPrice;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Double estimatedBudget;
    public Long serviceId;
    public Double actualItemPrice;
    public String itemName;
    public String itemDescription;
    public String startTime;
    public String endTime;
}
