package com.example.fusmobilni.interfaces;

import com.example.fusmobilni.responses.users.UserInfoResponse;

public interface IUpdateProfileCallback {
    void onSuccess(UserInfoResponse response);
    void onFailure(Throwable throwable);
}
