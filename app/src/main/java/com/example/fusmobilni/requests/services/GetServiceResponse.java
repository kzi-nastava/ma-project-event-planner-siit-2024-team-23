package com.example.fusmobilni.requests.services;

import com.example.fusmobilni.model.enums.ReservationConfirmation;
import java.util.List;

public class GetServiceResponse {
    private Long id;
    private String name;
    private String description;
    private Long categoryId;
    private List<Long> eventTypeIds;
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
    List<String> images;

    public GetServiceResponse(Long id, List<String> images, ReservationConfirmation reservationConfirmation, int cancellationDeadline, int reservationDeadline, int duration, boolean isVisible, boolean isAvailable, String specificities, Long serviceProviderId, double discount, double price, List<Long> eventTypeIds, Long categoryId, String description, String name) {
        this.id = id;
        this.images = images;
        this.reservationConfirmation = reservationConfirmation;
        this.cancellationDeadline = cancellationDeadline;
        this.reservationDeadline = reservationDeadline;
        this.duration = duration;
        this.isVisible = isVisible;
        this.isAvailable = isAvailable;
        this.specificities = specificities;
        this.serviceProviderId = serviceProviderId;
        this.discount = discount;
        this.price = price;
        this.eventTypeIds = eventTypeIds;
        this.categoryId = categoryId;
        this.description = description;
        this.name = name;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<Long> getEventTypeIds() {
        return eventTypeIds;
    }

    public void setEventTypeIds(List<Long> eventTypeIds) {
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

    public String getSpecificities() {
        return specificities;
    }

    public void setSpecificities(String specificities) {
        this.specificities = specificities;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
