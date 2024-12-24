package com.example.fusmobilni.responses.items.pricelist;

public class PriceListGetResponse {
    public Long id;
    public String name;
    public double price;
    public double discount;
    public double priceWithDiscount;

    public PriceListGetResponse(Long id, double priceWithDiscount, double discount, double price, String name) {
        this.id = id;
        this.priceWithDiscount = priceWithDiscount;
        this.discount = discount;
        this.price = price;
        this.name = name;
    }
}
