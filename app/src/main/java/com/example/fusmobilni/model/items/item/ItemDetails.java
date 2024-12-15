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
    private boolean isAvailable;
    private boolean isVisible;



}
