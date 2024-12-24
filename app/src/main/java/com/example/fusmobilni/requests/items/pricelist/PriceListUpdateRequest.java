package com.example.fusmobilni.requests.items.pricelist;

public class PriceListUpdateRequest {
    public double price;
    public double discount;

    public PriceListUpdateRequest(double price, double discount) {
        this.price = price;
        this.discount = discount;
    }
}
