package com.example.fusmobilni.responses.items.products;

import com.example.fusmobilni.responses.auth.ServiceProviderDetailsResponse;
import com.example.fusmobilni.responses.items.CategoryResponse;
import com.example.fusmobilni.responses.items.ItemReviewResponse;

import java.util.ArrayList;
import java.util.List;

public class ProductOverviewResponse {
    public Long id;
    public String name;
    public String description;
    public String specificities;
    public Double price;
    public Double discount;
    public ArrayList<String> images;
    public CategoryResponse category;
    public ServiceProviderDetailsResponse serviceProvider;
    public List<ItemReviewResponse> grades;

    public ProductOverviewResponse(Long id, CategoryResponse category, String description, List<ItemReviewResponse> grades, String name, Double price, Double discount, ServiceProviderDetailsResponse provider, String specificities, ArrayList<String> image) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.grades = grades;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.serviceProvider = provider;
        this.specificities = specificities;
        this.images = images;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<String> getImage() {
        return images;
    }

    public void setImage(ArrayList<String> image) {
        this.images = image;
    }

    public CategoryResponse getCategory() {
        return category;
    }

    public void setCategory(CategoryResponse category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ItemReviewResponse> getGrades() {
        return grades;
    }

    public void setGrades(List<ItemReviewResponse> grades) {
        this.grades = grades;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ServiceProviderDetailsResponse getProvider() {
        return serviceProvider;
    }

    public void setProvider(ServiceProviderDetailsResponse provider) {
        this.serviceProvider = provider;
    }

    public String getSpecificities() {
        return specificities;
    }

    public void setSpecificities(String specificities) {
        this.specificities = specificities;
    }
}
