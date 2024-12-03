package com.example.fusmobilni.fragments.EventFragments.CreateEventSteps;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentCreateEventStepTwoBinding;
import com.example.fusmobilni.interfaces.FragmentValidation;

public class CreateEventStepTwo extends Fragment  implements FragmentValidation {
    private FragmentCreateEventStepTwoBinding _binding;

    public CreateEventStepTwo() {
        // Required empty public constructor
    }
    public static CreateEventStepTwo newInstance(String param1, String param2) {
        return new CreateEventStepTwo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentCreateEventStepTwoBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();
        return view;
    }

    @Override
    public boolean validate() {
        return true;
    }
}