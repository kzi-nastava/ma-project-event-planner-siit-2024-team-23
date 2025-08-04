package com.example.fusmobilni.responses.events;

public class EventTypeResponse {
    public Long id;
    public String name;
    public String description;

    public EventTypeResponse(String description, Long id, String name) {
        this.description = description;
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
