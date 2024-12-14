package com.example.fusmobilni.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private FragmentHomeBinding _binding;
    private List<Event> events;
    private List<Service> services;
    private List<Product> products;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LinearLayout _eventsContainer;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        _binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = _binding.getRoot();

        services = fillServices();
        products = fillProducts();

        this._binding.eventsRecycleView.setAdapter(new EventsAdapter(new ArrayList<>()));
        this._binding.servicesRecycleView.setAdapter(new ServicesAdapter(new ArrayList<>()));
        this._binding.productsRecycleView.setAdapter(new ProductsAdapter(new ArrayList<>()));

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
    private void fetchProducts(){
        Call<ProductsHomeResponse> call = ClientUtils.productsService.findTopFiveProducts(null);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ProductsHomeResponse> call, Response<ProductsHomeResponse> response) {
                if(response.isSuccessful()){
                    List<ProductHomeResponse> list = response.body().products;
                    _binding.productsRecycleView.setAdapter(new ProductsAdapter(list));
                }
            }
            @Override
            public void onFailure(Call<ProductsHomeResponse> call, Throwable t) {
                Log.d("tag", t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void fetchServices(){
        Call<ServicesHomeResponse> call = ClientUtils.serviceOfferingService.findTopFiveServices(null);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ServicesHomeResponse> call, Response<ServicesHomeResponse> response) {
                if (response.isSuccessful()) {

                    List<ServiceHomeResponse> list = response.body().content;
                    _binding.servicesRecycleView.setAdapter(new ServicesAdapter(list));
                }
            }

            @Override
            public void onFailure(Call<ServicesHomeResponse> call, Throwable t) {
                Log.d("tag", t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void fetchEvents(){

        Call<EventsHomeResponse> call = ClientUtils.eventsService.findTopFiveEvents(null);
        call.enqueue(new Callback<>() {
            @Override

            public void onResponse(Call<EventsHomeResponse> call, Response<EventsHomeResponse> response) {
                if (response.isSuccessful()) {
                    List<EventHomeResponse> list = response.body().events;
                    _binding.eventsRecycleView.setAdapter(new EventsAdapter(list));
                }
            }

            @Override
            public void onFailure(Call<EventsHomeResponse> call, Throwable t) {
                Log.d("tag", t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private ArrayList<Service> fillServices() {
        ArrayList<Service> s = new ArrayList<>();
        s.add(new Service("Live band for weddings and parties", "Wedding Band", "New York", "Music"));
        s.add(new Service("Professional photography for events", "Photography Service", "Los Angeles", "Art"));
        s.add(new Service("Catering services for all occasions", "Catering Service", "Chicago", "Food"));
        s.add(new Service("Event decoration and setup", "Decoration Service", "San Francisco", "Art"));
        s.add(new Service("Spacious venue for corporate events", "Venue Rental", "Miami", "Travel"));
        return s;
    }

    private ArrayList<Event> fillEvents() {
        ArrayList<Event> e = new ArrayList<>();
        e.add(new Event("Food and Wine Tasting", "12-07-2024", "Napa Valley Vineyard", "Food", "Food"));
        e.add(new Event("Tech Innovators Conference", "15-08-2024", "Silicon Valley Expo Center", "Tech", "Tech"));
        e.add(new Event("Autumn Art and Sculpture Exhibition", "18-09-2024", "Paris Art Museum", "Art", "Art"));
        e.add(new Event("Global Startup Pitch Event", "22-11-2024", "Berlin Startup Hub", "Tech", "Tech"));
        e.add(new Event("International Film and Documentary Festival", "05-11-2024", "Toronto Film Centre", "Art", "Art"));
        return e;

    }

    private ArrayList<Product> fillProducts() {
        ArrayList<Product> p = new ArrayList<>();
        p.add(new Product("Gourmet Pizza", "A delicious blend of flavors", 15.99, "New York", "Food"));
        p.add(new Product("Lemonade", "Refreshing and invigorating beverage", 3.49, "California", "Health"));
        p.add(new Product("Mixed Nuts", "Sweet and savory snacks", 5.99, "Texas", "Sports"));
        p.add(new Product("Croissants", "Freshly baked pastries", 2.99, "France", "Art"));
        p.add(new Product("Dark Chocolate Truffles", "Artisanal chocolates", 12.49, "Belgium", "Fashion"));

        return p;

    }

}