package com.example.fusmobilni.fragments.users.register.regular;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.fusmobilni.databinding.FragmentStepOneBinding;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.viewModels.users.register.RegisterViewModel;

import java.util.Objects;


public class StepOneFragment extends Fragment implements FragmentValidation {

    private FragmentStepOneBinding _binding;

    private RegisterViewModel _registerViewModel;


    public StepOneFragment() {
        // Required empty public constructor
    }

    public static StepOneFragment newInstance() {
        return new StepOneFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentStepOneBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();
        _registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        return view;
    }
    @Override
    public boolean validate(){
            // Retrieve values from input fields
            String name = Objects.requireNonNull(_binding.nameInput.getEditText()).getText().toString().trim();
            String lastName = Objects.requireNonNull(_binding.lastNameInput.getEditText()).getText().toString().trim();
            String email = Objects.requireNonNull(_binding.emailInput.getEditText()).getText().toString().trim();
            String password = Objects.requireNonNull(_binding.passwordInput.getEditText()).getText().toString();
            String repeatPassword = Objects.requireNonNull(_binding.repeatPasswordInput.getEditText()).getText().toString();

            // Validate each field
            if (name.isEmpty()) {
                _binding.nameInput.setErrorEnabled(true);
                _binding.nameInput.setError("Name is required");
                return false;
            } else {
                _binding.nameInput.setError(null);
                _binding.nameInput.setErrorEnabled(false);
            }

            if (lastName.isEmpty()) {
                _binding.lastNameInput.setErrorEnabled(true);
                _binding.lastNameInput.setError("Last name is required");
                return false;
            } else {
                _binding.lastNameInput.setError(null);
                _binding.lastNameInput.setErrorEnabled(false);
            }
            // TODO check if the email is in the db already - emails already in use!
            if (email.isEmpty()) {
                _binding.emailInput.setErrorEnabled(true);
                _binding.emailInput.setError("Email is required");
                return false;
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _binding.emailInput.setErrorEnabled(true);
                _binding.emailInput.setError("Invalid email format");
                return false;
            } else {
                _binding.emailInput.setError(null);
                _binding.emailInput.setErrorEnabled(false);
            }

            if (password.isEmpty()) {
                _binding.passwordInput.setErrorEnabled(true);
                _binding.passwordInput.setError("Password is required");
                return false;
            } else {
                _binding.passwordInput.setError(null);
                _binding.passwordInput.setErrorEnabled(false);
            }

            if (!password.equals(repeatPassword)) {
                _binding.repeatPasswordInput.setErrorEnabled(true);
                _binding.repeatPasswordInput.setError("Passwords do not match");
                return false;
            } else {
                _binding.repeatPasswordInput.setError(null);
                _binding.repeatPasswordInput.setErrorEnabled(false);
            }
            _registerViewModel.setName(name);
            _registerViewModel.setLastName(lastName);
            _registerViewModel.setEmail(email);
            _registerViewModel.setPassword(password);

            return true;
    }

}