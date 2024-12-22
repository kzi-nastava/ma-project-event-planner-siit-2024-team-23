package com.example.fusmobilni.responses.auth;

import java.util.Optional;

public class UserAvatarResponse {
    private String avatar;

    public UserAvatarResponse(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
