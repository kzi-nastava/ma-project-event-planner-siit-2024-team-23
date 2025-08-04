package com.example.fusmobilni.fragments.users.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.databinding.FragmentStepTwoUpdateBinding;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.viewModels.users.UpdateProfileViewModel;

import java.util.Objects;


public class StepTwoUpdateFragment extends Fragment implements FragmentValidation {

    private FragmentStepTwoUpdateBinding _binding;
    private UpdateProfileViewModel _updateprofileViewModel;

    public StepTwoUpdateFragment() {
        // Required empty public constructor
    }

    public static StepTwoUpdateFragment newInstance() {
        return new StepTwoUpdateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentStepTwoUpdateBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();
        _updateprofileViewModel = new ViewModelProvider(requireActivity()).get(UpdateProfileViewModel.class);
        populateInputs();
        return view;
    }
    private void populateInputs() {
        _updateprofileViewModel.getCompanyName().observe(getViewLifecycleOwner(),
                name -> Objects.requireNonNull(_binding.companyNameInput.getEditText()).setText(name));
        _updateprofileViewModel.getCompanyDescriptionName().observe(getViewLifecycleOwner(),
                description -> Objects.requireNonNull(_binding.companyDescriptionInput.getEditText()).setText(description));
    }

    @Override
    public boolean validate() {
        String companyName = Objects.requireNonNull(_binding.companyNameInput.getEditText()).getText().toString().trim();
        String companyDescription = Objects.requireNonNull(_binding.companyDescriptionInput.getEditText()).getText().toString().trim();

        if(companyName.isEmpty()){
            _binding.companyNameInput.setErrorEnabled(true);
            _binding.companyNameInput.setError("Company name is required");
            return false;
        }else{
            _binding.companyNameInput.setError(null);
            _binding.companyNameInput.setErrorEnabled(false);
        }

        if(companyDescription.isEmpty()){
            _binding.companyDescriptionInput.setErrorEnabled(true);
            _binding.companyDescriptionInput.setError("Company description is required");
            return false;
        }else{
            _binding.companyDescriptionInput.setError(null);
            _binding.companyDescriptionInput.setErrorEnabled(false);
        }
        _updateprofileViewModel.setCompanyName(companyName);
        _updateprofileViewModel.setCompanyDescription(companyDescription);

        return true;
    }
}