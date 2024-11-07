package com.example.fusmobilni.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
        // Set up RecyclerView with LayoutManager

        // Optionally, set up the adapter
        this._binding.eventsRecycleView.setAdapter(new EventsAdapter(events));
        this._binding.servicesRecycleView.setAdapter(new ServicesAdapter(services));
        this._binding.productsRecycleView.setAdapter(new ProductsAdapter(products));
        //this._binding.productsRecycleView.setAdapter();

        return view;
    }

    private ArrayList<Service> fillServices() {
        ArrayList<Service> s = new ArrayList<>();
        s.add(new Service("Live band for weddings and parties", "Wedding Band"));
        s.add(new Service("Professional photography for events", "Photography Service"));
        s.add(new Service("Catering services for all occasions", "Catering Service"));
        s.add(new Service("Event decoration and setup", "Decoration Service"));
        s.add(new Service("Spacious venue for corporate events", "Venue Rental"));
        return s;
    }

    private ArrayList<Event> fillEvents() {
        ArrayList<Event> e = new ArrayList<>();
        e.add(new Event("12", "July", "Food and Wine Tasting Festival", "2024", "Napa Valley Vineyard"));
        e.add(new Event("15", "August", "Tech Innovators Conference", "2024", "Silicon Valley Expo Center"));
        e.add(new Event("18", "September", "Autumn Art and Sculpture Exhibition", "2024", "Paris Art Museum"));
        e.add(new Event("22", "October", "Global Startup Pitch Event", "2024", "Berlin Startup Hub"));
        e.add(new Event("5", "November", "International Film and Documentary Festival", "2024", "Toronto Film Centre"));
        return e;

    }
    private  ArrayList<Product> fillProducts(){
        ArrayList<Product> p = new ArrayList<>();
        p.add(new Product("A delicious blend of flavors", "Gourmet Pizza"));
        p.add(new Product("Refreshing and invigorating beverage", "Lemonade"));
        p.add(new Product("Sweet and savory snacks", "Mixed Nuts"));
        p.add(new Product("Freshly baked pastries", "Croissants"));
        p.add(new Product("Artisanal chocolates", "Dark Chocolate Truffles"));
        return p;
    }

}