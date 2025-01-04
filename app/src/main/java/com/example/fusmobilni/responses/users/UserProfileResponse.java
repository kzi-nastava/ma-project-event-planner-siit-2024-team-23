package com.example.fusmobilni.responses.users;

import com.example.fusmobilni.responses.events.review.EventReviewResponse;
import com.example.fusmobilni.responses.items.ItemReviewResponse;
import com.example.fusmobilni.responses.location.LocationResponse;
import com.example.fusmobilni.responses.users.reviews.UserReviewResponse;

import java.util.List;

public class UserProfileResponse {
    public Long id;
    public String email;
    public String firstName;
    public String lastName;
    public String role;
    public boolean gradable;
    public String userAvatar;
    public List<UserReviewResponse> userReviews;
    public List<ItemReviewResponse> itemReviews;
    public List<EventReviewResponse> eventReviews;
    public LocationResponse location;

    public UserProfileResponse(String email, List<EventReviewResponse> eventReviews, String firstName, boolean gradable, Long id, List<ItemReviewResponse> itemReviews, String lastName, LocationResponse location, String role, String userAvatar, List<UserReviewResponse> userReviews) {
        this.email = email;
        this.eventReviews = eventReviews;
        this.firstName = firstName;
        this.gradable = gradable;
        this.id = id;
        this.itemReviews = itemReviews;
        this.lastName = lastName;
        this.location = location;
        this.role = role;
        this.userAvatar = userAvatar;
        this.userReviews = userReviews;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEventReviews(List<EventReviewResponse> eventReviews) {
        this.eventReviews = eventReviews;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setGradable(boolean gradable) {
        this.gradable = gradable;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItemReviews(List<ItemReviewResponse> itemReviews) {
        this.itemReviews = itemReviews;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLocation(LocationResponse location) {
        this.location = location;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public void setUserReviews(List<UserReviewResponse> userReviews) {
        this.userReviews = userReviews;
    }

    public String getEmail() {
        return email;
    }

    public List<EventReviewResponse> getEventReviews() {
        return eventReviews;
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean isGradable() {
        return gradable;
    }

    public Long getId() {
        return id;
    }

    public List<ItemReviewResponse> getItemReviews() {
        return itemReviews;
    }

    public String getLastName() {
        return lastName;
    }

    public LocationResponse getLocation() {
        return location;
    }

    public String getRole() {
        return role;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public List<UserReviewResponse> getUserReviews() {
        return userReviews;
    }
}
