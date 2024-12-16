package com.example.fusmobilni.interfaces;

import com.example.fusmobilni.model.items.category.OfferingsCategory;
import com.example.fusmobilni.responses.events.components.EventComponentResponse;

public interface OnCategoryClickListener {
    void onItemClick(EventComponentResponse category, double price);
}
