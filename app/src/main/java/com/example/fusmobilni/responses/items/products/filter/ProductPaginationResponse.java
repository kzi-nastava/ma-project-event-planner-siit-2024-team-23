package com.example.fusmobilni.responses.items.products.filter;

import com.example.fusmobilni.responses.items.CategoryResponse;

public class ProductPaginationResponse {
    public Long id;
    public String name;
    public String description;
    public Double price;

    public CategoryResponse category;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ProductPaginationResponse(CategoryResponse category, String description, Long id, String name, Double price) {
        this.category = category;
        this.description = description;
        this.id = id;
        this.name = name;
        this.price = price;
    }
}



