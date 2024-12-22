package com.example.fusmobilni.responses.auth;

import com.example.fusmobilni.model.enums.UserType;

public class LoginResponse {
    private Long id;
    private String firstName;
    private String surname;
    private String email;
    private UserType role;
    private String jwt;

    public LoginResponse(Long id, String firstName, String surname, String email, UserType role, String jwt) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.role = role;
        this.jwt = jwt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
