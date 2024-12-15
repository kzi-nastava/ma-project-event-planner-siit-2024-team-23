package com.example.fusmobilni.responses.items;

import com.example.fusmobilni.responses.auth.EventOrganizerResponse;

import java.time.LocalDate;

public class ItemGradeResponse {
    public Long id;
    public int grade;
    public EventOrganizerResponse eventOrganizer;
    public String content;
    public LocalDate date;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public EventOrganizerResponse getEventOrganizer() {
        return eventOrganizer;
    }

    public void setEventOrganizer(EventOrganizerResponse eventOrganizer) {
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

    public ItemGradeResponse(String content, LocalDate date, EventOrganizerResponse eventOrganizer, int grade, Long id) {
        this.content = content;
        this.date = date;
        this.eventOrganizer = eventOrganizer;
        this.grade = grade;
        this.id = id;
    }
}
