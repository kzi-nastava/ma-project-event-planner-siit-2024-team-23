package com.example.fusmobilni.model.event;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class Event implements Parcelable {
    private static HashMap MonthMap = new HashMap<String,String>();

    static {
        MonthMap.put("01", "Jan");
        MonthMap.put("02", "Feb");
        MonthMap.put("03", "Mar");
        MonthMap.put("04", "Apr");
        MonthMap.put("05", "May");
        MonthMap.put("06", "Jun");
        MonthMap.put("07", "Jul");
        MonthMap.put("08", "Aug");
        MonthMap.put("09", "Sept");
        MonthMap.put("10", "Oct");
        MonthMap.put("11", "Nov");
        MonthMap.put("12", "Dec");
    }

    private String _title;
    private String _description;
    private String _month;
    private String _year;
    private String _day;
    private String _date;
    private String _location;
    private String _category;
    public Event(String description, String date,String title,String location,String category) {
        this._title = title;
        _description = description;
        this._date = date;
        this._location = location;
        this._category = category;
        String[] dateParts = date.split("-");
        this._day = dateParts[0];
        this._month =(String) MonthMap.get(dateParts[1]);
        this._year = dateParts[2];
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public String getDate() {
        return _date;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String _description) {
        this._description = _description;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(_title);
        dest.writeString(_month);
        dest.writeString(_year);
        dest.writeString(_day);
        dest.writeString(_date);
        dest.writeString(_location);
        dest.writeString(_category);
    }
    protected Event(Parcel in){
        _title = in.readString();
        _month = in.readString();
        _year = in.readString();
        _day = in.readString();
        _day = in.readString();
        _location = in.readString();
        _category = in.readString();
    }
}