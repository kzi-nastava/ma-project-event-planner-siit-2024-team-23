package com.example.fusmobilni.fragments.EventFragments.CreateEventSteps;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentCreateEventStepThreeBinding;
import com.example.fusmobilni.interfaces.FragmentValidation;

public class CreateEventStepThree extends Fragment implements FragmentValidation {
    FragmentCreateEventStepThreeBinding _binding;
    public CreateEventStepThree() {
        // Required empty public constructor
    }
    public static CreateEventStepThree newInstance() {
        return new CreateEventStepThree();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentCreateEventStepThreeBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();
        return view;
    }

    @Override
    public boolean validate() {
        return true;
    }
}