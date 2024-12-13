package com.example.fusmobilni.clients.services.categories;

import com.example.fusmobilni.requests.categories.CreateCategoryRequest;
import com.example.fusmobilni.requests.categories.GetCategoriesResponse;
import com.example.fusmobilni.requests.categories.GetCategoryResponse;
import com.example.fusmobilni.requests.categories.UpdateCategoryRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoryService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("categories")
    Call<GetCategoryResponse> create(@Body CreateCategoryRequest request);

    @PUT("categories/{id}")
    Call<GetCategoryResponse> update(@Body UpdateCategoryRequest request, @Path("id") Long id);

    @DELETE("categories/{id}")
    Call<Void> delete(@Path("id") Long id);

    @GET("categories")
    Call<GetCategoriesResponse> findAll();

}
