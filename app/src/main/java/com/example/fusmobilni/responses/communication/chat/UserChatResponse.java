package com.example.fusmobilni.responses.communication.chat;

public class UserChatResponse {
    public Long id;

    public UserChatResponse(Long id, String avatar, String name) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
    }

    public String name;
    public String avatar;
}
