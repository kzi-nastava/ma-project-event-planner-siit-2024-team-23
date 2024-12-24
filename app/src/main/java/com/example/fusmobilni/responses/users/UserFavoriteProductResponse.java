package com.example.fusmobilni.responses.users;

import com.example.fusmobilni.responses.items.CategoryResponse;
import com.example.fusmobilni.responses.location.LocationResponse;

public class UserFavoriteProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String image;
    private CategoryResponse category;
    private LocationResponse location;

    public UserFavoriteProductResponse(Long id, String name, String description, Double price, String image, CategoryResponse category, LocationResponse location) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public LocationResponse getLocation() {
        return location;
    }

    public void setLocation(LocationResponse location) {
        this.location = location;
    }
}
