package com.example.fusmobilni.interfaces;

import com.example.fusmobilni.responses.auth.LoginResponse;

public interface ILoginCallback {
    void onSuccess(LoginResponse response);
    void onFailure(Throwable throwable);
}
