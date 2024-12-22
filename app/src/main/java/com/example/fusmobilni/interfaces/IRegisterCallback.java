package com.example.fusmobilni.interfaces;

import com.example.fusmobilni.responses.register.regular.RegisterResponse;

public interface IRegisterCallback {
    void onSuccess(RegisterResponse response);
    void onFailure(Throwable throwable);
}
