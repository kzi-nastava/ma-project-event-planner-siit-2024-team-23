package com.example.fusmobilni.responses.items;

public class IsBoughtItemResponse {

    public Boolean isBought;

    public IsBoughtItemResponse(Boolean isBought) {
        this.isBought = isBought;
    }

    public Boolean getBought() {
        return isBought;
    }

    public void setBought(Boolean bought) {
        isBought = bought;
    }
}
