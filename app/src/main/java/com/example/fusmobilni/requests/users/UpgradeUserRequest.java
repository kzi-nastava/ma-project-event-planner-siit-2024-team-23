package com.example.fusmobilni.requests.users;

import com.example.fusmobilni.model.enums.UserType;

public class UpgradeUserRequest {
    public Long id;
    public UserType role;
    public String companyName;
    public String description;

    public UpgradeUserRequest(String companyName, String description, Long id, UserType role) {
        this.companyName = companyName;
        this.description = description;
        this.id = id;
        this.role = role;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
    }
}
