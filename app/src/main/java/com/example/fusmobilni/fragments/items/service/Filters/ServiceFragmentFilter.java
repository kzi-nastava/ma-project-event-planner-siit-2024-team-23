package com.example.fusmobilni.fragments.items.service.Filters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.items.category.CategoryFilterAdapter;
import com.example.fusmobilni.databinding.FragmentServiceFilterBinding;
import com.example.fusmobilni.model.items.category.Category;
import com.example.fusmobilni.viewModels.items.service.filters.ServiceSearchViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceFragmentFilter#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceFragmentFilter extends BottomSheetDialogFragment {

    private ServiceSearchViewModel _viewModel;
    private RecyclerView _categoryRecyclerView;
    private List<Category> _categories;
    private Spinner _locationSpinner;
    private CategoryFilterAdapter _adapter;
    private FragmentServiceFilterBinding _binding;

    public static ServiceFragmentFilter newInstance(String param1, String param2) {
        ServiceFragmentFilter fragment = new ServiceFragmentFilter();


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _binding = FragmentServiceFilterBinding.inflate(inflater, container, false);
        View view = _binding.getRoot();

        _categoryRecyclerView = _binding.categoryRecyclerViewServices;

        _viewModel = new ViewModelProvider(requireActivity()).get(ServiceSearchViewModel.class);


        this._categories = fillCategories();

        _adapter = new CategoryFilterAdapter(_categories);
        _categoryRecyclerView.setAdapter(_adapter);

        _binding.servicesFilterApplyButton.setOnClickListener(v -> {
            initializeApplyButton();
        });
        _binding.servicesFilterResetButton.setOnClickListener(v -> {
            _viewModel.resetFilters();dismiss();
        });
        _locationSpinner = _binding.spinnerServicesLOcation;
        initializeSpinner();
        initializeFields();

        return view;
    }

    private void initializeApplyButton() {
        Category category = _adapter.getSelectedCategory();
        _viewModel.setCategory(category);
        _viewModel.setLocation(String.valueOf(_locationSpinner.getSelectedItem()));
        dismiss();
    }

    private void initializeFields() {
        if (_viewModel.getCategory().getValue() != null) {
            _adapter.setSelectedCategory(_viewModel.getCategory().getValue().getId());
        }

        _locationSpinner.setSelection(extractLocationPosition(_viewModel.getLocation().getValue()));
    }

    private int extractLocationPosition(String location) {
        String[] locations = extractLocations();
        for (int i = 0; i < locations.length; ++i) {
            if (locations[i].equals(location)) {
                return i;
            }
        }
        return -1;
    }

    private String[] extractLocations() {
        return getResources().getStringArray(R.array.service_locations);
    }

    private List<Category> fillCategories() {
        return Arrays.asList(
                new Category(1, "Sports", R.drawable.ic_category_sports_active, R.drawable.ic_category_sports_inactive),
                new Category(2, "Music", R.drawable.ic_category_music_active, R.drawable.ic_category_music_inactive),
                new Category(3, "Art", R.drawable.ic_category_arts_active, R.drawable.ic_category_arts_inactive),
                new Category(4, "Food", R.drawable.ic_category_food_active, R.drawable.ic_category_food_inactive),
                new Category(5, "Tech", R.drawable.ic_category_sports_active, R.drawable.ic_category_sports_inactive),
                new Category(6, "Travel", R.drawable.ic_category_music_active, R.drawable.ic_category_music_inactive),
                new Category(7, "Education", R.drawable.ic_category_arts_active, R.drawable.ic_category_arts_inactive),
                new Category(8, "Health", R.drawable.ic_category_sports_active, R.drawable.ic_category_sports_inactive),
                new Category(9, "Fashion", R.drawable.ic_category_music_active, R.drawable.ic_category_music_inactive)
        );
    }

    private void initializeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.service_locations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _locationSpinner.setAdapter(adapter);
    }

}