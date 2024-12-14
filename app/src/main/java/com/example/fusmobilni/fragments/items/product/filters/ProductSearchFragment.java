package com.example.fusmobilni.fragments.items.product.filters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.items.product.ProductsHorizontalAdapter;
import com.example.fusmobilni.adapters.items.product.ProductsResponseAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentProductSearchBinding;
import com.example.fusmobilni.model.items.product.Product;
import com.example.fusmobilni.responses.items.CategoriesResponse;
import com.example.fusmobilni.responses.items.products.filter.ProductLocationsResponse;
import com.example.fusmobilni.responses.items.products.filter.ProductPaginationResponse;
import com.example.fusmobilni.responses.items.products.filter.ProductsMinMaxPriceResponse;
import com.example.fusmobilni.responses.items.products.filter.ProductsPaginationResponse;
import com.example.fusmobilni.responses.items.services.filter.ServiceLocationsResponse;
import com.example.fusmobilni.responses.items.services.filter.ServicePaginationResponse;
import com.example.fusmobilni.responses.items.services.filter.ServicesMinMaxPriceResponse;
import com.example.fusmobilni.responses.items.services.filter.ServicesPaginationResponse;
import com.example.fusmobilni.viewModels.items.product.filters.ProductSearchViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductSearchFragment extends Fragment {

    private ProductSearchViewModel _viewModel;
    private FragmentProductSearchBinding _binding;
    private TextInputLayout _searchView;
    private ArrayList<Product> _products;
    private RecyclerView _listView;
    private ProductsResponseAdapter _productsAdapter;
    private Spinner _paginationSpinner;
    private MaterialButton _prevButton;
    private MaterialButton _nextButton;
    private Double _minValue;
    private Double _maxValue;

    public ProductSearchFragment() {
        // Required empty public constructor
    }

    public static ProductSearchFragment newInstance(String param1, String param2) {
        ProductSearchFragment fragment = new ProductSearchFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _binding = FragmentProductSearchBinding.inflate(inflater, container, false);
        View view = _binding.getRoot();


        _listView = _binding.productList;
        _searchView = _binding.searchTextInputLayoutProducts;

        initializeButtons();
        fetchProducts();
        fetchLocations();
        fetchCategories();
        fetchMinMaxPrice();

        _productsAdapter = new ProductsResponseAdapter();
        _listView.setAdapter(_productsAdapter);

        _viewModel = new ViewModelProvider(requireActivity()).get(ProductSearchViewModel.class);

        _binding.productsFilterButton.setOnClickListener(v -> {
            openFilterFragment();
        });

        _binding.searchProductsButton.setOnClickListener(v -> {
            _viewModel.setConstraint(_searchView.getEditText().getText().toString().trim());
        });


        _viewModel.getConstraint().observe(getViewLifecycleOwner(), observer -> {
            _productsAdapter.setData(_viewModel.getProducts().getValue());
        });
        _viewModel.getProducts().observe(getViewLifecycleOwner(), observer -> {
            _productsAdapter.setData(_viewModel.getProducts().getValue());
        });
        initializePaginationSpinner();

        return view;
    }

    private void openFilterFragment() {

        ProductFilterFragment filterFragment = new ProductFilterFragment();
        Bundle bundle = new Bundle();
        if (_minValue != null && _maxValue != null) {
            bundle.putDouble("minValue", _minValue);
            bundle.putDouble("maxValue", _maxValue);
            filterFragment.setArguments(bundle);
            filterFragment.show(getParentFragmentManager(), filterFragment.getTag());
        }

    }

    private void initializeButtons() {

        _prevButton = this._binding.productSearchPreviousButton;
        _nextButton = this._binding.productSearchNextButton;

        _prevButton.setOnClickListener(v ->
                _viewModel.prevPage());
        _nextButton.setOnClickListener(v -> _viewModel.nextPage());


    }

    private void fetchCategories() {
        Call<CategoriesResponse> call = ClientUtils.productsService.findCategoriesForProducts();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                if (response.isSuccessful()) {
                    _viewModel.setCategories(response.body().getCategories());
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {

            }
        });
    }

    private void fetchMinMaxPrice() {
        Call<ProductsMinMaxPriceResponse> call = ClientUtils.productsService.findMinMaxPrice();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ProductsMinMaxPriceResponse> call, Response<ProductsMinMaxPriceResponse> response) {
                if (response.isSuccessful()) {
                    _minValue = response.body().minPrice;
                    _maxValue = response.body().maxPrice;

                    _viewModel.setMinSelectedPrice(_minValue);
                    _viewModel.setMaxSelectedPrice(_maxValue);
                    _viewModel.setLowerBoundPrice(_minValue);
                    _viewModel.setUpperBoundPrice(_maxValue);
                }
            }

            @Override
            public void onFailure(Call<ProductsMinMaxPriceResponse> call, Throwable t) {

            }
        });
    }

    private void fetchLocations() {
        Call<ProductLocationsResponse> call = ClientUtils.productsService.findProductLocations();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ProductLocationsResponse> call, Response<ProductLocationsResponse> response) {
                if (response.isSuccessful()) {
                    _viewModel.setLocations(response.body().getLocations());
                }
            }

            @Override
            public void onFailure(Call<ProductLocationsResponse> call, Throwable t) {

            }
        });
    }

    private void fetchProducts() {
        Call<ProductsPaginationResponse> call = ClientUtils.productsService.findFilteredAndPaginated(0, 5);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ProductsPaginationResponse> call, Response<ProductsPaginationResponse> response) {
                if (response.isSuccessful()) {
                    List<ProductPaginationResponse> responses = response.body().content;
                    long totalItems = response.body().totalItems;
                    int totalPages = response.body().totalPages;

                    _viewModel.setProducts(responses);
                    _viewModel.setTotalItems(totalItems);
                    _viewModel.setTotalPages(totalPages);
                }
            }

            @Override
            public void onFailure(Call<ProductsPaginationResponse> call, Throwable t) {

            }
        });
    }


    private void initializePaginationSpinner() {
        _paginationSpinner = _binding.paginationSpinnerProducts;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.paginationPageSizes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _paginationSpinner.setAdapter(adapter);
        _paginationSpinner.setSelection(0);
        _paginationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedItem = Integer.parseInt(String.valueOf(parent.getItemAtPosition(position)));
                _viewModel.setPageSize(selectedItem);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}