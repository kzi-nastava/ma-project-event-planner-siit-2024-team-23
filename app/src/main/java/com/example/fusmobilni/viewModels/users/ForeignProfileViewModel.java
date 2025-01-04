package com.example.fusmobilni.viewModels.users;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.responses.events.review.EventReviewResponse;
import com.example.fusmobilni.responses.items.ItemReviewResponse;
import com.example.fusmobilni.responses.users.reviews.UserReviewResponse;

import java.util.ArrayList;
import java.util.List;

public class ForeignProfileViewModel extends ViewModel {
    private MutableLiveData<List<UserReviewResponse>> userReviews = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<List<ItemReviewResponse>> itemReviews = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<List<EventReviewResponse>> eventReviews = new MutableLiveData<>(new ArrayList<>());

    public void resetReviews() {
        userReviews.setValue(new ArrayList<>());
        itemReviews.setValue(new ArrayList<>());
        eventReviews.setValue(new ArrayList<>());
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

    public void setItemReviews(List<ItemReviewResponse> itemReviews) {
        this.itemReviews.setValue(itemReviews);
    }

    public MutableLiveData<List<UserReviewResponse>> getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(List<UserReviewResponse> userReviews) {
        this.userReviews.setValue(userReviews);
        ;
    }
}
