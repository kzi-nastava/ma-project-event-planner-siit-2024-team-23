package com.example.fusmobilni.requests.services;

import com.example.fusmobilni.model.enums.ReservationConfirmation;

public class UpdateServiceRequest {
    private String name;
    private String description;
    private String eventTypeIds;
    private double price;
    private double discount;
    private Long serviceProviderId;
    private String specificities;
    private boolean isAvailable;
    private boolean isVisible;
    private int duration;
    private int reservationDeadline;
    private int cancellationDeadline;
    private ReservationConfirmation reservationConfirmation;

    public UpdateServiceRequest(String name, String description, String eventTypeIds,
                                double price, double discount, Long serviceProviderId, String specificities,
                                boolean isAvailable, boolean isVisible, int duration, int reservationDeadline,
                                int cancellationDeadline, ReservationConfirmation reservationConfirmation) {
        this.name = name;
        this.description = description;
        this.cancellationDeadline = cancellationDeadline;
        this.eventTypeIds = eventTypeIds;
        this.reservationConfirmation = reservationConfirmation;
        this.reservationDeadline = reservationDeadline;
        this.specificities = specificities;
        this.isVisible = isVisible;
        this.isAvailable = isAvailable;
        this.duration = duration;
        this.discount = discount;
        this.price = price;
        this.serviceProviderId = serviceProviderId;
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

    public String getEventTypeIds() {
        return eventTypeIds;
    }

    public void setEventTypeIds(String eventTypeIds) {
        this.eventTypeIds = eventTypeIds;
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

    public Long getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(Long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getReservationDeadline() {
        return reservationDeadline;
    }

    public void setReservationDeadline(int reservationDeadline) {
        this.reservationDeadline = reservationDeadline;
    }

    public int getCancellationDeadline() {
        return cancellationDeadline;
    }

    public void setCancellationDeadline(int cancellationDeadline) {
        this.cancellationDeadline = cancellationDeadline;
    }

    public ReservationConfirmation getReservationConfirmation() {
        return reservationConfirmation;
    }

    public void setReservationConfirmation(ReservationConfirmation reservationConfirmation) {
        this.reservationConfirmation = reservationConfirmation;
    }
}
