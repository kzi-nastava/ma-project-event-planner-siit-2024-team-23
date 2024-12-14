package com.example.fusmobilni.responses.items.products.filter;

public class ProductsMinMaxPriceResponse {
    public double minPrice;
    public double maxPrice;

    public ProductsMinMaxPriceResponse(double maxPrice, double minPrice) {
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
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
