package com.example.fusmobilni.model;

public class UserNotification {
    public String content;
    public boolean seen;
    public String timeStamp;

    public UserNotification() {
    }

    public UserNotification(String content, boolean seen, String timeStamp) {
        this.content = content;
        this.seen = seen;
        this.timeStamp = timeStamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
