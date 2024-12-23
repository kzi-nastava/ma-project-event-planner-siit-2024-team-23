package com.example.fusmobilni.clients.users;

import com.example.fusmobilni.responses.register.regular.RegisterResponse;
import com.example.fusmobilni.responses.users.UserInfoPreviewResponse;
import com.example.fusmobilni.responses.users.UserInfoResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
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


}
