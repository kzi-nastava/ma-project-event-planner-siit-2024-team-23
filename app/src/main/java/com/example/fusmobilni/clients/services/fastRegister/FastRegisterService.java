package com.example.fusmobilni.clients.services.fastRegister;

import com.example.fusmobilni.responses.FastRegisterInvitationResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FastRegisterService {
    @GET("fast-register/{hash}")
    Call<FastRegisterInvitationResponse> getByHash(@Path("hash") String hash);

}
