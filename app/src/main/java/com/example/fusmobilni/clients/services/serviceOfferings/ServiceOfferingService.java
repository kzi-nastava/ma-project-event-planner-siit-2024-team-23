package com.example.fusmobilni.clients.services.serviceOfferings;


import com.example.fusmobilni.requests.services.GetServicesResponse;
import com.example.fusmobilni.requests.services.GetServiceResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface ServiceOfferingService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:multipart/form-data"
    })
    @POST("services/")
    Call<GetServiceResponse> create(@PartMap Map<String, RequestBody> params,
                                    @Part List<MultipartBody.Part> images );

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:multipart/form-data"
    })
    @PUT("services/{id}")
    Call<GetServiceResponse> update(@PartMap Map<String, RequestBody> params,
                                    @Part List<MultipartBody.Part> images,
                                    @Path("id") Long id);

    @DELETE("services/{id}")
    Call<Void> delete(@Path("id") Long id);

    @GET("service-providers/{id}")
    Call<GetServicesResponse> findAllByServiceProvider(@Path("id") Long id);

}
