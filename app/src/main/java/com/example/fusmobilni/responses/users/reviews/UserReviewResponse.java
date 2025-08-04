package com.example.fusmobilni.responses.users.reviews;

public class UserReviewResponse {
    public Long id;
    public int rating;
    public String content;
    public String date;
    public UserReviewUserResponse rater;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserReviewUserResponse getRater() {
        return rater;
    }

    public void setRater(UserReviewUserResponse rater) {
        this.rater = rater;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public UserReviewResponse(String content, String date, Long id, UserReviewUserResponse rater, int rating) {
        this.content = content;
        this.date = date;
        this.id = id;
        this.rater = rater;
        this.rating = rating;
    }
}
