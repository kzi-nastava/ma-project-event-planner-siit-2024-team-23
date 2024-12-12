package com.example.fusmobilni.fragments.items.product.filters;

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
import com.example.fusmobilni.databinding.FragmentProductFilterBinding;
import com.example.fusmobilni.model.items.category.Category;
import com.example.fusmobilni.viewModels.items.product.filters.ProductSearchViewModel;
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
    private ProductSearchViewModel _viewModel;

    private RecyclerView _categoryRecyclerView;
    private List<Category> _categories;
    private FragmentProductFilterBinding _binding;
    private Spinner _locationSpinner;
    private CategoryFilterAdapter _adapter;
    private RangeSlider _slider;
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

        initializeSlider();

        _viewModel = new ViewModelProvider(requireActivity()).get(ProductSearchViewModel.class);

        this._categories = fillCategories();

        _adapter = new CategoryFilterAdapter(_categories);
        _categoryRecyclerView.setAdapter(_adapter);

        _binding.productsFilterApplyButton.setOnClickListener(v -> {
            initializeApplyButton();
        });
        _binding.servicesFilterResetButton.setOnClickListener(v->{
            _viewModel.resetFilters();dismiss();
        });

        _locationSpinner = _binding.spinnerProductsLOcation;
        initializeSpinner();
        initializeFields();
        return view;
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
    private void initializeSlider(){
        _slider = _binding.priceSliderProducts;

        _slider.setValueFrom(Float.valueOf(String.valueOf(_sliderMinValue)));
        _slider.setValueTo(Float.valueOf(String.valueOf(_sliderMaxValue)));
        _slider.setValues(_sliderMinValue.floatValue(), _sliderMaxValue.floatValue());
    }
    private void initializeApplyButton() {
        Category category = _adapter.getSelectedCategory();
        _viewModel.setCategory(category);


        _viewModel.setLocation(String.valueOf(_locationSpinner.getSelectedItem()));
        List<Float> valueList = _slider.getValues();
        _viewModel.setMaxSelectedPrice(Double.valueOf(valueList.get(1)));

        _viewModel.setMinSelectedPrice(Double.valueOf(valueList.get(0)));

        dismiss();
    }

    private void initializeFields() {
        if (_viewModel.getCategory().getValue() != null) {
            _adapter.setSelectedCategory(_viewModel.getCategory().getValue().getId());
        }
        _slider.setValues(Float.valueOf(String.valueOf(_viewModel.getMinSelectedPrice().getValue())), Float.valueOf(String.valueOf(_viewModel.getMaxSelectedPrice().getValue())));

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
        return getResources().getStringArray(R.array.product_locations);
    }

    private void initializeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.product_locations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _locationSpinner.setAdapter(adapter);
    }
}