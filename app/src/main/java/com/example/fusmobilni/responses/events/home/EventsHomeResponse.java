package com.example.fusmobilni.responses.events.home;

import java.util.List;

public class EventsHomeResponse {
    public List<EventHomeResponse> events;

    public EventsHomeResponse(List<EventHomeResponse> events) {
        this.events = events;
    }

    public List<EventHomeResponse> getEvents() {
        return events;
    }

    public void setEvents(List<EventHomeResponse> events) {
        this.events = events;
    }
}
