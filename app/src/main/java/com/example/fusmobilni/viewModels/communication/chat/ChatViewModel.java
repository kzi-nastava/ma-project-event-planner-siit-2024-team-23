package com.example.fusmobilni.viewModels.communication.chat;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChatViewModel extends ViewModel {
    private MutableLiveData<Long> chatId = new MutableLiveData<>(null);
    private MutableLiveData<Long> recipientId = new MutableLiveData<>(null);
    private MutableLiveData<String> recipientName = new MutableLiveData<>(null);
    private MutableLiveData<Uri> recipientAvatar = new MutableLiveData<Uri>(null);

    public MutableLiveData<Long> getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId.setValue(chatId);
    }

    public MutableLiveData<String> getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName.setValue(recipientName);
    }

    public MutableLiveData<Uri> getRecipientAvatar() {
        return recipientAvatar;
    }

    public void setRecipientAvatar(Uri recipientAvatar) {
        this.recipientAvatar.setValue(recipientAvatar);
    }

    public void populate(Long chatId, String recipientName, Uri recipientAvatar, Long recipientId) {
        this.setChatId(chatId);
        this.setRecipientAvatar(recipientAvatar);
        this.setRecipientName(recipientName);
        this.setRecipientId(recipientId);
    }

    public MutableLiveData<Long> getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId.setValue(recipientId);
    }
}
