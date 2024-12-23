package com.example.fusmobilni.model.communication.chat;

import android.net.Uri;

public class ChatOverview {
    public String name;
    public Uri avatar;
    public String latestMessage;

    public ChatOverview(String name, Uri avatar, String latestMessage) {
        this.name = name;
        this.avatar = avatar;
        this.latestMessage = latestMessage;
    }
}
