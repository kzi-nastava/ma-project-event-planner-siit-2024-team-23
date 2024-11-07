package com.example.fusmobilni.model;

public class Event {
    private String _title;
    private String _month;
    private String _year;
    private String _day;

    private String _location;
    public Event(String day, String month, String title, String year,String location) {
        this._day = day;
        this._month = month;
        this._title = title;
        this._year = year;
        this._location = location;
    }

    public String get_day() {
        return _day;
    }

    public void set_day(String day) {
        this._day = day;
    }

    public String get_month() {
        return _month;
    }

    public void setMonth(String month) {
        this._month = month;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        this._title = title;
    }

    public String getLocation() {
        return _location;
    }

    public void setLocation(String _location) {
        this._location = _location;
    }

    public String getYear() {
        return _year;
    }

    public void setYear(String year) {
        this._year = year;
    }
}
