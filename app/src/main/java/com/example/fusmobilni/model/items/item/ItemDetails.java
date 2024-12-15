package com.example.fusmobilni.model.items.item;

import com.example.fusmobilni.model.items.category.Category;
import com.example.fusmobilni.model.items.category.OfferingsCategory;
import com.example.fusmobilni.model.users.ServiceProviderDetails;

import java.util.ArrayList;

public class ItemDetails {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private OfferingsCategory category;
    private ServiceProviderDetails serviceProvider;
    private ArrayList<String> images;
    private String specificities;
    private Double discount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getSpecificities() {
        return specificities;
    }

    public void setSpecificities(String specificities) {
        this.specificities = specificities;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ServiceProviderDetails getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProviderDetails serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public OfferingsCategory getCategory() {
        return category;
    }

    public void setCategory(OfferingsCategory category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private boolean isAvailable;
    private boolean isVisible;


    public ItemDetails(boolean isVisible, boolean isAvailable, Double discount, String specificities, ArrayList<String> images, ServiceProviderDetails serviceProvider, OfferingsCategory category, Double price, String description, String name, Long id) {
        this.isVisible = isVisible;
        this.isAvailable = isAvailable;
        this.discount = discount;
        this.specificities = specificities;
        this.images = images;
        this.serviceProvider = serviceProvider;
        this.category = category;
        this.price = price;
        this.description = description;
        this.name = name;
        this.id = id;
    }
}
