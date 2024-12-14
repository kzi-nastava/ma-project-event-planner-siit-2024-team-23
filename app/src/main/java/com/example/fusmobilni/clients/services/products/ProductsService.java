package com.example.fusmobilni.clients.services.products;

import com.example.fusmobilni.responses.events.filter.EventsPaginationResponse;
import com.example.fusmobilni.responses.items.CategoriesResponse;
import com.example.fusmobilni.responses.items.products.filter.ProductsPaginationResponse;
import com.example.fusmobilni.responses.items.products.home.ProductsHomeResponse;
import com.example.fusmobilni.responses.items.products.filter.ProductLocationsResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ProductsService {
    @GET("products/top-five-products")
    Call<ProductsHomeResponse> findTopFiveProducts(@Query("city") String city);

    @GET("products/browse")
    Call<ProductsPaginationResponse> findFilteredAndPaginated(
            @Query("page") int page,
            @Query("pageSize") int pageSize
    );

    @GET("categories/products")
    Call<CategoriesResponse> findCategoriesForProducts();

    @GET("products/locations")
    Call<ProductLocationsResponse> findProductLocations();

    @GET("products/browse")
    Call<ProductsPaginationResponse> findFilteredAndPaginated(
            @Query("page") int page,
            @Query("pageSize") int pageSize,
            @QueryMap Map<String, String> options
    );
}
