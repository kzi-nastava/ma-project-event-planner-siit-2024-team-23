package com.example.fusmobilni.model.enums;

import androidx.annotation.NonNull;

public enum UserType {
    EVENT_ORGANIZER,
    SERVICE_PROVIDER,
    ADMIN,
    AUTHENTICATED_USER,
    UNAUTHENTICATED_USER;

    @NonNull
    @Override
    public String toString() {
        switch (this){
            case ADMIN:
                return "Admin";
            case AUTHENTICATED_USER:
                return "Authenticated user";
            case EVENT_ORGANIZER:
                return "Event organizer";
            case SERVICE_PROVIDER:
                return "Service provider";
            default:
                return super.toString();
        }
    }
}
