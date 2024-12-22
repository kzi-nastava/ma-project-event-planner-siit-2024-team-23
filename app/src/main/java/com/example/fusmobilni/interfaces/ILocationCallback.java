package com.example.fusmobilni.interfaces;

public interface ILocationCallback {
    void onLocationSuccess(String lat, String lon);
    void onLocationFailure(Throwable t);
}
