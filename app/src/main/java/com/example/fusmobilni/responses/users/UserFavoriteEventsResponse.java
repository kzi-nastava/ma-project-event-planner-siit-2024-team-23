package com.example.fusmobilni.responses.users;

import java.util.ArrayList;

public class UserFavoriteEventsResponse {
    private ArrayList<UserFavoriteEventResponse> events;

    public UserFavoriteEventsResponse(ArrayList<UserFavoriteEventResponse> events) {
        this.events = events;
    }

    public ArrayList<UserFavoriteEventResponse> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<UserFavoriteEventResponse> events) {
        this.events = events;
    }
}
