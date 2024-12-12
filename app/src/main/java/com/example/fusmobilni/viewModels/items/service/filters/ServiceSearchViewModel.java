package com.example.fusmobilni.viewModels.items.service.filters;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.model.items.category.Category;
import com.example.fusmobilni.model.items.service.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceSearchViewModel extends ViewModel {
    private   MutableLiveData<List<Service>> _allServices = new MutableLiveData<>();
    private  MutableLiveData<List<Service>> _filteredServices = new MutableLiveData<>();
    private  MutableLiveData<List<Service>> _pagedServices = new MutableLiveData<>();
    private   MutableLiveData<String> _constraint = new MutableLiveData<>("");
    private  MutableLiveData<String> _location = new MutableLiveData<>("");
    private  MutableLiveData<Category> _category = new MutableLiveData<>(null);
    private  MutableLiveData<Integer> _currentPage = new MutableLiveData<>(0);
    private MutableLiveData<Integer> _pageSize = new MutableLiveData<>(5);

    public void applyFilters() {
        List<Service> filteredList = new ArrayList<>();

        for (Service product : _allServices.getValue()) {
            boolean matchesConstraint = _constraint.getValue().isEmpty() || product.getName().toLowerCase().trim().contains(_constraint.getValue().toLowerCase().trim());
            boolean matchesLocation = _location.getValue().isEmpty() || product.getLocation().toLowerCase().trim().equals(_location.getValue().toLowerCase().trim());
            boolean matchesCategory = _category.getValue() == null || product.getCategory().toLowerCase().trim().equals(_category.getValue().getName().toLowerCase().trim());
            if (matchesConstraint && matchesLocation && matchesCategory) {
                filteredList.add(product);
            }
        }
        _filteredServices.getValue().clear();
        _filteredServices.setValue(filteredList);
        loadPage(0);
    }

    public void loadPage(int page) {
        if (page < 0 || page * _pageSize.getValue() > _filteredServices.getValue().size()) {
            return;
        }


        _currentPage.setValue(page);
        int start = page * _pageSize.getValue();
        int end = start + _pageSize.getValue();
        if (end > _filteredServices.getValue().size()) {
            end = _filteredServices.getValue().size();
        }

        List<Service> pageCategories = _filteredServices.getValue().subList(start, end);

        _pagedServices.setValue(pageCategories);

    }

    public void resetFilters() {
        _category.setValue(null);
        _location.setValue("");
    }

    public void prevPage() {
        if (_currentPage.getValue() > 0) {
            loadPage(_currentPage.getValue() - 1);
        }
    }

    public void nextPage() {
        if ((_currentPage.getValue() + 1) * _pageSize.getValue() < _filteredServices.getValue().size()) {
            loadPage(_currentPage.getValue() + 1);
        }
    }

    public void setPageSize(int selectedItem, String currentText) {
        _pageSize.setValue(selectedItem);
        applyFilters();
    }

    public void setData(List<Service> services) {
        this._allServices.setValue(new ArrayList<>(services));
        this._filteredServices.setValue(new ArrayList<>(services));
        this._pagedServices.setValue(new ArrayList<>(services));
        applyFilters();
    }

    public MutableLiveData<List<Service>> getAllServices() {
        return _allServices;
    }

    public void setAllServices(List<Service> _allServices) {
        this._allServices.setValue(_allServices);
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

    public MutableLiveData<List<Service>> getFilteredServices() {
        return _filteredServices;
    }

    public void setFilteredServices(List<Service> _filteredServices) {
        this._filteredServices.setValue(_filteredServices);
        applyFilters();
    }

    public MutableLiveData<String> getLocation() {
        return _location;
    }

    public void setLocation(String _location) {
        this._location.setValue(_location);
        applyFilters();
    }

    public MutableLiveData<List<Service>> getPagedServices() {
        return _pagedServices;
    }

    public void setPagedServices(List<Service> _pagedServices) {
        this._pagedServices.setValue(_pagedServices);
        applyFilters();
    }

    public MutableLiveData<Integer> getPageSize() {
        return _pageSize;
    }

    public void setPageSize(Integer _pageSize) {
        this._pageSize.setValue(_pageSize);
        applyFilters();
    }
}
