package com.example.fusmobilni.clients.users;

import com.example.fusmobilni.requests.users.favorites.FavoriteEventRequest;
import com.example.fusmobilni.requests.users.favorites.FavoriteProductRequest;
import com.example.fusmobilni.requests.users.favorites.FavoriteServiceRequest;
import com.example.fusmobilni.responses.events.home.EventHomeResponse;
import com.example.fusmobilni.responses.events.home.EventsHomeResponse;
import com.example.fusmobilni.responses.register.regular.RegisterResponse;
import com.example.fusmobilni.responses.users.UserFavoriteEventsResponse;
import com.example.fusmobilni.responses.users.UserFavoriteProductsResponse;
import com.example.fusmobilni.responses.users.UserFavoriteServiceResponse;
import com.example.fusmobilni.responses.users.UserFavoriteServicesResponse;
import com.example.fusmobilni.responses.users.UserInfoPreviewResponse;
import com.example.fusmobilni.responses.users.UserInfoResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserService {
    @GET("users/user-info")
    Call<UserInfoResponse> findUserInfo();
    @GET("users/user-info/preview")
    Call<UserInfoPreviewResponse> findUserPreviewInfo();
    @Multipart
    @PUT("users/{id}/mobile")
    Call<UserInfoResponse> updateAuthenticatedUser(@Part("data") RequestBody request, @Part MultipartBody.Part image, @Path("id") Long id);
    @Multipart
    @PUT("service-providers/{id}/mobile")
    Call<UserInfoResponse> updateServiceProvider(@Part("data") RequestBody request, @Part MultipartBody.Part image, @Path("id") Long id);
    @GET("users/{id}/favorite-events")
    Call<UserFavoriteEventsResponse> findFavoriteEvents(@Path("id") Long id);
    @POST("users/{id}/favorite-events")
    Call<Void> addEventToFavorites(@Path("id") Long id, @Body FavoriteEventRequest request);
    @GET("users/{id}/favorite-services")
    Call<UserFavoriteServicesResponse> findFavoriteServices(@Path("id") Long id);
    @POST("users/{id}/favorite-services")
    Call<Void> addToServiceFavorites(@Path("id") Long id, @Body FavoriteServiceRequest request);
    @GET("users/{id}/favorite-products")
    Call<UserFavoriteProductsResponse> findFavoriteProducts(@Path("id") Long id);
    @POST("users/{id}/favorite-products")
    Call<Void> addToProductFavorites(@Path("id") Long id, @Body FavoriteProductRequest request);
    @GET("events/users/{id}")
    Call<EventsHomeResponse> findEventsForUser(@Path("id") Long id);


}
