package com.example.fusmobilni.fragments.users.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.items.product.ProductsHorizontalAdapter;
import com.example.fusmobilni.adapters.items.service.ServiceHorizontalAdapter;
import com.example.fusmobilni.adapters.users.ViewProfileEventAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentUserItemsFragmentBinding;
import com.example.fusmobilni.model.event.Event;
import com.example.fusmobilni.model.items.product.Product;
import com.example.fusmobilni.model.items.service.Service;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.items.products.home.ProductHomeResponse;
import com.example.fusmobilni.responses.items.services.filter.ServicePaginationResponse;
import com.example.fusmobilni.responses.users.UserFavoriteEventsResponse;
import com.example.fusmobilni.responses.users.UserFavoriteProductsResponse;
import com.example.fusmobilni.responses.users.UserFavoriteServicesResponse;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFavItemsFragment extends Fragment {
    private FragmentUserItemsFragmentBinding _binding;
    private ServiceHorizontalAdapter serviceHorizontalAdapter;
    private ProductsHorizontalAdapter productsHorizontalAdapter;
    private RecyclerView listView;
    private LoginResponse user;

    List<ProductHomeResponse> products;
    List<ServicePaginationResponse> services;

    public UserFavItemsFragment() {
        // Required empty public constructor
    }

    public static UserFavItemsFragment newInstance() {
        return new UserFavItemsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentUserItemsFragmentBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();
        listView = _binding.recyclerView;
        MaterialButtonToggleGroup toggleGroup = _binding.toggleGroup;
        CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();
        user = prefs.getUser();

        serviceHorizontalAdapter = new ServiceHorizontalAdapter();
        productsHorizontalAdapter = new ProductsHorizontalAdapter();
        listView.setAdapter(serviceHorizontalAdapter);
        fillServices();
        fillProducts();

        toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.toggle_services) {
                    // Show services
                    listView.setAdapter(serviceHorizontalAdapter);
                } else if (checkedId == R.id.toggle_products) {
                    // Show products
                    listView.setAdapter(productsHorizontalAdapter);
                }
            }
        });
        return view;
    }

    private  void fillProducts(){
        Call<UserFavoriteProductsResponse> request = ClientUtils.userService.findFavoriteProducts(user.getId());
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<UserFavoriteProductsResponse> call, @NonNull Response<UserFavoriteProductsResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    products = response.body().getProducts().stream().map(product ->
                                    new ProductHomeResponse(product.getCategory(), product.getDescription(), product.getId(), product.getLocation(),
                                            product.getName(), product.getPrice(), product.getImage())).
                            collect(Collectors.toList());
                    productsHorizontalAdapter.setData(products);
                    listView.setAdapter(productsHorizontalAdapter);
                    if(products.isEmpty()){
                        _binding.recyclerView.setVisibility(View.GONE);
                        _binding.emptyMessage.setVisibility(View.VISIBLE);
                    }else{
                        _binding.recyclerView.setVisibility(View.VISIBLE);
                        _binding.emptyMessage.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<UserFavoriteProductsResponse> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fillServices() {
        Call<UserFavoriteServicesResponse> request = ClientUtils.userService.findFavoriteServices(user.getId());
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<UserFavoriteServicesResponse> call, @NonNull Response<UserFavoriteServicesResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    services = response.body().getResponses().stream().map(service ->
                                    new ServicePaginationResponse(service.getCategory(), service.getDescription(), service.getId(), service.getLocation(), service.getName(), service.getPrice(), service.getImage())).
                            collect(Collectors.toList());
                    serviceHorizontalAdapter.setData(services);
                    listView.setAdapter(serviceHorizontalAdapter);
                    if(services.isEmpty()){
                        _binding.recyclerView.setVisibility(View.GONE);
                        _binding.emptyMessage.setVisibility(View.VISIBLE);
                    }else{
                        _binding.recyclerView.setVisibility(View.VISIBLE);
                        _binding.emptyMessage.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<UserFavoriteServicesResponse> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}