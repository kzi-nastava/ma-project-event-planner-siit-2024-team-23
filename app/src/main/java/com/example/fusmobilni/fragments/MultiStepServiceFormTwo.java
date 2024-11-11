package com.example.fusmobilni.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentMultiStepServiceFormOneBinding;
import com.example.fusmobilni.databinding.FragmentMultiStepServiceFormTwoBinding;


public class MultiStepServiceFormTwo extends Fragment {


    private FragmentMultiStepServiceFormTwoBinding binding;

    public MultiStepServiceFormTwo() {
        // Required empty public constructor
    }


    public static MultiStepServiceFormTwo newInstance(String param1, String param2) {
        return new MultiStepServiceFormTwo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMultiStepServiceFormTwoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.backwardsButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_serviceCreationStepTwo_toServiceCreationStepOne);
        });

        return view;
    }
}