package com.example.fusmobilni.responses.communication.chat;

public class ChatGetResponse {
    public Long id;
    public UserChatResponse primaryUser;

    public ChatGetResponse(String latestMessageContent, UserChatResponse secondaryUser, UserChatResponse primaryUser, Long id) {
        this.latestMessageContent = latestMessageContent;
        this.secondaryUser = secondaryUser;
        this.primaryUser = primaryUser;
        this.id = id;
    }

    public UserChatResponse secondaryUser;
    public String latestMessageContent;
}
