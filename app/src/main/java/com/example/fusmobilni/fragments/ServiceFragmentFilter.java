package com.example.fusmobilni.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.CategoryFilterAdapter;
import com.example.fusmobilni.databinding.FragmentServiceFilterBinding;
import com.example.fusmobilni.interfaces.OnFilterProductsApplyListener;
import com.example.fusmobilni.interfaces.OnFilterServicesApplyListener;
import com.example.fusmobilni.model.Category;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceFragmentFilter#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceFragmentFilter extends BottomSheetDialogFragment {

    private String _selectedCategory = "";
    private String _selectedLocation = "";
    private RecyclerView _categoryRecyclerView;
    private List<Category> _categories;
    private Spinner _locationSpinner;
    private CategoryFilterAdapter _adapter;
    private FragmentServiceFilterBinding _binding;
    private OnFilterServicesApplyListener _filterListener;

    public static ServiceFragmentFilter newInstance(String param1, String param2) {
        ServiceFragmentFilter fragment = new ServiceFragmentFilter();
        Bundle args = new Bundle();

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


        this._categories = Arrays.asList(
                new Category("Sports", R.drawable.ic_category_sports_active, R.drawable.ic_category_sports_inactive),
                new Category("Music", R.drawable.ic_category_music_active, R.drawable.ic_category_music_inactive),
                new Category("Art", R.drawable.ic_category_arts_active, R.drawable.ic_category_arts_inactive),
                new Category("Food", R.drawable.ic_category_food_active, R.drawable.ic_category_food_inactive),
                new Category("Tech", R.drawable.ic_category_sports_active, R.drawable.ic_category_sports_inactive),
                new Category("Travel", R.drawable.ic_category_music_active, R.drawable.ic_category_music_inactive),
                new Category("Education", R.drawable.ic_category_arts_active, R.drawable.ic_category_arts_inactive),
                new Category("Health", R.drawable.ic_category_sports_active, R.drawable.ic_category_sports_inactive),
                new Category("Fashion", R.drawable.ic_category_music_active, R.drawable.ic_category_music_inactive)
        );

        _adapter = new CategoryFilterAdapter(_categories);
        _categoryRecyclerView.setAdapter(_adapter);

        _binding.servicesFilterApplyButton.setOnClickListener(v->{
            Category category = _adapter.getSelectedCategory();
            _selectedCategory = (category != null) ? category.getName() : "";


            _selectedLocation = String.valueOf(_locationSpinner.getSelectedItem());
            if (_filterListener != null) {
                _filterListener.onFilterApply(_selectedCategory, _selectedLocation);
            }
            dismiss();
        });
        _locationSpinner = _binding.spinnerServicesLOcation;
        initializeSpinner();
        return view;
    }
    private void initializeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.service_locations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _locationSpinner.setAdapter(adapter);
    }
    public void set_filterListener(OnFilterServicesApplyListener listener) {
        this._filterListener = listener;
    }
}