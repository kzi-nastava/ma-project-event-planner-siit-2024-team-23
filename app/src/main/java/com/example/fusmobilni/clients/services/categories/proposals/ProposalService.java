package com.example.fusmobilni.clients.services.categories.proposals;

import com.example.fusmobilni.requests.categories.GetCategoriesResponse;
import com.example.fusmobilni.requests.proposals.GetProposalsResponse;
import com.example.fusmobilni.requests.proposals.ModifyItemProposalRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProposalService {

    @POST("categories/proposals/{id}")
    Call<Void> modify(@Path("id") Long id,
                      @Body ModifyItemProposalRequest request);

    @GET("categories/proposals")
    Call<GetProposalsResponse> findAll();
}
