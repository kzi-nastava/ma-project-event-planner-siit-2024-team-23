package com.example.fusmobilni.viewModels.items.product.filters;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.model.items.category.Category;
import com.example.fusmobilni.model.items.product.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductSearchViewModel extends ViewModel {
    private MutableLiveData<List<Product>> _allProducts = new MutableLiveData<>();
    private MutableLiveData<List<Product>> _filteredProducts = new MutableLiveData<>();
    private MutableLiveData<List<Product>> _pagedProducts = new MutableLiveData<>();
    private MutableLiveData<String> _constraint = new MutableLiveData<>("");
    private MutableLiveData<String> _location = new MutableLiveData<>("");
    private MutableLiveData<Category> _category = new MutableLiveData<>(null);
    private MutableLiveData<Double> _minSelectedPrice = new MutableLiveData<>(Double.MIN_VALUE);
    private MutableLiveData<Double> _maxSelectedPrice = new MutableLiveData<>(Double.MAX_VALUE);
    private MutableLiveData<Integer> _currentPage = new MutableLiveData<>(0);
    private MutableLiveData<Integer> _pageSize = new MutableLiveData<>(5);

    public void applyFilters() {
        List<Product> filteredList = new ArrayList<>();

        for (Product product : _allProducts.getValue()) {
            boolean matchesConstraint = _constraint.getValue().isEmpty() || product.getName().toLowerCase().trim().contains(_constraint.getValue().toLowerCase().trim());
            boolean matchesLocation = _location.getValue().isEmpty() || product.getLocation().toLowerCase().trim().equals(_location.getValue().toLowerCase().trim());
            boolean matchesPrice = product.getPrice() >= _minSelectedPrice.getValue() && product.getPrice() <= _maxSelectedPrice.getValue();
            boolean matchesCategory = _category.getValue() == null || product.getCategory().toLowerCase().trim().equals(_category.getValue().getName().toLowerCase().trim());
            if (matchesConstraint && matchesLocation && matchesPrice && matchesCategory) {
                filteredList.add(product);
            }
        }
        _filteredProducts.getValue().clear();
        _filteredProducts.setValue(filteredList);
        loadPage(0);
    }

    public void loadPage(int page) {
        if (page < 0 || page * _pageSize.getValue() > _filteredProducts.getValue().size()) {
            return;
        }


        _currentPage.setValue(page);
        int start = page * _pageSize.getValue();
        int end = start + _pageSize.getValue();
        if (end > _filteredProducts.getValue().size()) {
            end = _filteredProducts.getValue().size();
        }

        List<Product> pageCategories = _filteredProducts.getValue().subList(start, end);

        _pagedProducts.setValue(pageCategories);

    }

    public void resetFilters() {
        _category.setValue(null);
        _location.setValue("");
        _minSelectedPrice.setValue(Double.MIN_VALUE);
        _minSelectedPrice.setValue(Double.MAX_VALUE);
        applyFilters();
    }

    public void prevPage() {
        if (_currentPage.getValue() > 0) {
            loadPage(_currentPage.getValue() - 1);
        }
    }

    public void nextPage() {
        if ((_currentPage.getValue() + 1) * _pageSize.getValue() < _filteredProducts.getValue().size()) {
            loadPage(_currentPage.getValue() + 1);
        }
    }

    public void setPageSize(int selectedItem) {
        _pageSize.setValue(selectedItem);
        applyFilters();
    }

    public void setData(List<Product> products) {
        this._allProducts.setValue(new ArrayList<>(products));
        this._filteredProducts.setValue(new ArrayList<>(products));
        this._pagedProducts.setValue(new ArrayList<>(products));
        applyFilters();
    }

    public MutableLiveData<List<Product>> getAllProducts() {
        return _allProducts;
    }

    public void setAllProducts(List<Product> _allProducts) {
        this._allProducts.setValue(_allProducts);

        applyFilters();
    }

    public MutableLiveData<Category> getCategory() {
        return _category;
    }

    public void setCategory(Category _category) {
        this._category.setValue(_category);
        applyFilters();
    }

    public MutableLiveData<String> getConstraint() {
        return _constraint;
    }

    public void setConstraint(String _constraint) {
        this._constraint.setValue(_constraint);
        applyFilters();
    }

    public MutableLiveData<Integer> getCurrentPage() {
        return _currentPage;
    }

    public void setCurrentPage(Integer _currentPage) {
        this._currentPage.setValue(_currentPage);
        applyFilters();
    }

    public MutableLiveData<List<Product>> getFilteredProducts() {
        return _filteredProducts;
    }

    public void setFilteredProducts(List<Product> _filteredProducts) {
        this._filteredProducts.setValue(_filteredProducts);
        applyFilters();
    }

    public MutableLiveData<String> getLocation() {
        return _location;
    }

    public void setLocation(String _location) {
        this._location.setValue(_location);
        applyFilters();
    }

    public MutableLiveData<Double> getMaxSelectedPrice() {
        return _maxSelectedPrice;
    }

    public void setMaxSelectedPrice(Double _maxSelectedPrice) {
        this._maxSelectedPrice.setValue(_maxSelectedPrice);
        applyFilters();
    }

    public MutableLiveData<Double> getMinSelectedPrice() {
        return _minSelectedPrice;
    }

    public void setMinSelectedPrice(Double _minSelectedPrice) {
        this._minSelectedPrice.setValue(_minSelectedPrice);
        applyFilters();
    }

    public MutableLiveData<List<Product>> getPagedProducts() {
        return _pagedProducts;
    }

    public void setPagedProducts(List<Product> _pagedProducts) {
        this._pagedProducts.setValue(_pagedProducts);
    }

    public MutableLiveData<Integer> getPageSize() {
        return _pageSize;
    }

    public void setPageSize(Integer _pageSize) {
        this._pageSize.setValue(_pageSize);
        applyFilters();
    }
}
