package com.example.fusmobilni.fragments.EventFragments.CreateEventSteps;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.ProductsHorizontalAdapter;
import com.example.fusmobilni.adapters.ServiceHorizontalAdapter;
import com.example.fusmobilni.databinding.FragmentChooseProductBinding;
import com.example.fusmobilni.model.Product;
import com.example.fusmobilni.model.Service;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;

public class ChooseProductFragment extends Fragment {
    private FragmentChooseProductBinding _binding;
    private ServiceHorizontalAdapter serviceHorizontalAdapter;
    private ProductsHorizontalAdapter productsHorizontalAdapter;
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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentChooseProductBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();
        listView = _binding.recyclerView;
        MaterialButtonToggleGroup toggleGroup = _binding.toggleGroup;

        serviceHorizontalAdapter = new ServiceHorizontalAdapter();
        productsHorizontalAdapter = new ProductsHorizontalAdapter();
        listView.setAdapter(serviceHorizontalAdapter);
        ArrayList<Service> services = fillServices();
        ArrayList<Product> products = fillProducts();
        serviceHorizontalAdapter.setData(services);
        productsHorizontalAdapter.setData(products);

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
    private  ArrayList<Product> fillProducts(){
        ArrayList<Product> p = new ArrayList<>();
        p.add(new Product("Gourmet Pizza", "A delicious blend of flavors", 15.99, "New York", "Food"));
        p.add(new Product("Lemonade", "Refreshing and invigorating beverage", 3.49, "California", "Health"));
        p.add(new Product("Mixed Nuts", "Sweet and savory snacks", 5.99, "Texas", "Sports"));
        p.add(new Product("Croissants", "Freshly baked pastries", 2.99, "France", "Art"));
        p.add(new Product("Dark Chocolate Truffles", "Artisanal chocolates", 12.49, "Belgium", "Fashion"));
        return p;
    }

    private ArrayList<Service> fillServices() {
        ArrayList<Service> s = new ArrayList<>();
        s.add(new Service("Live band for weddings and parties", "Wedding Band", "New York", "Music"));
        s.add(new Service("Professional photography for events", "Photography Service", "Los Angeles", "Art"));
        s.add(new Service("Catering services for all occasions", "Catering Service", "Chicago", "Food"));
        s.add(new Service("Event decoration and setup", "Decoration Service", "San Francisco", "Art"));
        s.add(new Service("Spacious venue for corporate events", "Venue Rental", "Miami", "Travel"));
        s.add(new Service("DJ service with customized playlists", "DJ Service", "Las Vegas", "Music"));
        s.add(new Service("Makeup and styling for events", "Makeup Service", "New York", "Fashion"));
        s.add(new Service("Mobile food truck service for events", "Food Truck Service", "Austin", "Food"));
        s.add(new Service("Event photography with instant prints", "Instant Photography", "Orlando", "Art"));
        return s;
    }
}