package com.example.fusmobilni.responses.items;

import java.util.List;

public class CategoriesResponse {
    public List<CategoryResponse> categories;

    public CategoriesResponse(List<CategoryResponse> categories) {
        this.categories = categories;
    }

    public List<CategoryResponse> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryResponse> categories) {
        this.categories = categories;
    }
}
