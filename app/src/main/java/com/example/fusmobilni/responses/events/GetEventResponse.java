package com.example.fusmobilni.responses.events;

import java.time.LocalDate;
import java.time.LocalTime;

public class GetEventResponse {
    private Long id;
    private String title;
    private String description;
    private int maxParticipants;
    private boolean isPublic;

    private String date;
    private String time;

    public GetEventResponse(Long id, String title, String description, int maxParticipants, String date, boolean isPublic, String time) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.date = date;
        this.isPublic = isPublic;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
