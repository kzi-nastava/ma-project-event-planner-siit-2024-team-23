package com.example.fusmobilni.model;

public class Category {
    private int _id;
    private int _activeIconResId;
    private  int _inactiveIconResId;
    private String _name;

    public Category(int id,String name, int activeIconResId, int inactiveIconResId) {
        this._id = id;
        this._activeIconResId = activeIconResId;
        this._name = name;
        this._inactiveIconResId = inactiveIconResId;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public int getInactiveIconResId() {
        return _inactiveIconResId;
    }

    public void setInactiveIconResId(int _inactiveIconResId) {
        this._inactiveIconResId = _inactiveIconResId;
    }

    public String getName() {
        return _name;
    }

    public int getActiveIconResId() {
        return _activeIconResId;
    }

    public void setActiveIconResId(int iconResId) {
        this._activeIconResId = iconResId;
    }


    public void setName(String _name) {
        this._name = _name;
    }
}
