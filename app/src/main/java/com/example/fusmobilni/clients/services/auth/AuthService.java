package com.example.fusmobilni.clients.services.auth;

import com.example.fusmobilni.requests.auth.LoginRequest;
import com.example.fusmobilni.requests.register.fast.FastRegisterRequest;
import com.example.fusmobilni.requests.register.regular.RegisterRequest;
import com.example.fusmobilni.requests.services.GetServiceResponse;
import com.example.fusmobilni.responses.auth.EmailVerificationResponse;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.auth.UserAvatarResponse;
import com.example.fusmobilni.responses.register.fast.FastRegisterResponse;
import com.example.fusmobilni.responses.register.regular.RegisterResponse;

import java.util.List;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface AuthService {

    @POST("auth/fast-register")
    Call<FastRegisterResponse> fastRegister(@Body FastRegisterRequest request);

    @GET("auth/verify-email")
    Call<EmailVerificationResponse> verifyEmail(@Query("token") String token);

    @Multipart
    @POST("auth/register/mobile")
    Call<RegisterResponse> register(@Part("data") RequestBody request, @Part MultipartBody.Part image);

    @POST("auth/login")
    Call<Object> login(@Body LoginRequest request);

    @GET("users/user-avatar")
    @Streaming
    @Headers("Accept: image/jpeg")
    Call<ResponseBody> findProfileImage();

    @GET("users/{id}/user-avatar")
    @Streaming
    @Headers("Accept: image/jpeg")
    Call<ResponseBody> findProfileImageByUserId(@Path("id") Long id);
}
