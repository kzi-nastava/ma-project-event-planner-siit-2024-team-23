package com.example.fusmobilni.responses.users;

import java.util.ArrayList;

public class UserFavoriteServicesResponse {
    private ArrayList<UserFavoriteServiceResponse> responses;

    public ArrayList<UserFavoriteServiceResponse> getResponses() {
        return responses;
    }

    public void setResponses(ArrayList<UserFavoriteServiceResponse> responses) {
        this.responses = responses;
    }

    public UserFavoriteServicesResponse(ArrayList<UserFavoriteServiceResponse> responses) {
        this.responses = responses;
    }
}
