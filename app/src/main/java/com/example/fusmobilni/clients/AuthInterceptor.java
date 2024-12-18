package com.example.fusmobilni.clients;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.model.users.User;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final CustomSharedPrefs sharedPrefs;

    public AuthInterceptor(Context context) {
        this.sharedPrefs = new CustomSharedPrefs(context);
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        User user = sharedPrefs.getUser();

        String jwtToken = (user != null) ? user.getJwt() : null;

        if (jwtToken != null && !jwtToken.isEmpty()) {
            Request modified = originalRequest.newBuilder().header("Authorization", "Bearer" + jwtToken).build();
            return chain.proceed(modified);
        }
        return chain.proceed(originalRequest);
    }
}
