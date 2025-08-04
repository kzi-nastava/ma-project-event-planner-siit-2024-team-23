package com.example.fusmobilni.model.event.eventTypes;

import com.example.fusmobilni.model.items.category.Category;
import com.example.fusmobilni.model.items.category.OfferingsCategory;

import java.util.ArrayList;

public class SuggestedCategories {
    public ArrayList<OfferingsCategory> categories;

    public SuggestedCategories(ArrayList<OfferingsCategory> categories) {
        this.categories = categories;
    }
}
