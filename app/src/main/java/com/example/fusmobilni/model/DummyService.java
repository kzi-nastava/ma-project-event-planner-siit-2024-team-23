package com.example.fusmobilni.model;

import android.net.Uri;

public class DummyService {

    private String title;
    private String description;

    public DummyService(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
