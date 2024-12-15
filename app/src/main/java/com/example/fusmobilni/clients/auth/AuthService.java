package com.example.fusmobilni.clients.auth;

import com.example.fusmobilni.requests.register.fast.FastRegisterRequest;
import com.example.fusmobilni.responses.auth.EmailVerificationResponse;
import com.example.fusmobilni.responses.register.fast.FastRegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthService {

    @POST("auth/fast-register")
    Call<FastRegisterResponse> fastRegister(@Body FastRegisterRequest request);

    @GET("auth/verify-email")
    Call<EmailVerificationResponse> verifyEmail(@Query("token") String token);
}
