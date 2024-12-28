package com.example.fusmobilni.clients.services.items.pricelist;

import com.example.fusmobilni.requests.items.pricelist.PriceListUpdateRequest;
import com.example.fusmobilni.responses.items.pricelist.PriceListGetResponse;
import com.example.fusmobilni.responses.items.pricelist.PriceListsGetResponse;
import com.example.fusmobilni.responses.users.GetCompanyOverviewResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

public interface PriceListService {

    @GET("service-providers/{spId}/price-lists")
    Call<PriceListsGetResponse> findAllItemsBySpId(@Path("spId") Long spId);

    @PUT("service-providers/{spId}/price-lists/{id}")
    Call<PriceListGetResponse> update(@Path("spId") Long spId,
                                             @Path("id") Long id,
                                             @Body PriceListUpdateRequest request);

    @GET("service-providers/{spId}/price-lists/pdf")
    @Streaming
    Call<ResponseBody> downloadPriceListPdf(@Path("spId") Long spId);

    @GET("service-providers/{spId}/company-overview")
    Call<GetCompanyOverviewResponse> getCompanyOverview(@Path("spId") Long spId);
}
