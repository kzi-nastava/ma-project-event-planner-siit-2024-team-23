package com.example.fusmobilni.viewModels.items.product.filters;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.responses.items.CategoryResponse;
import com.example.fusmobilni.responses.items.products.filter.ProductPaginationResponse;
import com.example.fusmobilni.responses.items.products.filter.ProductsPaginationResponse;
import com.example.fusmobilni.responses.items.services.filter.ServicesPaginationResponse;
import com.example.fusmobilni.responses.location.LocationResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductSearchViewModel extends ViewModel {
    private MutableLiveData<List<ProductPaginationResponse>> _products = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<String> _constraint = new MutableLiveData<>("");
    private MutableLiveData<LocationResponse> _location = new MutableLiveData<>(null);
    private MutableLiveData<List<LocationResponse>> _locations = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<List<CategoryResponse>> _categories = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<CategoryResponse> _category = new MutableLiveData<>(null);
    private MutableLiveData<Double> _minSelectedPrice = new MutableLiveData<>(Double.MIN_VALUE);
    private MutableLiveData<Double> _maxSelectedPrice = new MutableLiveData<>(Double.MAX_VALUE);
    private MutableLiveData<Integer> _currentPage = new MutableLiveData<>(0);
    private MutableLiveData<Integer> _pageSize = new MutableLiveData<>(5);

    private MutableLiveData<Long> _totalItems = new MutableLiveData<>(0L);
    private MutableLiveData<Integer> _totalPages = new MutableLiveData<>(0);
    private MutableLiveData<Double> _upperBoundPrice = new MutableLiveData<>(Double.MAX_VALUE);
    private MutableLiveData<Double> _lowerBoundPrice = new MutableLiveData<>(Double.MAX_VALUE);

    public void doFilter() {
        Map<String, String> queryParams = new HashMap<>();
        if (!_constraint.getValue().isEmpty())
            queryParams.put("constraint", _constraint.getValue());
        if (_category.getValue() != null)
            queryParams.put("categoryId", String.valueOf(_category.getValue().getId()));

        if (_location.getValue() != null)
            queryParams.put("city", _location.getValue().getCity());
        queryParams.put("minPrice", String.valueOf(_minSelectedPrice.getValue()));
        queryParams.put("maxPrice", String.valueOf(_maxSelectedPrice.getValue()));
        Call<ProductsPaginationResponse> call = ClientUtils.productsService.findFilteredAndPaginated(
                _currentPage.getValue(),
                _pageSize.getValue(),
                queryParams
        );
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ProductsPaginationResponse> call, Response<ProductsPaginationResponse> response) {
                if (response.isSuccessful()) {
                    _products.setValue(response.body().content);
                }
            }

            @Override
            public void onFailure(Call<ProductsPaginationResponse> call, Throwable t) {

            }
        });
    }

    public void resetFilters() {
        _category.setValue(null);
        _constraint.setValue("");
        _currentPage.setValue(0);
        _location.setValue(null);
        _pageSize.setValue(5);
        _minSelectedPrice.setValue(_upperBoundPrice.getValue());
        _maxSelectedPrice.setValue(_lowerBoundPrice.getValue());

        Call<ProductsPaginationResponse> call = ClientUtils.productsService.findFilteredAndPaginated(_currentPage.getValue(), _pageSize.getValue());
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ProductsPaginationResponse> call, Response<ProductsPaginationResponse> response) {
                if (response.isSuccessful()) {
                    _products.setValue(response.body().content);
                }
            }

            @Override
            public void onFailure(Call<ProductsPaginationResponse> call, Throwable t) {

            }
        });
    }

    public void prevPage() {
        if (_currentPage.getValue() > 0) {
            setCurrentPage(_currentPage.getValue() - 1);
            doFilter();
        }
    }

    public void nextPage() {
        if ((_currentPage.getValue() + 1) * _pageSize.getValue() < _totalItems.getValue()) {
            setCurrentPage(_currentPage.getValue() + 1);
            doFilter();
        }
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

    public MutableLiveData<Long> getTotalItems() {
        return _totalItems;
    }

    public void setTotalItems(Long _totalItems) {
        this._totalItems.setValue(_totalItems);
        ;
    }

    public MutableLiveData<Integer> getTotalPages() {
        return _totalPages;
    }

    public void setTotalPages(Integer _totalPages) {
        this._totalPages.setValue(_totalPages);
    }

    public MutableLiveData<List<ProductPaginationResponse>> getProducts() {
        return _products;
    }

    public void setProducts(List<ProductPaginationResponse> _allProducts) {
        this._products.setValue(_allProducts);
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

    public MutableLiveData<Double> getMaxSelectedPrice() {
        return _maxSelectedPrice;
    }

    public void setMaxSelectedPrice(Double _maxSelectedPrice) {
        this._maxSelectedPrice.setValue(_maxSelectedPrice);
    }

    public MutableLiveData<Double> getMinSelectedPrice() {
        return _minSelectedPrice;
    }

    public void setMinSelectedPrice(Double _minSelectedPrice) {
        this._minSelectedPrice.setValue(_minSelectedPrice);
    }


    public MutableLiveData<Integer> getPageSize() {
        return _pageSize;
    }

    public void setPageSize(Integer _pageSize) {
        this._pageSize.setValue(_pageSize);
        doFilter();
    }


}
