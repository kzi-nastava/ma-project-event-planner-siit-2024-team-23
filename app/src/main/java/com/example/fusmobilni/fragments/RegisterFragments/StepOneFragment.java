package com.example.fusmobilni.fragments.RegisterFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentRoleSelectionBinding;
import com.example.fusmobilni.databinding.FragmentStepOneBinding;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.viewModels.RegisterViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepOneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepOneFragment extends Fragment implements FragmentValidation {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentStepOneBinding _binding;

    private RegisterViewModel _registerViewModel;


    public StepOneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StepOneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StepOneFragment newInstance(String param1, String param2) {
        StepOneFragment fragment = new StepOneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentStepOneBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();
        _registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        return view;
    }
    @Override
    public boolean validate(){
            // Retrieve values from input fields
            String name = _binding.nameInput.getEditText().getText().toString().trim();
            String lastName = _binding.lastNameInput.getEditText().getText().toString().trim();
            String email = _binding.emailInput.getEditText().getText().toString().trim();
            String password = _binding.passwordInput.getEditText().getText().toString();
            String repeatPassword = _binding.repeatPasswordInput.getEditText().getText().toString();

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

            return true; // All validations passed
    }

}