package com.example.fusmobilni.clients.services.serviceOfferings;


import com.example.fusmobilni.requests.services.GetServicesResponse;
import com.example.fusmobilni.requests.services.GetServiceResponse;
import com.example.fusmobilni.requests.services.ServiceFilterRequest;
import com.example.fusmobilni.requests.services.cardView.GetServicesCardResponse;
import com.example.fusmobilni.responses.events.filter.EventsPaginationResponse;
import com.example.fusmobilni.responses.items.CategoriesResponse;
import com.example.fusmobilni.responses.items.services.ServiceOverviewResponse;
import com.example.fusmobilni.responses.items.services.ServiceReservationResponse;
import com.example.fusmobilni.responses.items.services.filter.ServiceLocationsResponse;
import com.example.fusmobilni.responses.items.services.filter.ServicesMinMaxPriceResponse;
import com.example.fusmobilni.responses.items.services.filter.ServicesPaginationResponse;
import com.example.fusmobilni.responses.items.services.home.ServicesHomeResponse;

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

public interface ServiceOfferingService {


    @Multipart
    @POST("services/mobile")
    Call<GetServiceResponse> create(
            @Part("data") RequestBody request,
            @Part List<MultipartBody.Part> images
    );

    @Multipart
    @PUT("services/{id}/mobile")
    Call<GetServiceResponse> update(@Part("data") RequestBody body,
                                    @Part List<MultipartBody.Part> images,
                                    @Path("id") Long id);

    @Multipart
    @POST("services/proposals/mobile")
    Call<Void> createProposal(
            @Part("data") RequestBody request,
            @Part List<MultipartBody.Part> images
    );

    @DELETE("services/{id}")
    Call<Void> delete(@Path("id") Long id);

    @POST("service-providers/{id}/services")
    Call<GetServicesCardResponse> findAllByServiceProvider(@Path("id") Long id, @Body ServiceFilterRequest request);

    @GET("services/{id}")
    Call<GetServiceResponse> findById(@Path("id") long id);

    @GET("services/top-five-services")
    Call<ServicesHomeResponse> findTopFiveServices(@Query("userId") Long userId);

    @GET("services/browse")
    Call<ServicesPaginationResponse> findFilteredAndPaginated(
            @Query("page") int page,
            @Query("pageSize") int pageSize
    );

    @GET("categories/services")
    Call<CategoriesResponse> findCategoriesForServices();

    @GET("services/locations")
    Call<ServiceLocationsResponse> findServiceLocations();

    @GET("services/browse")
    Call<ServicesPaginationResponse> findFilteredAndPaginated(
            @Query("page") int page,
            @Query("pageSize") int pageSize,
            @QueryMap Map<String, String> options
    );

    @GET("services/minMaxPrice")
    Call<ServicesMinMaxPriceResponse> findMinMaxPrice();

    @GET("services/overview/{id}")
    Call<ServiceOverviewResponse> findServiceForOverview(@Path("id") Long id);

    @GET("services/reservation/{id}")
    Call<ServiceReservationResponse> findServiceForReservation(@Path("id") Long id);


}
