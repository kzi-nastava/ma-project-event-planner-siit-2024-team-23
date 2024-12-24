package com.example.fusmobilni.interfaces;

import com.example.fusmobilni.responses.events.home.EventsHomeResponse;

public interface ICalendarCallback {
    void onCalendarDateClick(EventsHomeResponse events);
}
