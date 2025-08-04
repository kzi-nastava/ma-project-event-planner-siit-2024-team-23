package com.example.fusmobilni.interfaces;

import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.auth.SuspensionResponse;

public interface ILoginCallback {
    void onSuccess(LoginResponse response);

    void onFailure(Throwable throwable);

    void onSuspension(SuspensionResponse response);
}
