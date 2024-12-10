package com.example.fusmobilni.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.fusmobilni.R;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.model.PrototypeService;
import com.example.fusmobilni.requests.categories.GetCategoriesResponse;
import com.example.fusmobilni.requests.eventTypes.GetEventTypesResponse;
import com.example.fusmobilni.viewModels.ServiceProviderViewModel;
import com.example.fusmobilni.viewModels.ServiceSearchViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceProviderFilterFragment extends BottomSheetDialogFragment {

    private ServiceProviderViewModel viewModel;

    private GetCategoriesResponse categories;
    private GetEventTypesResponse eventTypes;
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
        Call<GetCategoriesResponse> categoriesCall = ClientUtils.categoryService.findAll();
        categoriesCall.enqueue(new Callback<GetCategoriesResponse>() {
            @Override
            public void onResponse(Call<GetCategoriesResponse> call, Response<GetCategoriesResponse> response) {
                categories = response.body();
                ArrayList<String> categoryNames = categories.categories.stream()
                        .map(category -> category.name)
                        .collect(Collectors.toCollection(ArrayList::new));
                ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categoryNames);
                categoryDropdown.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Call<GetCategoriesResponse> call, Throwable t) {
            }
        });

        Call<GetEventTypesResponse> eventTypesCall = ClientUtils.eventTypeService.findAll();
        eventTypesCall.enqueue(new Callback<GetEventTypesResponse>() {
            @Override
            public void onResponse(Call<GetEventTypesResponse> call, Response<GetEventTypesResponse> response) {
                eventTypes = response.body();
                ArrayList<String> eventTypeNames = eventTypes.eventTypes.stream()
                        .map(category -> category.name)
                        .collect(Collectors.toCollection(ArrayList::new));
                ArrayAdapter<String> eventTypeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, eventTypeNames);
                eventTypeDropdown.setAdapter(eventTypeAdapter);
            }

            @Override
            public void onFailure(Call<GetEventTypesResponse> call, Throwable t) {
            }
        });
        initializeInputs(view);
        setupSwitchListeners();
        setupPriceRangeSlider();
        setupDialogButtons(view);
        initializeFields();
        setUpDropdownListeners();
        return view;
    }

    private void setUpDropdownListeners() {
        categoryDropdown.setOnItemClickListener((parent, v, position, id) -> {
            viewModel.setCategoryId(categories.categories.get(position).id);
        });
        eventTypeDropdown.setOnItemClickListener((parent, v, position, id) -> {
            viewModel.setEventTypeId(eventTypes.eventTypes.get(position).id);
        });
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
            viewModel.applyFilters();
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
        priceRangeSlider.setValueTo(100000f);
        priceRangeSlider.setStepSize(1f);
        priceRangeSlider.setLabelFormatter(value -> "$" + String.format("%.0f", value));
    }
}