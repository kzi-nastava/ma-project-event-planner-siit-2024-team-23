package com.example.fusmobilni.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Product implements Parcelable {
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

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    protected Product(Parcel in){
        _name = in.readString();
        _description = in.readString();
        _price = in.readDouble();
        _location = in.readString();
        _category = in.readString();
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(_name);
        dest.writeString(_description);
        dest.writeDouble(_price);
        dest.writeString(_category);
        dest.writeString(_location);
    }
}
