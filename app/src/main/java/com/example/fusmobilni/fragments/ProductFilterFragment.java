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
import com.example.fusmobilni.databinding.FragmentProductFilterBinding;
import com.example.fusmobilni.interfaces.OnFilterProductsApplyListener;
import com.example.fusmobilni.model.Category;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.slider.RangeSlider;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFilterFragment extends BottomSheetDialogFragment {

    private String _selectedCategory = "";
    private String _selectedLocation = "";
    private double _minPrice = 0.0;
    private double _maxPrice = 100.0;
    private RecyclerView _categoryRecyclerView;
    private List<Category> _categories;
    private FragmentProductFilterBinding _binding;
    private Spinner _locationSpinner;
    private CategoryFilterAdapter _adapter;
    private RangeSlider _slider;
    private OnFilterProductsApplyListener _filterListener;
    private Double _sliderMinValue;
    private Double _sliderMaxValue;

    public ProductFilterFragment() {
        // Required empty public constructor
    }

    public static ProductFilterFragment newInstance(String param1, String param2) {
        ProductFilterFragment fragment = new ProductFilterFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _sliderMinValue = getArguments().getDouble("min_slider");
            _sliderMaxValue = getArguments().getDouble("max_slider");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _binding = FragmentProductFilterBinding.inflate(inflater, container, false);
        View view = _binding.getRoot();
        _categoryRecyclerView = _binding.categoryRecyclerViewProducts;
        _slider = _binding.priceSliderProducts;

        _slider.setValueFrom(Float.valueOf(String.valueOf(_sliderMinValue)));
        _slider.setValueTo(Float.valueOf(String.valueOf(_sliderMaxValue)));
        _slider.setValues(_sliderMinValue.floatValue(), _sliderMaxValue.floatValue());

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

        _binding.productsFilterApplyButton.setOnClickListener(v -> {
            Category category = _adapter.getSelectedCategory();
            _selectedCategory = (category != null) ? category.getName() : "";


            _selectedLocation = String.valueOf(_locationSpinner.getSelectedItem());
            List<Float> valueList = _slider.getValues();
            _minPrice = valueList.get(0);
            _maxPrice = valueList.get(1);

            if (_filterListener != null) {
                _filterListener.onFilterApply(_selectedCategory, _minPrice,_maxPrice, _selectedLocation);
            }
            dismiss();
        });

        _locationSpinner = _binding.spinnerProductsLOcation;
        initializeSpinner();
        return view;
    }

    private void initializeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.event_locations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _locationSpinner.setAdapter(adapter);
    }

    public void set_filterListener(OnFilterProductsApplyListener listener) {
        this._filterListener = listener;
    }
}