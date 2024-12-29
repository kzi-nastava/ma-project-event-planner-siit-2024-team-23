package com.example.fusmobilni.clients.services.products;

import com.example.fusmobilni.requests.products.GetProductResponse;
import com.example.fusmobilni.requests.services.GetServiceResponse;
import com.example.fusmobilni.requests.services.ServiceFilterRequest;
import com.example.fusmobilni.requests.services.cardView.GetServicesCardResponse;
import com.example.fusmobilni.responses.events.filter.EventsPaginationResponse;
import com.example.fusmobilni.responses.items.CategoriesResponse;
import com.example.fusmobilni.responses.items.products.filter.ProductsMinMaxPriceResponse;
import com.example.fusmobilni.responses.items.products.filter.ProductsPaginationResponse;
import com.example.fusmobilni.responses.items.products.home.ProductsHomeResponse;
import com.example.fusmobilni.responses.items.products.filter.ProductLocationsResponse;
import com.example.fusmobilni.responses.items.services.filter.ServicesMinMaxPriceResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
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

    @GET("products/minMaxPrice")
    Call<ProductsMinMaxPriceResponse> findMinMaxPrice();

    @Multipart
    @POST("products/mobile")
    Call<GetProductResponse> create(
            @Part("data") RequestBody request,
            @Part List<MultipartBody.Part> images
    );

    @Multipart
    @PUT("products/{id}/mobile")
    Call<GetProductResponse> update(@Part("data") RequestBody body,
                                    @Part List<MultipartBody.Part> images,
                                    @Path("id") Long id);

    @Multipart
    @POST("products/proposals/mobile")
    Call<Void> createProposal(
            @Part("data") RequestBody request,
            @Part List<MultipartBody.Part> images
    );

    @DELETE("products/{id}")
    Call<Void> delete(@Path("id") Long id);

    @POST("service-providers/{id}/products")
    Call<ProductsHomeResponse> findAllByServiceProvider(@Path("id") Long id, @Body ServiceFilterRequest request);

    @GET("products/{id}")
    Call<GetProductResponse> findById(@Path("id") long id);





}
