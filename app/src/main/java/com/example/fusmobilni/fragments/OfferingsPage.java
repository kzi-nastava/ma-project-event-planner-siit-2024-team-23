package com.example.fusmobilni.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.CategoryAdapter;
import com.example.fusmobilni.adapters.PupServiceAdapter;
import com.example.fusmobilni.databinding.FragmentOfferingsPageBinding;
import com.example.fusmobilni.databinding.FragmentServiceViewBinding;
import com.example.fusmobilni.interfaces.CategoryListener;
import com.example.fusmobilni.model.OfferingsCategory;

import java.util.ArrayList;


public class OfferingsPage extends Fragment implements CategoryListener {

    private ArrayList<OfferingsCategory> categories = new ArrayList<>();
    private CategoryAdapter adapter;
    private FragmentOfferingsPageBinding binding;


    public OfferingsPage() {
        // Required empty public constructor
    }


    public static OfferingsPage newInstance(String param1, String param2) {
        return new OfferingsPage();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOfferingsPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        RecyclerView recycler = binding.categoryRecyclerView;
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        dummyData();
        this.adapter = new CategoryAdapter(this.categories, this);
        recycler.setAdapter(adapter);

        return view;
    }


    private void dummyData() {
        categories.add(new OfferingsCategory(1, "Sport", "Sport je jako zanimljiv i zabavan"));
        categories.add(new OfferingsCategory(2, "Food", "Sport je jako zanimljiv i zabavan"));
        categories.add(new OfferingsCategory(3, "Slavlje", "Sport je jako zanimljiv i zabavan"));
        categories.add(new OfferingsCategory(4, "Hronologija", "Sport je jako zanimljiv i zabavan"));
        categories.add(new OfferingsCategory(5, "Jelo", "Sport je jako zanimljiv i zabavan"));
    }

    @Override
    public void onDeleteCategory(int position) {

    }

    @Override
    public void onUpdateCategory(int position) {

    }
}