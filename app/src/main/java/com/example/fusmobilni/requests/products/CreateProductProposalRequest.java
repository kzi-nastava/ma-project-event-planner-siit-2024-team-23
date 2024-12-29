package com.example.fusmobilni.requests.products;

import com.example.fusmobilni.model.enums.ReservationConfirmation;

public class CreateProductProposalRequest {

    private String categoryName;
    private String categoryDescription;
    private String name;
    private String description;
    private String eventTypeIds;
    private double price;
    private double discount;
    private Long serviceProviderId;
    private String specificities;
    private boolean isAvailable;
    private boolean isVisible;

    public CreateProductProposalRequest(String categoryName, String categoryDescription, String name, String description, String eventTypeIds,
                                        double price, double discount, Long serviceProviderId, String specificities,
                                        boolean isAvailable, boolean isVisible) {
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.name = name;
        this.description = description;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}
