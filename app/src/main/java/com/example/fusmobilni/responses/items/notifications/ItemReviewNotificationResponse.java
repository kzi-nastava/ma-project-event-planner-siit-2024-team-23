package com.example.fusmobilni.responses.items.notifications;

import com.example.fusmobilni.model.UserNotification;

public class ItemReviewNotificationResponse extends UserNotification {
    public Long id;
    public String eoImage;

    public String type;

    public ItemReviewNotificationResponse(String content, boolean seen, String timeStamp) {
        super(content, seen, timeStamp);
    }

    public ItemReviewNotificationResponse(String content, String eoImage, Long id, boolean seen, String timeStamp, String type) {
        this.content = content;
        this.eoImage = eoImage;
        this.id = id;
        this.seen = seen;
        this.timeStamp = timeStamp;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEoImage() {
        return eoImage;
    }

    public void setEoImage(String eoImage) {
        this.eoImage = eoImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
