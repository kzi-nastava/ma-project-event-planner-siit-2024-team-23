package com.example.fusmobilni.fragments;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.PupServiceAdapter;
import com.example.fusmobilni.databinding.FragmentServiceCreationBinding;
import com.example.fusmobilni.databinding.FragmentServiceViewBinding;
import com.example.fusmobilni.interfaces.DeleteServiceListener;
import com.example.fusmobilni.model.DummyService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class ServiceView extends Fragment implements DeleteServiceListener {

    FragmentServiceViewBinding binding;
    private RecyclerView recyclerView;
    private PupServiceAdapter serviceAdapter;
    private View modalBackground;
    private View deleteModal;

    private SearchView searchView;
    private FloatingActionButton floatingActionButton;
    private List<DummyService> services = new ArrayList<>();

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

        addDummyData();

        serviceAdapter = new PupServiceAdapter(services, this);
        recyclerView.setAdapter(serviceAdapter);

        floatingActionButton = binding.floatingActionButton;
        floatingActionButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_serviceView_toServiceCreation);
        });
        modalBackground = binding.modalBackground;
        deleteModal = view.findViewById(R.id.nigger);
        searchView = binding.searchView;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                serviceAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return view;
    }

    @Override
    public void onDeleteService(int position) {
        modalBackground.setVisibility(View.VISIBLE);
        deleteModal.setVisibility(View.VISIBLE);
        Button cancelButton = deleteModal.findViewById(R.id.cancelButton);
        Button confirmButton = deleteModal.findViewById(R.id.confirmButton);

        cancelButton.setOnClickListener(v -> {
            modalBackground.setVisibility(View.INVISIBLE);
            deleteModal.setVisibility(View.INVISIBLE);
        });

        confirmButton.setOnClickListener(v -> {
            this.services.remove(position);
            serviceAdapter.notifyItemRemoved(position);
            serviceAdapter.notifyItemRangeChanged(position, services.size());
            modalBackground.setVisibility(View.INVISIBLE);
            deleteModal.setVisibility(View.INVISIBLE);
        });
    }

    public void addDummyData() {
        services.add(new DummyService("Service 1", "Description 1"));
        services.add(new DummyService("Service 2", "Description 2"));
        services.add(new DummyService("Service 3", "Description 3"));
        services.add(new DummyService("Service 4", "Description 4"));
        services.add(new DummyService("Service 5", "Description 5"));
    }

}