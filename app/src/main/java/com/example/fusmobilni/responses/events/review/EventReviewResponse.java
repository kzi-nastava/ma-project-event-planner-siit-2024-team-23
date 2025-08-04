package com.example.fusmobilni.responses.events.review;

public class EventReviewResponse {
    public Long id;
    public int grade;
    public EventReviewUserResponse user;
    public String content;
    public String date;

    public EventReviewResponse(String content, String date, int grade, Long id, EventReviewUserResponse user) {
        this.content = content;
        this.date = date;
        this.grade = grade;
        this.id = id;
        this.user = user;
    }

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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventReviewUserResponse getUser() {
        return user;
    }

    public void setUser(EventReviewUserResponse user) {
        this.user = user;
    }
}
