package com.example.fusmobilni.responses.items;

import com.example.fusmobilni.responses.auth.EventOrganizerResponse;

import java.time.LocalDate;

public class ItemGradeResponse {
    public Long id;
    public int grade;
    public EventOrganizerReviewResponse eventOrganizer;
    public String content;
    public String date;

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

    public EventOrganizerReviewResponse getEventOrganizer() {
        return eventOrganizer;
    }

    public void setEventOrganizer(EventOrganizerReviewResponse eventOrganizer) {
        this.eventOrganizer = eventOrganizer;
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

    public ItemGradeResponse(String content, String date, EventOrganizerReviewResponse eventOrganizer, int grade, Long id) {
        this.content = content;
        this.date = date;
        this.eventOrganizer = eventOrganizer;
        this.grade = grade;
        this.id = id;
    }
}
