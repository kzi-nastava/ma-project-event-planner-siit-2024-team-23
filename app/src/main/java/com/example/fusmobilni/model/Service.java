package com.example.fusmobilni.model;

public class Service {
    private String _name;
    private String _description;
    private String _location;
    private String _category;
    public Service(String description, String name,String location,String category) {
        this._description = description;
        this._name = name;
        this._category = category;
        this._location = location;
    }

    public String getCategory() {
        return _category;
    }

    public void setCategory(String _category) {
        this._category = _category;
    }

    public String getLocation() {
        return _location;
    }

    public void setLocation(String _location) {
        this._location = _location;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        this._description = description;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }
}
