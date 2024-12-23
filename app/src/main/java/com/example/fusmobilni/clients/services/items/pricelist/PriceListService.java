package com.example.fusmobilni.clients.services.items.pricelist;

import com.example.fusmobilni.requests.items.pricelist.PriceListUpdateRequest;
import com.example.fusmobilni.responses.items.pricelist.PriceListGetResponse;
import com.example.fusmobilni.responses.items.pricelist.PriceListsGetResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PriceListService {

    @GET("service-providers/{spId}/price-lists")
    public Call<PriceListsGetResponse> findAllItemsBySpId(@Path("spId") Long spId);

    @PUT("service-providers/{spId}/price-lists/{id}")
    public Call<PriceListGetResponse> update(@Path("spId") Long spId,
                                             @Path("id") Long id,
                                             @Body PriceListUpdateRequest request);

}
