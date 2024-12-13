package com.example.fusmobilni.responses.events.filter;

import com.example.fusmobilni.responses.events.EventTypeResponse;
import com.example.fusmobilni.responses.location.LocationResponse;

public class EventPaginationResponse {
    public Long id;
    public boolean isPublic;
    public String title;
    public String date;
    public String description;
    public LocationResponse location;
    public EventTypeResponse type;
    public int numberGoing;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public LocationResponse getLocation() {
        return location;
    }

    public void setLocation(LocationResponse location) {
        this.location = location;
    }

    public int getNumberGoing() {
        return numberGoing;
    }

    public void setNumberGoing(int numberGoing) {
        this.numberGoing = numberGoing;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EventTypeResponse getType() {
        return type;
    }

    public void setType(EventTypeResponse type) {
        this.type = type;
    }

    public EventPaginationResponse(String date, String description, Long id, boolean isPublic, LocationResponse location, int numberGoing, String title, EventTypeResponse type) {
        this.date = date;
        this.description = description;
        this.id = id;
        this.isPublic = isPublic;
        this.location = location;
        this.numberGoing = numberGoing;
        this.title = title;
        this.type = type;
    }
}
