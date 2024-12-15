package com.example.fusmobilni.fragments.events.event.createEventSteps;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.adapters.items.ItemsHorizontalAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentChooseProductBinding;
import com.example.fusmobilni.requests.events.event.GetItemsByCategoryAndPrice;
import com.example.fusmobilni.responses.events.GetItemResponse;
import com.example.fusmobilni.responses.events.GetItemsResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseProductFragment extends Fragment {
    private FragmentChooseProductBinding _binding;
    private ItemsHorizontalAdapter itemsHorizontalAdapter;
    private double estimatedBudget;
    private Long eventId;
    private Long categoryId;
    private ArrayList<GetItemResponse> items = new ArrayList<>();
    private RecyclerView listView;
    public ChooseProductFragment() {
        // Required empty public constructor
    }
    public static ChooseProductFragment newInstance() {
        return new ChooseProductFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        eventId = bundle.getLong("eventId");
        estimatedBudget = bundle.getDouble("price");
        categoryId = bundle.getLong("category");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentChooseProductBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();
        listView = _binding.recyclerView;

        itemsHorizontalAdapter = new ItemsHorizontalAdapter();
        fillItems();
        return view;
    }

    private void fillItems() {
        Call<GetItemsResponse> request = ClientUtils.itemsService.
                findAllByCategoryAndPrice(new GetItemsByCategoryAndPrice(categoryId, estimatedBudget));
        request.enqueue(new Callback<GetItemsResponse>() {
            @Override
            public void onResponse(Call<GetItemsResponse> call, Response<GetItemsResponse> response) {
                if(response.isSuccessful()){
                    items.clear();
                    items = response.body().itemsResponsesDTO;
                    itemsHorizontalAdapter.setData(items);
                    listView.setAdapter(itemsHorizontalAdapter);
                }
            }

            @Override
            public void onFailure(Call<GetItemsResponse> call, Throwable t) {

            }
        });
    }
}