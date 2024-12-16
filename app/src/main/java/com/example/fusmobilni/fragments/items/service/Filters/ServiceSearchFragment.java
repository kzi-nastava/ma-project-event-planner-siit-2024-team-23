package com.example.fusmobilni.fragments.items.service.Filters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.items.service.ServiceHorizontalAdapter;
import com.example.fusmobilni.adapters.loading.LoadingCardHorizontalAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentServiceSearchBinding;
import com.example.fusmobilni.model.items.service.Service;
import com.example.fusmobilni.responses.items.CategoriesResponse;
import com.example.fusmobilni.responses.items.services.filter.ServiceLocationsResponse;
import com.example.fusmobilni.responses.items.services.filter.ServicePaginationResponse;
import com.example.fusmobilni.responses.items.services.filter.ServicesMinMaxPriceResponse;
import com.example.fusmobilni.responses.items.services.filter.ServicesPaginationResponse;
import com.example.fusmobilni.viewModels.items.service.filters.ServiceSearchViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceSearchFragment extends Fragment {
    private ServiceSearchViewModel _viewModel;
    private FragmentServiceSearchBinding _binding;
    private TextInputLayout _searchView;
    private ArrayList<Service> _services;
    private RecyclerView _listView;
    private ServiceHorizontalAdapter _servicesAdapter;
    private Spinner _paginationSpinner;
    private MaterialButton _prevButton;
    private MaterialButton _nextButton;

    private Double _sliderMinValue = null;
    private Double _sliderMaxValue = null;

    public static ServiceSearchFragment newInstance(String param1, String param2) {
        ServiceSearchFragment fragment = new ServiceSearchFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
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

        _binding = FragmentServiceSearchBinding.inflate(inflater, container, false);
        View view = _binding.getRoot();

        _listView = _binding.serviceList;
        _searchView = _binding.searchTextInputLayoutService;
        initializeButtons();

        _servicesAdapter = new ServiceHorizontalAdapter();
        _listView.setAdapter(_servicesAdapter);

        _viewModel = new ViewModelProvider(requireParentFragment()).get(ServiceSearchViewModel.class);
        initializeLoadingCards();
        _binding.nestedServiceCards.setVisibility(View.GONE);

        fetchServices();
        fetchLocations();
        fetchCategories();
        fetchMinMaxPrice();

        _viewModel.getServices().observe(getViewLifecycleOwner(), observer -> {
            turnOnShimmer();
            delayedTask(() -> {
                _servicesAdapter.setData(_viewModel.getServices().getValue());
                _listView.setAdapter(_servicesAdapter);
                turnOffShimmer();
            });

        });

        _binding.buttonSearchServices.setOnClickListener(v -> {

            _viewModel.setConstraint(_searchView.getEditText().getText().toString());
        });

        _binding.serviceFilterButton.setOnClickListener(v -> {
            openFilterFragment();
        });

        initalizepaginationspinner();

        return view;
    }

    private void initializeLoadingCards() {
        _binding.loadingCards.setAdapter(new LoadingCardHorizontalAdapter(_viewModel.getPageSize().getValue()));
    }

    private void fetchServices() {
        Call<ServicesPaginationResponse> call = ClientUtils.serviceOfferingService.findFilteredAndPaginated(0, 5);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ServicesPaginationResponse> call, Response<ServicesPaginationResponse> response) {
                if (response.isSuccessful()) {
                    List<ServicePaginationResponse> responses = response.body().content;
                    long totalItems = response.body().totalItems;
                    int totalPages = response.body().totalPages;

                    _viewModel.setServices(responses);
                    _viewModel.setTotalItems(totalItems);
                    _viewModel.setTotalPages(totalPages);
                }
            }

            @Override
            public void onFailure(Call<ServicesPaginationResponse> call, Throwable t) {

            }
        });
    }

    private void delayedTask(Runnable onComplete) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            _binding.loadingCards.setAdapter(new LoadingCardHorizontalAdapter(_viewModel.getPageSize().getValue()));
            _binding.nestedLoadingCards.setVisibility(View.VISIBLE);
            _binding.nestedServiceCards.setVisibility(View.GONE);
            if (onComplete != null) {
                onComplete.run();
            }
        }, 1000);
    }

    private void turnOnShimmer() {
        _binding.loadingCards.setAdapter(new LoadingCardHorizontalAdapter(_viewModel.getPageSize().getValue()));
        _binding.nestedLoadingCards.setVisibility(View.VISIBLE);
        _binding.nestedServiceCards.setVisibility(View.GONE);
    }

    private void turnOffShimmer() {


        _binding.loadingCards.setAdapter(new LoadingCardHorizontalAdapter(0));
        _binding.nestedServiceCards.setVisibility(View.VISIBLE);
        _binding.nestedLoadingCards.setVisibility(View.GONE);


    }

    private void initalizepaginationspinner() {
        _paginationSpinner = _binding.paginationSpinnerservice;
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

    private void fetchCategories() {
        Call<CategoriesResponse> call = ClientUtils.serviceOfferingService.findCategoriesForServices();
        call.enqueue(new Callback<CategoriesResponse>() {
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
        Call<ServicesMinMaxPriceResponse> call = ClientUtils.serviceOfferingService.findMinMaxPrice();
        call.enqueue(new Callback<ServicesMinMaxPriceResponse>() {
            @Override
            public void onResponse(Call<ServicesMinMaxPriceResponse> call, Response<ServicesMinMaxPriceResponse> response) {
                if (response.isSuccessful()) {
                    _sliderMinValue = response.body().minPrice;
                    _sliderMaxValue = response.body().maxPrice;

                    _viewModel.setSelectedMinPrice(_sliderMinValue);
                    _viewModel.setSelectedMaxPrice(_sliderMaxValue);
                    _viewModel.setUpperBoundPrice(_sliderMaxValue);
                    _viewModel.setLowerBoundPrice(_sliderMinValue);
                }
            }

            @Override
            public void onFailure(Call<ServicesMinMaxPriceResponse> call, Throwable t) {

            }
        });
    }

    private void fetchLocations() {
        Call<ServiceLocationsResponse> call = ClientUtils.serviceOfferingService.findServiceLocations();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ServiceLocationsResponse> call, Response<ServiceLocationsResponse> response) {
                if (response.isSuccessful()) {
                    _viewModel.setLocations(response.body().getLocations());
                }
            }

            @Override
            public void onFailure(Call<ServiceLocationsResponse> call, Throwable t) {

            }
        });
    }

    private void openFilterFragment() {
        ServiceFragmentFilter filterFragment = new ServiceFragmentFilter();
        Bundle bundle = new Bundle();
        if (_sliderMinValue == null && _sliderMaxValue == null) {
            return;
        }
        bundle.putDouble("minValue", _sliderMinValue);
        bundle.putDouble("maxValue", _sliderMaxValue);
        filterFragment.setArguments(bundle);
        filterFragment.show(getParentFragmentManager(), filterFragment.getTag());
    }

    private void initializeButtons() {
        _prevButton = this._binding.serviceSearchPreviousButton;
        _nextButton = this._binding.serviceSearchNextButton;

        _prevButton.setOnClickListener(v -> _viewModel.prevPage());
        _nextButton.setOnClickListener(v -> _viewModel.nextPage());
    }


}