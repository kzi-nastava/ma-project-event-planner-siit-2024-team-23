package com.example.fusmobilni.responses.events.home;

import java.util.List;

public class EventsPreviewResponse {
    private List<EventPreviewResponse> previews;

    public EventsPreviewResponse(List<EventPreviewResponse> previews) {
        this.previews = previews;
    }

    public List<EventPreviewResponse> getPreviews() {
        return previews;
    }

    public void setPreviews(List<EventPreviewResponse> previews) {
        this.previews = previews;
    }
}
