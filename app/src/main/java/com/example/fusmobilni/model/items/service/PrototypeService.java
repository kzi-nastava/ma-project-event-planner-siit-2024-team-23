package com.example.fusmobilni.model.items.service;

import android.net.Uri;

import java.util.ArrayList;

public class PrototypeService {

    private String category;
    private String name;
    private String description;
    private String specificities;
    private double price;
    private double discount;
    private ArrayList<Uri> imageUris = new ArrayList<>();
    private ArrayList<String> eventTypes = new ArrayList<>();
    private boolean isVisible;
    private boolean isAvailable;
    private Integer duration;
    private int cancellationDeadline;
    private int reservationDeadline;
    private boolean isAutomaticReservation;
    private boolean isDeleted;

    public PrototypeService(String category, String name, String description, String specificities,
                            double price, double discount, boolean isVisible, boolean isAvailable,
                            Integer duration, int cancellationDeadline, int reservationDeadline,
                            boolean isAutomaticReservation, ArrayList<Uri> imageUris,
                            ArrayList<String> eventTypes) {

        this.category = category;
        this.name = name;
        this.description = description;
        this.specificities = specificities;
        this.price = price;
        this.discount = discount;
        this.imageUris = imageUris;
        this.isVisible = isVisible;
        this.isAvailable = isAvailable;
        this.duration = duration;
        this.cancellationDeadline = cancellationDeadline;
        this.reservationDeadline = reservationDeadline;
        this.isAutomaticReservation = isAutomaticReservation;
        isDeleted = false;
        this.eventTypes = eventTypes;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecificities() {
        return specificities;
    }

    public void setSpecificities(String specificities) {
        this.specificities = specificities;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public ArrayList<Uri> getImageUris() {
        return imageUris;
    }

    public void setImageUris(ArrayList<Uri> imageUris) {
        this.imageUris = imageUris;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public int getCancellationDeadline() {
        return cancellationDeadline;
    }

    public void setCancellationDeadline(int cancellationDeadline) {
        this.cancellationDeadline = cancellationDeadline;
    }

    public int getReservationDeadline() {
        return reservationDeadline;
    }

    public void setReservationDeadline(int reservationDeadline) {
        this.reservationDeadline = reservationDeadline;
    }

    public boolean isAutomaticReservation() {
        return isAutomaticReservation;
    }

    public void setAutomaticReservation(boolean automaticReservation) {
        isAutomaticReservation = automaticReservation;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public ArrayList<String> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(ArrayList<String> eventTypes) {
        this.eventTypes = eventTypes;
    }
}
