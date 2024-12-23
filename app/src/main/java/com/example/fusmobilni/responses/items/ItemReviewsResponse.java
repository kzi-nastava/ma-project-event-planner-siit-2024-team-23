package com.example.fusmobilni.responses.items;

import java.util.List;

public class ItemReviewsResponse {
    List<ItemReviewResponse> itemReviews;

    public ItemReviewsResponse(List<ItemReviewResponse> itemReviews) {
        this.itemReviews = itemReviews;
    }

    public List<ItemReviewResponse> getItemReviews() {
        return itemReviews;
    }

    public void setItemReviews(List<ItemReviewResponse> itemReviews) {
        this.itemReviews = itemReviews;
    }
}
