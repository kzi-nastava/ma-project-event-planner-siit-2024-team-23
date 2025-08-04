package com.example.fusmobilni.viewModels.items.service.filters;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.responses.items.CategoryResponse;
import com.example.fusmobilni.responses.items.services.filter.ServicePaginationResponse;
import com.example.fusmobilni.responses.items.services.filter.ServicesPaginationResponse;
import com.example.fusmobilni.responses.location.LocationResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceSearchViewModel extends ViewModel {
    private MutableLiveData<List<ServicePaginationResponse>> _services = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<String> _constraint = new MutableLiveData<>("");
    private MutableLiveData<LocationResponse> _location = new MutableLiveData<>(null);
    private MutableLiveData<CategoryResponse> _category = new MutableLiveData<>(null);
    private MutableLiveData<Integer> _currentPage = new MutableLiveData<>(0);
    private MutableLiveData<Integer> _pageSize = new MutableLiveData<>(5);
    private MutableLiveData<Long> _totalItems = new MutableLiveData<>(0L);
    private MutableLiveData<Integer> _totalPages = new MutableLiveData<>(0);
    private MutableLiveData<List<LocationResponse>> _locations = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<List<CategoryResponse>> _categories = new MutableLiveData<>(new ArrayList<>());

    private MutableLiveData<Double> _selectedMaxPrice = new MutableLiveData<>(Double.MAX_VALUE);
    private MutableLiveData<Double> _selectedMinPrice = new MutableLiveData<>(Double.MIN_VALUE);
    private MutableLiveData<Double> _upperBoundPrice = new MutableLiveData<>(Double.MAX_VALUE);
    private MutableLiveData<Double> _lowerBoundPrice = new MutableLiveData<>(Double.MAX_VALUE);

    public void resetPage() {
        _currentPage.setValue(0);
    }

    public void doFilter() {
        Map<String, String> queryParams = new HashMap<>();
        CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();
        if (prefs != null && prefs.getUser() != null) {
            queryParams.put("userId", String.valueOf(prefs.getUser().getId()));
        }
        if (!_constraint.getValue().isEmpty())
            queryParams.put("constraint", _constraint.getValue());
        if (_category.getValue() != null)
            queryParams.put("categoryId", String.valueOf(_category.getValue().getId()));

        if (_location.getValue() != null)
            queryParams.put("city", _location.getValue().getCity());
        queryParams.put("minPrice", String.valueOf(_selectedMinPrice.getValue()));
        queryParams.put("maxPrice", String.valueOf(_selectedMaxPrice.getValue()));
        Call<ServicesPaginationResponse> call = ClientUtils.serviceOfferingService.findFilteredAndPaginated(
                _currentPage.getValue(),
                _pageSize.getValue(),
                queryParams
        );
        call.enqueue(new Callback<ServicesPaginationResponse>() {
            @Override
            public void onResponse(Call<ServicesPaginationResponse> call, Response<ServicesPaginationResponse> response) {
                if (response.isSuccessful()) {
                    _services.setValue(response.body().content);
                }
            }

            @Override
            public void onFailure(Call<ServicesPaginationResponse> call, Throwable t) {

            }
        });
    }

    public void prevPage() {
        if (_currentPage.getValue() > 0) {
            _currentPage.setValue(_currentPage.getValue() - 1);
            doFilter();
        }
    }

    public void nextPage() {
        if ((_currentPage.getValue() + 1) * _pageSize.getValue() < _totalItems.getValue()) {
            _currentPage.setValue(_currentPage.getValue() + 1);
            doFilter();
        }
    }

    public void resetFilters() {
        _category.setValue(null);
        _constraint.setValue("");
        _currentPage.setValue(0);
        _location.setValue(null);
        _pageSize.setValue(5);
        _selectedMaxPrice.setValue(_upperBoundPrice.getValue());
        _selectedMinPrice.setValue(_lowerBoundPrice.getValue());

        Call<ServicesPaginationResponse> call = ClientUtils.serviceOfferingService.findFilteredAndPaginated(_currentPage.getValue(), _pageSize.getValue());
        call.enqueue(new Callback<ServicesPaginationResponse>() {
            @Override
            public void onResponse(Call<ServicesPaginationResponse> call, Response<ServicesPaginationResponse> response) {
                if (response.isSuccessful()) {
                    _services.setValue(response.body().content);
                }
            }

            @Override
            public void onFailure(Call<ServicesPaginationResponse> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<Double> getUpperBoundPrice() {
        return _upperBoundPrice;
    }

    public void setUpperBoundPrice(Double _upperBoundPrice) {
        this._upperBoundPrice.setValue(_upperBoundPrice);
    }

    public MutableLiveData<Double> getLowerBoundPrice() {
        return _lowerBoundPrice;
    }

    public void setLowerBoundPrice(Double _lowerBoundPrice) {
        this._lowerBoundPrice.setValue(_lowerBoundPrice);
    }

    public MutableLiveData<Double> getSelectedMaxPrice() {
        return _selectedMaxPrice;
    }

    public void setSelectedMaxPrice(Double selectedMaxPrice) {
        this._selectedMaxPrice.setValue(selectedMaxPrice);
    }

    public MutableLiveData<Double> getSelectedMinPrice() {
        return _selectedMinPrice;
    }

    public void setSelectedMinPrice(Double selectedMinPrice) {
        this._selectedMinPrice.setValue(selectedMinPrice);
    }

    public MutableLiveData<List<CategoryResponse>> getCategories() {
        return _categories;
    }

    public void setCategories(List<CategoryResponse> _categories) {
        this._categories.setValue(_categories);
    }

    public MutableLiveData<CategoryResponse> getCategory() {
        return _category;
    }

    public void setCategory(CategoryResponse _category) {
        this._category.setValue(_category);
    }

    public MutableLiveData<String> getConstraint() {
        return _constraint;
    }

    public void setConstraint(String _constraint) {
        this._constraint.setValue(_constraint);
        doFilter();
    }

    public MutableLiveData<Integer> getCurrentPage() {
        return _currentPage;
    }

    public void setCurrentPage(Integer _currentPage) {
        this._currentPage.setValue(_currentPage);
    }


    public MutableLiveData<LocationResponse> getLocation() {
        return _location;
    }

    public void setLocation(LocationResponse _location) {
        this._location.setValue(_location);
    }

    public MutableLiveData<List<LocationResponse>> getLocations() {
        return _locations;
    }

    public void setLocations(List<LocationResponse> _locations) {
        this._locations.setValue(_locations);
    }

    public MutableLiveData<Integer> getPageSize() {
        return _pageSize;
    }

    public void setPageSize(Integer _pageSize) {
        this._pageSize.setValue(_pageSize);
        this._currentPage.setValue(0);
        doFilter();
    }

    public MutableLiveData<List<ServicePaginationResponse>> getServices() {
        return _services;
    }

    public void setServices(List<ServicePaginationResponse> _services) {
        this._services.setValue(_services);
    }

    public MutableLiveData<Long> getTotalItems() {
        return _totalItems;
    }

    public void setTotalItems(Long _totalItems) {
        this._totalItems.setValue(_totalItems);
    }

    public MutableLiveData<Integer> getTotalPages() {
        return _totalPages;
    }

    public void setTotalPages(Integer _totalPages) {
        this._totalPages.setValue(_totalPages);
    }


}
