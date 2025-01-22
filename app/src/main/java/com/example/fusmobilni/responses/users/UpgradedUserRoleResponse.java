package com.example.fusmobilni.responses.users;

import com.example.fusmobilni.model.enums.UserType;

public class UpgradedUserRoleResponse {
    public UserType role;

    public UpgradedUserRoleResponse(UserType role) {
        this.role = role;
    }

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
    }
}
