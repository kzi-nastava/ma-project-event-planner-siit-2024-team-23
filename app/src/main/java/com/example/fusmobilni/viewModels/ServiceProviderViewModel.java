package com.example.fusmobilni.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.model.Category;
import com.example.fusmobilni.model.PrototypeService;
import com.example.fusmobilni.model.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceProviderViewModel extends ViewModel {

    private MutableLiveData<List<PrototypeService>> allServices = new MutableLiveData<List<PrototypeService>>();
    private MutableLiveData<List<PrototypeService>> filteredServices = new MutableLiveData<>();
    private MutableLiveData<String> category = new MutableLiveData<>("");
    private MutableLiveData<String> eventType = new MutableLiveData<>("");
    private MutableLiveData<Double> lowerBoundaryPrice = new MutableLiveData<>(0.0);
    private MutableLiveData<Double> upperBoundaryPrice = new MutableLiveData<>(8000.0);
    private MutableLiveData<Boolean> availability = new MutableLiveData<>(true);
    private MutableLiveData<String> searchConstraint = new MutableLiveData<>("");


    public void applyFilters() {
        List<PrototypeService> filteredList = new ArrayList<>();

        for (PrototypeService service : allServices.getValue()) {
            boolean matchesConstraint = service.getName().toLowerCase().trim().contains(searchConstraint.getValue().toLowerCase().trim())
                    || searchConstraint.getValue().isEmpty();
            boolean matchesCategory = service.getCategory().equalsIgnoreCase(category.getValue())
                    || category.getValue().isEmpty();
            boolean matchesEventType = service.getEventTypes().contains(eventType.getValue())
                    || eventType.getValue().isEmpty();
            boolean matchesPrice = service.getPrice() >= lowerBoundaryPrice.getValue()
                    && service.getPrice() <= upperBoundaryPrice.getValue();
            boolean matchesAvailability = service.isAvailable() == availability.getValue();

            if(matchesConstraint && matchesCategory && matchesEventType && matchesPrice && matchesAvailability)
                filteredList.add(service);
        }
        filteredServices.getValue().clear();
        filteredServices.setValue(filteredList);
    }

    public void resetFilters() {
        this.category.setValue("");
        this.eventType.setValue("");
        this.lowerBoundaryPrice.setValue(0.0);
        this.upperBoundaryPrice.setValue(8000.0);
        this.availability.setValue(true);
    }

    public void setData(List<PrototypeService> services) {
        this.allServices.setValue(new ArrayList<>(services));
        this.filteredServices.setValue(new ArrayList<>(services));
    }

    public MutableLiveData<List<PrototypeService>> getAllServices() {
        return allServices;
    }

    public void setAllServices(List<PrototypeService> _allServices) {
        this.allServices.setValue(_allServices);
        applyFilters();
    }




    public MutableLiveData<List<PrototypeService>> getFilteredServices() {
        return filteredServices;
    }

    public void setFilteredServices(List<PrototypeService> filteredServices) {
        this.filteredServices.setValue(filteredServices);
        applyFilters();
  }


    public MutableLiveData<String> getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category.setValue(category);
        applyFilters();
    }

    public MutableLiveData<String> getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType.setValue(eventType);
        applyFilters();
    }


    public MutableLiveData<Boolean> getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability.setValue(availability);
        applyFilters();
    }

    public MutableLiveData<String> getSearchConstraint() {
        return searchConstraint;
    }

    public void setSearchConstraint(String searchConstraint) {
        this.searchConstraint.setValue(searchConstraint);
        applyFilters();
    }

    public MutableLiveData<Double> getLowerBoundaryPrice() {
        return lowerBoundaryPrice;
    }

    public void setLowerBoundaryPrice(Double lowerBoundaryPrice) {
        this.lowerBoundaryPrice.setValue(lowerBoundaryPrice);
        applyFilters();
    }

    public MutableLiveData<Double> getUpperBoundaryPrice() {
        return upperBoundaryPrice;
    }

    public void setUpperBoundaryPrice(Double upperBoundaryPrice) {
        this.upperBoundaryPrice.setValue(upperBoundaryPrice);
        applyFilters();
    }
}
