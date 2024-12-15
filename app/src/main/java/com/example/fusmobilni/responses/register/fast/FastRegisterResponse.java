package com.example.fusmobilni.responses.register.fast;

import java.util.List;

public class FastRegisterResponse {
    public Long id;
    public String firstName;
    public String surname;
    public String email;
    public List<String> image;
    public String role;

    public FastRegisterResponse(String email, String firstName, Long id, List<String> image, String role, String surname) {
        this.email = email;
        this.firstName = firstName;
        this.id = id;
        this.image = image;
        this.role = role;
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
