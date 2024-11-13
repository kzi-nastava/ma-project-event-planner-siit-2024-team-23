package com.example.fusmobilni.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.EventsAdapter;
import com.example.fusmobilni.adapters.ProductsAdapter;
import com.example.fusmobilni.adapters.ServicesAdapter;
import com.example.fusmobilni.databinding.FragmentHomeBinding;
import com.example.fusmobilni.model.Event;
import com.example.fusmobilni.model.Product;
import com.example.fusmobilni.model.Service;

import java.util.ArrayList;
import java.util.List;

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

        events = fillEvents();
        services = fillServices();
        products = fillProducts();

        this._binding.eventsRecycleView.setAdapter(new EventsAdapter(events));
        this._binding.servicesRecycleView.setAdapter(new ServicesAdapter(services));
        this._binding.productsRecycleView.setAdapter(new ProductsAdapter(products));

        this._binding.homeEventsSeeAllButton.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_searchFragment);
        });
        this._binding.homeServicesSeeAllButton.setOnClickListener(v->{

            Navigation.findNavController(view).navigate(R.id.action_home_fragment_to_service_search);
        });
        this._binding.productsSeeAllButton.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_home_fragment_to_product_search);
        });

        return view;
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
        e.add(new Event("Food and Wine Tasting", "12-07-2024", "Napa Valley Vineyard", "Food"));
        e.add(new Event("Tech Innovators Conference", "15-08-2024", "Silicon Valley Expo Center", "Tech"));
        e.add(new Event("Autumn Art and Sculpture Exhibition", "18-09-2024", "Paris Art Museum", "Art"));
        e.add(new Event("Global Startup Pitch Event", "22-11-2024", "Berlin Startup Hub", "Tech"));
        e.add(new Event("International Film and Documentary Festival", "05-11-2024", "Toronto Film Centre", "Art"));
        return e;

    }
    private  ArrayList<Product> fillProducts(){
        ArrayList<Product> p = new ArrayList<>();
        p.add(new Product("Gourmet Pizza", "A delicious blend of flavors", 15.99, "New York", "Food"));
        p.add(new Product("Lemonade", "Refreshing and invigorating beverage", 3.49, "California", "Health"));
        p.add(new Product("Mixed Nuts", "Sweet and savory snacks", 5.99, "Texas", "Sports"));
        p.add(new Product("Croissants", "Freshly baked pastries", 2.99, "France", "Art"));
        p.add(new Product("Dark Chocolate Truffles", "Artisanal chocolates", 12.49, "Belgium", "Fashion"));

        return p;

    }

}