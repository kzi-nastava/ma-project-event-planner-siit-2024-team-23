package com.example.fusmobilni.responses.events;

import java.time.LocalDate;
import java.time.LocalTime;

public class GetEventResponse {
    private Long id;
    private String title;
    private String description;
    private int maxParticipants;
    private boolean isAvailable;
    private LocalDate date;
    private LocalTime time;

}
