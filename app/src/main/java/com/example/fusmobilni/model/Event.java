package com.example.fusmobilni.model;

import java.util.HashMap;

public class Event {
    private static HashMap MonthMap = new HashMap<String,String>();

    static {
        MonthMap.put("1", "Jan");
        MonthMap.put("2", "Feb");
        MonthMap.put("3", "Mar");
        MonthMap.put("4", "Apr");
        MonthMap.put("5", "May");
        MonthMap.put("6", "Jun");
        MonthMap.put("7", "Jul");
        MonthMap.put("8", "Aug");
        MonthMap.put("9", "Sept");
        MonthMap.put("10", "Oct");
        MonthMap.put("11", "Nov");
        MonthMap.put("12", "Dec");
    }

    private String _title;
    private String _month;
    private String _year;
    private String _day;
    private String _date;
    private String _location;
    private String _category;
    public Event(String title,String date,String location,String category) {
        this._title = title;
        this._date = date;
        this._location = location;
        this._category = category;
        String[] dateParts = date.split("-");
        this._day = dateParts[0];
        this._month =(String) MonthMap.get(dateParts[1]);
        this._year = dateParts[2];
    }

    public String getDate() {
        return _date;
    }

    public void setDate(String _date) {
        this._date = _date;
    }

    public String getCategory() {
        return _category;
    }

    public void setCategory(String _category) {
        this._category = _category;
    }

    public String getDay() {
        return _day;
    }

    public void setDay(String day) {
        this._day = day;
    }

    public String getMonth() {
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
