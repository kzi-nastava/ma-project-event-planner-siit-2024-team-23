package com.example.fusmobilni.requests.events.event;

public class CreateAgendaActivityRequest {

    public String title;
    public String description;
    public String startTime;
    public String endTime;
    public Long eventId;

    public CreateAgendaActivityRequest(String title, Long eventId, String endTime, String startTime, String description) {
        this.title = title;
        this.eventId = eventId;
        this.endTime = endTime;
        this.startTime = startTime;
        this.description = description;
    }
}
