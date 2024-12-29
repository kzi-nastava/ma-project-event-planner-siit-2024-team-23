package com.example.fusmobilni.requests.products;

import com.example.fusmobilni.model.enums.ReservationConfirmation;

public class CreateProductRequest {
    private String name;
    private String description;
    private Long categoryId;
    private String eventTypeIds;
    private double price;
    private double discount;
    private Long serviceProviderId;
    private String specificities;
    private boolean isAvailable;
    private boolean isVisible;

    public CreateProductRequest(String name, String description, Long categoryId, String eventTypeIds,
                                double price, double discount, Long serviceProviderId, String specificities,
                                boolean isAvailable, boolean isVisible) {
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.eventTypeIds = eventTypeIds;
        this.specificities = specificities;
        this.isVisible = isVisible;
        this.isAvailable = isAvailable;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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


}
