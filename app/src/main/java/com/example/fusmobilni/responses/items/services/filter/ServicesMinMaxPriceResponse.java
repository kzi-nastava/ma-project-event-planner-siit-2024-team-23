package com.example.fusmobilni.responses.items.services.filter;

public class ServicesMinMaxPriceResponse {
    public double minPrice;
    public double maxPrice;

    public double getMaxPrice() {
        return maxPrice;
    }

    public ServicesMinMaxPriceResponse(double maxPrice, double minPrice) {
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }
}
