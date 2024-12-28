package com.example.fusmobilni.viewModels.admin;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.responses.items.ItemReviewResponse;

import java.util.ArrayList;
import java.util.List;

public class ReviewApprovalViewModel extends ViewModel {

    MutableLiveData<List<ItemReviewResponse>> itemReviews = new MutableLiveData<>(new ArrayList<>());

    public MutableLiveData<List<ItemReviewResponse>> getItemReviews() {
        return itemReviews;
    }

    public void removeItemReview(ItemReviewResponse review) {
        List<ItemReviewResponse> reviews = itemReviews.getValue();
        reviews.removeIf(item -> item.getId().equals(review.getId()));
        itemReviews.setValue(reviews);
    }

    public void setItemReviews(List<ItemReviewResponse> itemReviews) {
        this.itemReviews.setValue(itemReviews);
    }
}
