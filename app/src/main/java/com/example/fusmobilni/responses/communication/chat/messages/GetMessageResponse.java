package com.example.fusmobilni.responses.communication.chat.messages;

public class GetMessageResponse {
    public Long id;
    public Long senderId;
    public Long recipientId;

    public GetMessageResponse(Long id, Long senderId, Long recipientId, String content) {
        this.id = id;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
    }

    public String content;
}
