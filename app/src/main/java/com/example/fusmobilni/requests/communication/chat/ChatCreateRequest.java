package com.example.fusmobilni.requests.communication.chat;

public class ChatCreateRequest {
    public Long primaryUserId;

    public ChatCreateRequest(Long primaryUserId, Long secondaryUserId) {
        this.primaryUserId = primaryUserId;
        this.secondaryUserId = secondaryUserId;
    }

    public Long secondaryUserId;
}
