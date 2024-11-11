package com.example.fusmobilni.model;

public class Product {
    private String _name;
    private String _description;
    private double _price;
    private String _location;
    private String _category;
    public Product(String name, String description,double price,String location,String category) {
        this._description = description;
        this._name = name;
        this._price = price;
        this._location = location;
        this._category = category;
    }

    public String getCategory() {
        return _category;
    }

    public void setCategory(String category) {
        this._category = category;
    }

    public double getPrice() {
        return _price;
    }

    public void setPrice(double price) {
        this._price = price;
    }

    public String getLocation() {
        return _location;
    }

    public void setLocation(String location) {
        this._location = location;
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

    public void setName(String _name) {
        this._name = _name;
    }
}
