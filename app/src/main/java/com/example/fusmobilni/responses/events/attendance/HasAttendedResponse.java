package com.example.fusmobilni.responses.events.attendance;

public class HasAttendedResponse {
    public boolean hasAttended;

    public HasAttendedResponse(boolean hasAttended) {
        this.hasAttended = hasAttended;
    }

    public boolean isHasAttended() {
        return hasAttended;
    }

    public void setHasAttended(boolean hasAttended) {
        this.hasAttended = hasAttended;
    }
}
