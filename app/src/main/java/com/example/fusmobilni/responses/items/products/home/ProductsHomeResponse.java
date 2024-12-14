package com.example.fusmobilni.responses.items.products.home;

import java.util.List;

public class ProductsHomeResponse {
    public List<ProductHomeResponse> products;

    public ProductsHomeResponse(List<ProductHomeResponse> products) {
        this.products = products;
    }

    public List<ProductHomeResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductHomeResponse> products) {
        this.products = products;
    }
}
