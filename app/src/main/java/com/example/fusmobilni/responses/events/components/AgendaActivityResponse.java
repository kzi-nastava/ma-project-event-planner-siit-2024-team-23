package com.example.fusmobilni.responses.events.components;

import com.example.fusmobilni.model.event.AgendaActivity;

import java.sql.Time;

public class AgendaActivityResponse {
    public Long id;
    public String title;
    public String description;
    public String startTime;
    public String endTime;


    public AgendaActivityResponse(Long id, String endTime, String description, String title, String startTime) {
        this.id = id;
        this.endTime = endTime;
        this.description = description;
        this.title = title;
        this.startTime = startTime;
    }

    public AgendaActivity toAgenda() {
        return new AgendaActivity(id, Time.valueOf(startTime), Time.valueOf(endTime), title, description);
    }
}
