package com.example.fusmobilni.responses.auth;

public class SuspensionResponse {
    String message;
    String timeTillExpiry;

    public SuspensionResponse(String message, String timeTillExpiry) {
        this.message = message;
        this.timeTillExpiry = timeTillExpiry;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeTillExpiry() {
        return timeTillExpiry;
    }

    public void setTimeTillExpiry(String timeTillExpiry) {
        this.timeTillExpiry = timeTillExpiry;
    }
}
