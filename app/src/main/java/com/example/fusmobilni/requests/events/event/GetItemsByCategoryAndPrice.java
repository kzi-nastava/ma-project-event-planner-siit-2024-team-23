package com.example.fusmobilni.requests.events.event;

public class GetItemsByCategoryAndPrice {
    public Long categoryId;
    public double price;

    public GetItemsByCategoryAndPrice(Long categoryId, double price) {
        this.categoryId = categoryId;
        this.price = price;
    }
}
