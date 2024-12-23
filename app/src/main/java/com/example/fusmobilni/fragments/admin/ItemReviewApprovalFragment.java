package com.example.fusmobilni.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.adapters.items.reviews.ItemReviewApprovalAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentItemReviewApprovalBinding;
import com.example.fusmobilni.responses.items.ItemReviewsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemReviewApprovalFragment extends Fragment {

    private FragmentItemReviewApprovalBinding _binding;
    private ItemReviewApprovalAdapter _adapter;

    public ItemReviewApprovalFragment() {
        // Required empty public constructor
    }

    public static ItemReviewApprovalFragment newInstance(String param1, String param2) {
        ItemReviewApprovalFragment fragment = new ItemReviewApprovalFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _binding = FragmentItemReviewApprovalBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        fetchPendingReviews();
        return root;
    }

    public void fetchPendingReviews() {
        Call<ItemReviewsResponse> call = ClientUtils.itemsService.findPendingReviews();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ItemReviewsResponse> call, Response<ItemReviewsResponse> response) {
                if (response.isSuccessful()) {
                    _adapter = new ItemReviewApprovalAdapter(response.body().getItemReviews());
                    _binding.itemReviewRecycler.setAdapter(_adapter);
                }
            }

            @Override
            public void onFailure(Call<ItemReviewsResponse> call, Throwable t) {

            }
        });
    }
}