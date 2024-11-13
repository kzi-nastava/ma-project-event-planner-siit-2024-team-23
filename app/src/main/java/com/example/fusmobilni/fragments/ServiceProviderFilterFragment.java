package com.example.fusmobilni.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.PrototypeService;
import com.example.fusmobilni.viewModels.ServiceProviderViewModel;
import com.example.fusmobilni.viewModels.ServiceSearchViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceProviderFilterFragment extends BottomSheetDialogFragment {

    private ServiceProviderViewModel viewModel;
    private AutoCompleteTextView categoryDropdown;
    private AutoCompleteTextView eventTypeDropdown;
    private RangeSlider priceRangeSlider;
    private SwitchMaterial availabilitySwitch;
    private SwitchMaterial enableAvailabilitySwitch;


    public ServiceProviderFilterFragment() {
        // Required empty public constructor
    }

    public static ServiceProviderFilterFragment newInstance() {
        return new ServiceProviderFilterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_service_provider_filter, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ServiceProviderViewModel.class);
        initializeInputs(view);
        setupSwitchListeners();
        setupPriceRangeSlider();
        setupDropdowns();
        setupDialogButtons(view);
        initializeFields();
        return view;
    }

    private void setupSwitchListeners() {
        enableAvailabilitySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            availabilitySwitch.setEnabled(isChecked);
        });
    }


    private void initializeFields() {
        viewModel.getCategory().observe(getViewLifecycleOwner(), category -> {
            if (!Objects.equals(category, "")) {
                categoryDropdown.setText(category, false);
            }

        });

        viewModel.getEventType().observe(getViewLifecycleOwner(), eventType -> {
            if (!Objects.equals(eventType, "")) {
                eventTypeDropdown.setText(eventType, false);
            }

        });

        priceRangeSlider.setValues(viewModel.getLowerBoundaryPrice().getValue().floatValue(),
                viewModel.getUpperBoundaryPrice().getValue().floatValue());

        viewModel.getIsAvailabilityEnabled().observe(getViewLifecycleOwner(), isAvailabilityEnabled -> {
            enableAvailabilitySwitch.setChecked(isAvailabilityEnabled);
        });

        viewModel.getAvailability().observe(getViewLifecycleOwner(), availability -> {
            availabilitySwitch.setChecked(availability);
        });
    }


    private void setupDialogButtons(View dialogView) {
        Button resetBtn = dialogView.findViewById(R.id.resetButton);
        Button applyBtn = dialogView.findViewById(R.id.applyButton);

        resetBtn.setOnClickListener(v -> {
            viewModel.resetFilters();
            dismiss();
        });

        applyBtn.setOnClickListener(v -> {
            initializeFilterValues();
            dismiss();
        });
    }

    private void initializeInputs(View dialogView) {
        categoryDropdown = dialogView.findViewById(R.id.text_services_category);
        eventTypeDropdown = dialogView.findViewById(R.id.text_event_type);
        priceRangeSlider = dialogView.findViewById(R.id.range_slider_price);
        availabilitySwitch = dialogView.findViewById(R.id.switch_availability);
        availabilitySwitch.setEnabled(false);
        enableAvailabilitySwitch = dialogView.findViewById(R.id.enable_availability_switch);
        enableAvailabilitySwitch.setChecked(false);
    }

    private void initializeFilterValues() {
        viewModel.setCategory(categoryDropdown.getText().toString());
        viewModel.setEventType(eventTypeDropdown.getText().toString());
        List<Float> priceRangeValues = priceRangeSlider.getValues();
        viewModel.setLowerBoundaryPrice(Double.valueOf(priceRangeValues.get(0)));
        viewModel.setUpperBoundaryPrice(Double.valueOf(priceRangeValues.get(1)));
        viewModel.setIsAvailabilityEnabled(enableAvailabilitySwitch.isChecked());
        viewModel.setAvailability(availabilitySwitch.isChecked());
    }

    @SuppressLint("DefaultLocale")
    private void setupPriceRangeSlider() {
        priceRangeSlider.setValueFrom(0f);
        priceRangeSlider.setValueTo(10000f);
        priceRangeSlider.setStepSize(1f);
        priceRangeSlider.setLabelFormatter(value -> "$" + String.format("%.0f", value));
    }

    private void setupDropdowns() {
        String[] categories = getResources().getStringArray(R.array.categories);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories);
        categoryDropdown.setAdapter(categoryAdapter);

        String[] eventTypes = getResources().getStringArray(R.array.EventTypes);
        ArrayAdapter<String> eventTypeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, eventTypes);
        eventTypeDropdown.setAdapter(eventTypeAdapter);
    }
}