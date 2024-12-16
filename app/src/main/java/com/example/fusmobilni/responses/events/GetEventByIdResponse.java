package com.example.fusmobilni.responses.events;

import com.example.fusmobilni.model.event.eventTypes.EventType;

import java.time.LocalDate;
import java.time.LocalTime;

public class GetEventByIdResponse {
    private Long id;
    private boolean isPublic;
    private String title;
    private String description;
    private EventTypeResponse type;
    private String date;
    private String time;
    private int maxParticipants;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
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

    public EventTypeResponse getType() {
        return type;
    }

    public void setType(EventTypeResponse type) {
        this.type = type;
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

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public GetEventByIdResponse(Long id, boolean isPublic, String title, String description, EventTypeResponse type, String date, String time, int maxParticipants) {
        this.id = id;
        this.isPublic = isPublic;
        this.title = title;
        this.description = description;
        this.type = type;
        this.date = date;
        this.time = time;
        this.maxParticipants = maxParticipants;
    }
}
