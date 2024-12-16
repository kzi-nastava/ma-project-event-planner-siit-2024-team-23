package com.example.fusmobilni.fragments.items.service.Forms;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentMultiStepServiceFormTwoBinding;
import com.example.fusmobilni.model.enums.ReservationConfirmation;
import com.example.fusmobilni.viewModels.items.service.ServiceViewModel;


public class MultiStepServiceFormTwo extends Fragment {


    private FragmentMultiStepServiceFormTwoBinding binding;
    private ServiceViewModel viewModel;

    public MultiStepServiceFormTwo() {
        // Required empty public constructor
    }


    public static MultiStepServiceFormTwo newInstance() {
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
        viewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);
        View view = binding.getRoot();

        populateInputs();

        binding.backwardsButton.setOnClickListener(v -> {
            setValues();
            Navigation.findNavController(view).navigate(R.id.action_serviceCreationStepTwo_toServiceCreationStepOne);
        });

        binding.forwardButton.setOnClickListener(v -> {
            setValues();
            if (validate()) {
                Navigation.findNavController(view).navigate(R.id.action_serviceCreationStepTwo_toServiceCreationStepThree);
            }
        });

        if (viewModel.getIsUpdating().getValue()) {
            binding.textView2.setText("Update Service Form");
        }

        return view;
    }

    private void setValues() {
        viewModel.setSpecificities(String.valueOf(binding.specificitiesText.getText()));
        viewModel.setDuration(Integer.valueOf(String.valueOf(binding.serviceDurationInput.getText())));
        viewModel.setReservationDeadline(Integer.valueOf(String.valueOf(binding.reservationDeadlineInput.getText())));
        viewModel.setCancellationDeadline(Integer.valueOf(String.valueOf(binding.cancellationDeadlineInput.getText())));
        viewModel.setIsVisible(binding.visibilityCheckbox.isChecked());
        viewModel.setIsAvailable(binding.availabilityCheckbox.isChecked());
        if (binding.confirmationAutomatic.isChecked())
            viewModel.setIsAutomaticReservation(true);
        viewModel.setReservationConfirmation(ReservationConfirmation.AUTOMATIC);
        if(binding.confirmationManual.isChecked())
            viewModel.setIsAutomaticReservation(false);
        viewModel.setReservationConfirmation(ReservationConfirmation.MANUAL);
    }


    private void populateInputs() {
        viewModel.getSpecificities().observe(getViewLifecycleOwner(), specificities -> {
            binding.specificitiesText.setText(specificities);
        });

        viewModel.getDuration().observe(getViewLifecycleOwner(), duration -> {
            binding.serviceDurationInput.setText(String.valueOf(duration));
        });

        viewModel.getReservationDeadline().observe(getViewLifecycleOwner(), deadline -> {
            binding.reservationDeadlineInput.setText(String.valueOf(deadline));
        });

        viewModel.getCancellationDeadline().observe(getViewLifecycleOwner(), deadline -> {
            binding.cancellationDeadlineInput.setText(String.valueOf(deadline));
        });

        viewModel.getIsVisible().observe(getViewLifecycleOwner(), isVisible -> {
            binding.visibilityCheckbox.setChecked(isVisible);
        });

        viewModel.getIsAvailable().observe(getViewLifecycleOwner(), isAvailable -> {
            binding.availabilityCheckbox.setChecked(isAvailable);
        });

        viewModel.getIsAutomaticReservation().observe(getViewLifecycleOwner(), isAutomaticReservation -> {
            if (isAutomaticReservation != null) {
                if (isAutomaticReservation) {
                    binding.confirmationMethodGroup.check(R.id.confirmation_automatic);
                } else {
                    binding.confirmationMethodGroup.check(R.id.confirmation_manual);
                }
            }
        });
    }

    private boolean validate() {
        if (viewModel.getSpecificities().getValue().isEmpty()) {
            binding.specificitiesInput.setError("Specificities is required");
            binding.specificitiesInput.setErrorEnabled(true);
            return false;
        }
        if (viewModel.getDuration().getValue() == null || viewModel.getDuration().getValue() <= 0) {
            binding.serviceDurationField.setError("Duration must be greater than 0 ");
            binding.serviceDurationField.setErrorEnabled(true);
            return false;
        }
        if (viewModel.getReservationDeadline().getValue() == null || viewModel.getReservationDeadline().getValue() <= 0) {
            binding.reservationDeadlineField.setError("Deadline must be greater than 0 ");
            binding.reservationDeadlineField.setErrorEnabled(true);
            return false;
        }
        if (viewModel.getCancellationDeadline().getValue() == null || viewModel.getCancellationDeadline().getValue() <= 0) {
            binding.cancellationDeadlineField.setError("Deadline must be greater than 0 ");
            binding.cancellationDeadlineField.setErrorEnabled(true);
            return false;
        }
        return true;
    }

}