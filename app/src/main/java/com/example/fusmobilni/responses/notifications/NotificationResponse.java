package com.example.fusmobilni.responses.notifications;

public class NotificationResponse {
    public Long id;
    public String image;
    public String content;
    public boolean seen;
    public String timeStamp;

    public NotificationResponse(String content, Long id, String image, boolean seen, String timeStamp) {
        this.content = content;
        this.id = id;
        this.image = image;
        this.seen = seen;
        this.timeStamp = timeStamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
