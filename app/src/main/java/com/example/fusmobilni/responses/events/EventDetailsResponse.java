package com.example.fusmobilni.responses.events;

import com.example.fusmobilni.requests.events.event.AgendaActivitiesResponse;
import com.example.fusmobilni.responses.auth.EventOrganizerResponse;
import com.example.fusmobilni.responses.events.components.AgendaActivityResponse;
import com.example.fusmobilni.responses.location.LocationResponse;

import java.util.List;

public class EventDetailsResponse {
    private Long id;
    private boolean isPublic;
    private String title;
    private String description;
    private LocationResponse location;
    private EventOrganizerResponse eventOrganizer;
    private List<AgendaActivityResponse> agendas;
    private EventTypeResponse type;
    private String date;
    private String time;
    private int maxParticipants;

    public EventDetailsResponse(Long id, boolean isPublic, String title, String description, LocationResponse location,
                                EventOrganizerResponse eventOrganizer, EventTypeResponse type, String date, String time,
                                int maxParticipants, List<AgendaActivityResponse> agendas) {
        this.id = id;
        this.isPublic = isPublic;
        this.title = title;
        this.description = description;
        this.location = location;
        this.eventOrganizer = eventOrganizer;
        this.type = type;
        this.date = date;
        this.time = time;
        this.maxParticipants = maxParticipants;
        this.agendas = agendas;
    }

    public List<AgendaActivityResponse> getAgendas() {
        return agendas;
    }

    public void setAgendas(List<AgendaActivityResponse> agendas) {
        this.agendas = agendas;
    }

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

    public LocationResponse getLocation() {
        return location;
    }

    public void setLocation(LocationResponse location) {
        this.location = location;
    }

    public EventOrganizerResponse getEventOrganizer() {
        return eventOrganizer;
    }

    public void setEventOrganizer(EventOrganizerResponse eventOrganizer) {
        this.eventOrganizer = eventOrganizer;
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
}
