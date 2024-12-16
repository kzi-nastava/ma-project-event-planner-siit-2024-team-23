package com.example.fusmobilni.requests.events.event;

public class CreateEventRequest {
    private String title;

    private String description;
    private int maxParticipants;
    private boolean isPublic;

    private String date;
    private String time;
    private Long eventOrganizerId;
    private Long eventTypeId;

    public CreateEventRequest(String title, String description, int maxParticipants, boolean isPublic, String date, String time, Long eventOrganizerId, Long eventTypeId) {
        this.title = title;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.isPublic = isPublic;
        this.date = date;
        this.time = time;
        this.eventOrganizerId = eventOrganizerId;
        this.eventTypeId = eventTypeId;
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

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
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

    public Long getEventOrganizerId() {
        return eventOrganizerId;
    }

    public void setEventOrganizerId(Long eventOrganizerId) {
        this.eventOrganizerId = eventOrganizerId;
    }

    public Long getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Long eventTypeId) {
        this.eventTypeId = eventTypeId;
    }
}
