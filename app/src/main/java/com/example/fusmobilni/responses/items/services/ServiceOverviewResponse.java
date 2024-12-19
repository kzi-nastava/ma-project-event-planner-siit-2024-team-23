package com.example.fusmobilni.responses.items.services;

import com.example.fusmobilni.responses.auth.ServiceProviderResponse;
import com.example.fusmobilni.responses.items.CategoryResponse;
import com.example.fusmobilni.responses.items.ItemGradeResponse;

import java.util.List;

public class ServiceOverviewResponse {
    public String name;
    public String description;
    public String specificities;
    public Double price;
    public String image;
    public CategoryResponse category;
    public ServiceProviderResponse provider;
    public List<ItemGradeResponse> grades;

    public ServiceOverviewResponse(CategoryResponse category, String description, List<ItemGradeResponse> grades, String name, Double price, ServiceProviderResponse provider, String specificities,String image) {
        this.category = category;
        this.description = description;
        this.grades = grades;
        this.name = name;
        this.price = price;
        this.provider = provider;
        this.specificities = specificities;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public List<ItemGradeResponse> getGrades() {
        return grades;
    }

    public void setGrades(List<ItemGradeResponse> grades) {
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

    public ServiceProviderResponse getProvider() {
        return provider;
    }

    public void setProvider(ServiceProviderResponse provider) {
        this.provider = provider;
    }

    public String getSpecificities() {
        return specificities;
    }

    public void setSpecificities(String specificities) {
        this.specificities = specificities;
    }
}
