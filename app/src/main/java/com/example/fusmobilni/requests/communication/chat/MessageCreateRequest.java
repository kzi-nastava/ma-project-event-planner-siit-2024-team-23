package com.example.fusmobilni.requests.communication.chat;

public class MessageCreateRequest {
    public Long senderId;
    public Long recipientId;
    public Long chatId;
    public String content;

    public MessageCreateRequest(Long senderId, Long recipientId, Long chatId, String content) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.chatId = chatId;
        this.content = content;
    }
}
