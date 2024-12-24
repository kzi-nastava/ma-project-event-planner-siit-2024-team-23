package com.example.fusmobilni.responses.users;

import com.example.fusmobilni.responses.events.EventTypeResponse;
import com.example.fusmobilni.responses.location.LocationResponse;

import java.time.LocalDate;

public class UserFavoriteEventResponse {
    public Long id;
    public boolean isPublic;
    public String title;
    public String date;
    public String description;
    public String image;
    public LocationResponse location;
    public EventTypeResponse type;
    public int numberGoing;

    public UserFavoriteEventResponse(Long id, boolean isPublic, String title, String date, String description, String image, LocationResponse location, EventTypeResponse type, int numberGoing) {
        this.id = id;
        this.isPublic = isPublic;
        this.title = title;
        this.date = date;
        this.description = description;
        this.image = image;
        this.location = location;
        this.type = type;
        this.numberGoing = numberGoing;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocationResponse getLocation() {
        return location;
    }

    public void setLocation(LocationResponse location) {
        this.location = location;
    }

    public EventTypeResponse getType() {
        return type;
    }

    public void setType(EventTypeResponse type) {
        this.type = type;
    }

    public int getNumberGoing() {
        return numberGoing;
    }

    public void setNumberGoing(int numberGoing) {
        this.numberGoing = numberGoing;
    }
}
