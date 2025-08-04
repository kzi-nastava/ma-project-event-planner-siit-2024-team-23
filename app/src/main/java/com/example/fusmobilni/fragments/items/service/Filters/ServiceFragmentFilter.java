package com.example.fusmobilni.fragments.items.service.Filters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.fusmobilni.adapters.items.category.CategoryResponsesFilterAdapter;
import com.example.fusmobilni.databinding.FragmentServiceFilterBinding;
import com.example.fusmobilni.responses.items.CategoryResponse;
import com.example.fusmobilni.responses.location.LocationResponse;
import com.example.fusmobilni.viewModels.items.service.filters.ServiceSearchViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceFragmentFilter#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceFragmentFilter extends BottomSheetDialogFragment {

    private ServiceSearchViewModel _viewModel;
    private RecyclerView _categoryRecyclerView;

    private RangeSlider _slider;

    private Double _sliderMinValue;
    private Double _sliderMaxValue;
    private Spinner _locationSpinner;
    private CategoryResponsesFilterAdapter _adapter;
    private FragmentServiceFilterBinding _binding;

    public static ServiceFragmentFilter newInstance(String param1, String param2) {
        ServiceFragmentFilter fragment = new ServiceFragmentFilter();


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _sliderMinValue = getArguments().getDouble("minValue");
            _sliderMaxValue = getArguments().getDouble("maxValue");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _binding = FragmentServiceFilterBinding.inflate(inflater, container, false);
        View view = _binding.getRoot();

        _categoryRecyclerView = _binding.categoryRecyclerViewServices;

        _viewModel = new ViewModelProvider(getParentFragment()).get(ServiceSearchViewModel.class);

        initializeSlider();

        _adapter = new CategoryResponsesFilterAdapter(_viewModel.getCategories().getValue());

        _locationSpinner = _binding.spinnerServicesLOcation;
        _categoryRecyclerView.setAdapter(_adapter);

        _binding.servicesFilterApplyButton.setOnClickListener(v -> {
            initializeApplyButton();
        });
        _binding.servicesFilterResetButton.setOnClickListener(v -> {
            _viewModel.resetPage();
            _viewModel.resetFilters();
            _adapter.resetCategories();
            dismiss();
        });
        initializeSpinner(_viewModel.getLocations().getValue());
        initializeFields();

        return view;
    }

    private int findLocation(String city) {
        for (int i = 0; i < _viewModel.getLocations().getValue().size(); ++i) {
            if (city.equals(_viewModel.getLocations().getValue().get(i).getCity())) {
                return i;
            }
        }
        return -1;
    }

    private void initializeApplyButton() {
        _viewModel.resetPage();
        CategoryResponse category = _adapter.getSelectedCategory();
        _viewModel.setCategory(category);
        if (_viewModel.getLocation().getValue() != null)
            _locationSpinner.setSelection(findLocation(_viewModel.getLocation().getValue().getCity()));
        else
            _locationSpinner.setSelection(_viewModel.getLocations().getValue().size());
        List<Float> valueList = _slider.getValues();

        _viewModel.setSelectedMaxPrice(Double.valueOf(valueList.get(1)));

        _viewModel.setSelectedMinPrice(Double.valueOf(valueList.get(0)));


        _viewModel.doFilter();

        dismiss();
    }

    private void initializeFields() {
        if (_viewModel.getCategory().getValue() != null) {
            _adapter.setSelectedCategory(_viewModel.getCategory().getValue().getId());
        }

        if (_viewModel.getLocation().getValue() != null)
            _locationSpinner.setSelection(findLocation(_viewModel.getLocation().getValue().getCity()));
        else
            _locationSpinner.setSelection(_viewModel.getLocations().getValue().size());
        _slider.setValues(Float.valueOf(String.valueOf(_viewModel.getSelectedMinPrice().getValue())), Float.valueOf(String.valueOf(_viewModel.getSelectedMaxPrice().getValue())));

    }

    private void initializeSpinner(List<LocationResponse> locations) {

        List<String> cityNames = new ArrayList<>();
        for (LocationResponse l : locations) {
            cityNames.add(l.getCity());
        }
        cityNames.add("");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, cityNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _locationSpinner.setAdapter(adapter);
        _locationSpinner.setSelection(adapter.getPosition(""));
        _locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCity = parent.getItemAtPosition(position).toString();
                LocationResponse location = null;
                for (LocationResponse l : _viewModel.getLocations().getValue()) {
                    if (l.getCity().equals(selectedCity)) {
                        location = l;
                    }
                }
                _viewModel.setLocation(location);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initializeSlider() {
        _slider = _binding.priceSliderServices;

        _slider.setValueFrom(Float.valueOf(String.valueOf(_sliderMinValue)));
        _slider.setValueTo(Float.valueOf(String.valueOf(_sliderMaxValue)));
        _slider.setValues(_sliderMinValue.floatValue(), _sliderMaxValue.floatValue());
    }

}