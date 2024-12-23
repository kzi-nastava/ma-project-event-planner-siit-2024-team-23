package com.example.fusmobilni.responses.users;

import java.util.ArrayList;

public class UserFavoriteProductsResponse {
    private ArrayList<UserFavoriteProductResponse> products;

    public UserFavoriteProductsResponse(ArrayList<UserFavoriteProductResponse> products) {
        this.products = products;
    }

    public ArrayList<UserFavoriteProductResponse> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<UserFavoriteProductResponse> products) {
        this.products = products;
    }
}
