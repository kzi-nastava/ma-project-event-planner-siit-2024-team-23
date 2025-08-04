package com.example.fusmobilni.responses.events;

import com.example.fusmobilni.requests.categories.GetCategoryResponse;

import java.util.List;

public class GetEventTypeWithCategoriesResponse {
    public Long id;
    public String name;
    public String description;
    public List<GetCategoryResponse> suggestedCategories;
}
