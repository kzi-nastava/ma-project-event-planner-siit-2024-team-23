package com.example.fusmobilni.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.events.event.EventsAdapter;
import com.example.fusmobilni.adapters.items.product.ProductsAdapter;
import com.example.fusmobilni.adapters.items.service.ServicesAdapter;
import com.example.fusmobilni.adapters.loading.LoadingCardVerticalAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentHomeBinding;
import com.example.fusmobilni.model.event.Event;
import com.example.fusmobilni.model.items.product.Product;
import com.example.fusmobilni.model.items.service.Service;
import com.example.fusmobilni.responses.events.home.EventHomeResponse;
import com.example.fusmobilni.responses.events.home.EventsHomeResponse;
import com.example.fusmobilni.responses.items.products.home.ProductHomeResponse;
import com.example.fusmobilni.responses.items.products.home.ProductsHomeResponse;
import com.example.fusmobilni.responses.items.services.home.ServiceHomeResponse;
import com.example.fusmobilni.responses.items.services.home.ServicesHomeResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding _binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        _binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = _binding.getRoot();


        initializeLoadingCards();

        this._binding.eventsRecycleView.setAdapter(new EventsAdapter(new ArrayList<>()));
        this._binding.servicesRecycleView.setAdapter(new ServicesAdapter(new ArrayList<>()));
        this._binding.productsRecycleView.setAdapter(new ProductsAdapter(new ArrayList<>()));

        _binding.eventsRecycleView.setVisibility(View.GONE);
        _binding.servicesRecycleView.setVisibility(View.GONE);
        _binding.productsRecycleView.setVisibility(View.GONE);

        this._binding.homeEventsSeeAllButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_searchFragment);
        });
        this._binding.homeServicesSeeAllButton.setOnClickListener(v -> {

            Navigation.findNavController(view).navigate(R.id.action_home_fragment_to_service_search);
        });
        this._binding.productsSeeAllButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_home_fragment_to_product_search);
        });

        fetchEvents();
        fetchServices();
        fetchProducts();
        return view;
    }

    private void turnOffShimmer(RecyclerView loadingCardsView, RecyclerView actualCards) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            loadingCardsView.setAdapter(new LoadingCardVerticalAdapter(0));
            loadingCardsView.setVisibility(View.GONE);
            actualCards.setVisibility(View.VISIBLE);

        }, 1500); //
    }

    private void initializeLoadingCards() {
        _binding.eventsLoadingCards.setAdapter(new LoadingCardVerticalAdapter(5));
        _binding.serviceLoadingCards.setAdapter(new LoadingCardVerticalAdapter(5));
        _binding.productLoadingCards.setAdapter(new LoadingCardVerticalAdapter(5));
    }

    private void fetchProducts() {
        Call<ProductsHomeResponse> call = ClientUtils.productsService.findTopFiveProducts(null);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ProductsHomeResponse> call, Response<ProductsHomeResponse> response) {
                if (response.isSuccessful()) {
                    List<ProductHomeResponse> list = response.body().products;
                    _binding.productsRecycleView.setAdapter(new ProductsAdapter(list));
                    turnOffShimmer(_binding.productLoadingCards, _binding.productsRecycleView);
                }
            }

            @Override
            public void onFailure(Call<ProductsHomeResponse> call, Throwable t) {
                Log.d("tag", t.getMessage());
                Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchServices() {
        Call<ServicesHomeResponse> call = ClientUtils.serviceOfferingService.findTopFiveServices(null);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ServicesHomeResponse> call, Response<ServicesHomeResponse> response) {
                if (response.isSuccessful()) {

                    List<ServiceHomeResponse> list = response.body().content;
                    _binding.servicesRecycleView.setAdapter(new ServicesAdapter(list));
                    turnOffShimmer(_binding.serviceLoadingCards, _binding.servicesRecycleView);
                }
            }

            @Override
            public void onFailure(Call<ServicesHomeResponse> call, Throwable t) {
                Log.d("tag", t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchEvents() {

        Call<EventsHomeResponse> call = ClientUtils.eventsService.findTopFiveEvents(null);
        call.enqueue(new Callback<>() {
            @Override

            public void onResponse(Call<EventsHomeResponse> call, Response<EventsHomeResponse> response) {
                if (response.isSuccessful()) {
                    List<EventHomeResponse> list = response.body().events;
                    _binding.eventsRecycleView.setAdapter(new EventsAdapter(list));
                    turnOffShimmer(_binding.eventsLoadingCards, _binding.eventsRecycleView);
                }
            }

            @Override
            public void onFailure(Call<EventsHomeResponse> call, Throwable t) {
                Log.d("tag", t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}