package com.example.fusmobilni.model.users;

import com.example.fusmobilni.model.enums.UserType;

public class User {
    private Long _id;
    private String _firstName;
    private String _lastName;
    private String _email;
    private String _avatar;
    private String _jwt;
    private UserType _role;

    public Long getId() {
        return _id;
    }

    public void setId(Long _id) {
        this._id = _id;
    }

    public String getFirstName() {
        return _firstName;
    }

    public void setFirstName(String _firstName) {
        this._firstName = _firstName;
    }

    public String getLastName() {
        return _lastName;
    }

    public void setLastName(String _lastName) {
        this._lastName = _lastName;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String _email) {
        this._email = _email;
    }

    public String getAvatar() {
        return _avatar;
    }

    public void setAvatar(String _avatar) {
        this._avatar = _avatar;
    }

    public String getJwt() {
        return _jwt;
    }

    public void setJwt(String _jwt) {
        this._jwt = _jwt;
    }

    public UserType getRole() {
        return _role;
    }

    public void setRole(UserType _role) {
        this._role = _role;
    }
}
