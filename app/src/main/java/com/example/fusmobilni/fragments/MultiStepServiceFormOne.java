package com.example.fusmobilni.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentMultiStepServiceFormOneBinding;
import com.example.fusmobilni.databinding.FragmentServiceCreationBinding;

public class MultiStepServiceFormOne extends Fragment {

//    private String serviceCategory;
//    private String eventType;
//    private String name;
//    private String description;
//    private double price;
//    private double discount;
    private FragmentMultiStepServiceFormOneBinding binding;


    public MultiStepServiceFormOne() {
    }

    public static MultiStepServiceFormOne newInstance() {
        return new MultiStepServiceFormOne();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMultiStepServiceFormOneBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        String[] categories = getResources().getStringArray(R.array.categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories);
        binding.autoCompleteTextviewStep1.setAdapter(adapter);

        String[] eventTypes = getResources().getStringArray(R.array.EventTypes);
        ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, eventTypes);
        binding.eventTypesInputStep1.setAdapter(eventAdapter);

        binding.materialButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_serviceCreationStepOne_toServiceCreationStepTwo);
        });

        return view;
    }
}