package com.example.fusmobilni.fragments.users.register.regular;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.fusmobilni.databinding.FragmentStepThreeBinding;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.viewModels.users.register.RegisterViewModel;
import java.util.Objects;


public class StepThreeFragment extends Fragment implements FragmentValidation {
    private FragmentStepThreeBinding _binding;
    private RegisterViewModel _registerViewModel;


    public StepThreeFragment() {
        // Required empty public constructor
    }

    public static StepThreeFragment newInstance() {
        return new StepThreeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentStepThreeBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();
        _registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        return view;
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

        _registerViewModel.setCompanyName(companyName);
        _registerViewModel.setCompanyDescription(companyDescription);

        return true;
    }
}