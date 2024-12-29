package com.example.fusmobilni.viewModels.admin;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.clients.services.events.reviews.EventReviewService;
import com.example.fusmobilni.responses.events.review.EventReviewResponse;
import com.example.fusmobilni.responses.items.ItemReviewResponse;

import java.util.ArrayList;
import java.util.List;

public class ReviewApprovalViewModel extends ViewModel {

    MutableLiveData<List<ItemReviewResponse>> itemReviews = new MutableLiveData<>(new ArrayList<>());
    MutableLiveData<List<EventReviewResponse>> eventReviews = new MutableLiveData<>(new ArrayList<>());
    MutableLiveData<Boolean> itemReviewsLoaded = new MutableLiveData<>(false);
    MutableLiveData<Boolean> eventReviewsLoaded = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> getEventReviewsLoaded() {
        return eventReviewsLoaded;
    }

    public void setEventReviewsLoaded(Boolean eventReviewsLoaded) {
        this.eventReviewsLoaded.setValue(eventReviewsLoaded);
    }

    public MutableLiveData<Boolean> getItemReviewsLoaded() {
        return itemReviewsLoaded;
    }

    public void setItemReviewsLoaded(Boolean itemReviewsLoaded) {
        this.itemReviewsLoaded.setValue(itemReviewsLoaded);
    }

    public MutableLiveData<List<EventReviewResponse>> getEventReviews() {
        return eventReviews;
    }

    public void setEventReviews(List<EventReviewResponse> eventReviews) {
        this.eventReviews.setValue(eventReviews);
    }

    public MutableLiveData<List<ItemReviewResponse>> getItemReviews() {
        return itemReviews;
    }
    public void removeEventReview(EventReviewResponse review){
        List<EventReviewResponse> reviews = eventReviews.getValue();
        reviews.removeIf(item -> item.getId().equals(review.getId()));
        eventReviews.setValue(reviews);
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
