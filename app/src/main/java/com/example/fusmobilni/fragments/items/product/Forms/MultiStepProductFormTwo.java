package com.example.fusmobilni.fragments.items.product.Forms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentMultiStepServiceFormTwoBinding;
import com.example.fusmobilni.viewModels.items.product.ProductViewModel;


public class MultiStepProductFormTwo extends Fragment {


    private FragmentMultiStepServiceFormTwoBinding binding;
    private ProductViewModel viewModel;

    public MultiStepProductFormTwo() {
        // Required empty public constructor
    }


    public static MultiStepProductFormTwo newInstance() {
        return new MultiStepProductFormTwo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMultiStepServiceFormTwoBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        View view = binding.getRoot();
        binding.serviceDurationField.setVisibility(View.GONE);
        binding.serviceMinDurationField.setVisibility(View.GONE);
        binding.serviceMaxDurationField.setVisibility(View.GONE);
        binding.reservationDeadlineField.setVisibility(View.GONE);
        binding.cancellationDeadlineField.setVisibility(View.GONE);
        binding.confirmationMethodLabel.setVisibility(View.GONE);
        binding.confirmationMethodGroup.setVisibility(View.GONE);

        populateInputs();

        binding.backwardsButton.setOnClickListener(v -> {
            setValues();
            Navigation.findNavController(view).navigate(R.id.action_productCreationStepTwo_toProductCreationStepOne);
        });

        binding.forwardButton.setOnClickListener(v -> {
            setValues();
            if (validate()) {
                Navigation.findNavController(view).navigate(R.id.action_productCreationStepTwo_toProductCreationStepThree);
            }
        });

        if (viewModel.getIsUpdating().getValue()) {
            binding.textView2.setText("Update Service Form");
        }

        return view;
    }

    private void setValues() {
        viewModel.setSpecificities(String.valueOf(binding.specificitiesText.getText()));
        viewModel.setIsVisible(binding.visibilityCheckbox.isChecked());
        viewModel.setIsAvailable(binding.availabilityCheckbox.isChecked());
    }


    private void populateInputs() {
        viewModel.getSpecificities().observe(getViewLifecycleOwner(), specificities -> {
            binding.specificitiesText.setText(specificities);
        });

        viewModel.getIsVisible().observe(getViewLifecycleOwner(), isVisible -> {
            binding.visibilityCheckbox.setChecked(isVisible);
        });

        viewModel.getIsAvailable().observe(getViewLifecycleOwner(), isAvailable -> {
            binding.availabilityCheckbox.setChecked(isAvailable);
        });
    }

    private boolean validate() {
        if (viewModel.getSpecificities().getValue().isEmpty()) {
            binding.specificitiesInput.setError("Specificities is required");
            binding.specificitiesInput.setErrorEnabled(true);
            return false;
        }
        return true;
    }

}