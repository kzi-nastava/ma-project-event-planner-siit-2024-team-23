package com.example.fusmobilni.fragments.items.company;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.adapters.items.reviews.ItemReviewsAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentCompanyOverviewBinding;
import com.example.fusmobilni.helper.UriConverter;
import com.example.fusmobilni.responses.users.GetCompanyOverviewResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CompanyOverviewFragment extends Fragment {

    private FragmentCompanyOverviewBinding binding;
    private ItemReviewsAdapter adapter;

    private Long spId;
    private GetCompanyOverviewResponse companyOverview;
    private RecyclerView recyclerView;


    public CompanyOverviewFragment() {}

    public static CompanyOverviewFragment newInstance() {
        return new CompanyOverviewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompanyOverviewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        spId = getUserId();
        if (spId == null)
            return view;
        Call<GetCompanyOverviewResponse> callback = ClientUtils.priceListService.getCompanyOverview(spId);
        callback.enqueue(new Callback<GetCompanyOverviewResponse>() {
            @Override
            public void onResponse(Call<GetCompanyOverviewResponse> call, Response<GetCompanyOverviewResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    companyOverview = response.body();
                    setUpRecycler();
                    initializePage();
                }
            }

            @Override
            public void onFailure(Call<GetCompanyOverviewResponse> call, Throwable t) {

            }
        });
        return view;
    }

    private Long getUserId() {
        try {
            if (getArguments() == null)
                return null;
            return getArguments().getLong("spId");
        } catch (NullPointerException e) {
            return null;
        }
    }

    private void setUpRecycler() {
        recyclerView = binding.gradesRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ItemReviewsAdapter(companyOverview.reviews.getItemReviews());
        recyclerView.setAdapter(adapter);
    }

    private void initializePage() {
        try {
            binding.avatar.setImageURI(UriConverter.
                    convertToUriFromBase64(getContext(), companyOverview.avatar));
        } catch (IOException e) {
            Log.e("Tag", "Error setting up avatar");
        }
        binding.companyDescription.setText(companyOverview.companyDescription);
        binding.companyName.setText(companyOverview.companyName);
        binding.companyOwner.setText(String.format("%s %s", companyOverview.name, companyOverview.surname));
    }
}