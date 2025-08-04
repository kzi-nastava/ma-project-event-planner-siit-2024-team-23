package com.example.fusmobilni.responses.users;

import com.example.fusmobilni.responses.items.ItemReviewsResponse;

public class GetCompanyOverviewResponse {
    public String avatar;
    public String name;
    public String surname;
    public String companyName;
    public String companyDescription;
    public ItemReviewsResponse reviews;

    public GetCompanyOverviewResponse(String avatar, String name, String surname, String companyName, String companyDescription, ItemReviewsResponse reviews) {
        this.avatar = avatar;
        this.name = name;
        this.surname = surname;
        this.companyName = companyName;
        this.companyDescription = companyDescription;
        this.reviews = reviews;
    }
}
