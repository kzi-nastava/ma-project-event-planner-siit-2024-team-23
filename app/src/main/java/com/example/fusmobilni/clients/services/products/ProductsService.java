package com.example.fusmobilni.clients.services.products;

import com.example.fusmobilni.responses.items.products.ProductsHomeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductsService {
    @GET("products/top-five-products")
    Call<ProductsHomeResponse> findTopFiveProducts(@Query("city") String city);
}
