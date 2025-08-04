package com.example.fusmobilni.clients.services.geoCoding;

import com.example.fusmobilni.responses.geoCoding.GeoCodingResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeoCodingService {

    @GET("search")
    Call<List<GeoCodingResponse>> getGeoCode(@Query("key") String apiKey,
                          @Query("q") String address,
                          @Query("format") String format);

}
