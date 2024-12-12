package com.example.fusmobilni.model.items.service;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Service implements Parcelable {
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

    protected Service(Parcel in) {
        _name = in.readString();
        _description = in.readString();
        _location = in.readString();
        _category = in.readString();
    }

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(_name);
        dest.writeString(_description);
        dest.writeString(_category);
        dest.writeString(_location);
    }
}
