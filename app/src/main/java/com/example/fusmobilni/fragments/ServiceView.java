package com.example.fusmobilni.fragments;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.PupServiceAdapter;
import com.example.fusmobilni.databinding.FragmentServiceCreationBinding;
import com.example.fusmobilni.databinding.FragmentServiceViewBinding;
import com.example.fusmobilni.model.DummyService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class ServiceView extends Fragment {

    FragmentServiceViewBinding binding;
    private RecyclerView recyclerView;
    private PupServiceAdapter serviceAdapter;
    private List<DummyService> services;

    public ServiceView() {
        // Required empty public constructor
    }

    public static ServiceView newInstance(String param1, String param2) {
        ServiceView fragment = new ServiceView();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentServiceViewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        services = new ArrayList<>();
        services.add(new DummyService("Service 1", "Description 1"));
        services.add(new DummyService("Service 2", "Description 2"));
        services.add(new DummyService("Service 3", "Description 3"));
        services.add(new DummyService("Service 4", "Description 4"));
        services.add(new DummyService("Service 5", "Description 5"));

        serviceAdapter = new PupServiceAdapter(services);

        recyclerView.setAdapter(serviceAdapter);

        return view;
    }
}