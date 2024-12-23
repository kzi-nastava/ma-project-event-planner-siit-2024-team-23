package com.example.fusmobilni.interfaces;

import com.example.fusmobilni.model.event.eventTypes.EventType;

public interface IEventTypeCallback {
    void onSuccess(EventType eventType);
    void onFailure(Throwable throwable);
}
