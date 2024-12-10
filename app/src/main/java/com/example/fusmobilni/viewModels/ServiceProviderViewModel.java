package com.example.fusmobilni.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.model.Category;
import com.example.fusmobilni.model.PrototypeService;
import com.example.fusmobilni.model.Service;
import com.example.fusmobilni.requests.services.GetServiceResponse;
import com.example.fusmobilni.requests.services.GetServicesResponse;
import com.example.fusmobilni.requests.services.ServiceFilterRequest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceProviderViewModel extends ViewModel {

    private final MutableLiveData<List<GetServiceResponse>> services = new MutableLiveData<List<GetServiceResponse>>();
    private final MutableLiveData<String> category = new MutableLiveData<>("");
    private final MutableLiveData<String> eventType = new MutableLiveData<>("");

    private final MutableLiveData<Long> categoryId = new MutableLiveData<>(null);
    private final MutableLiveData<Long> eventTypeId = new MutableLiveData<>(null);
    private final MutableLiveData<Double> lowerBoundaryPrice = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> upperBoundaryPrice = new MutableLiveData<>(8000.0);
    private final MutableLiveData<Boolean> availability = new MutableLiveData<>(true);
    private final MutableLiveData<String> searchConstraint = new MutableLiveData<>("");

    private final MutableLiveData<Boolean> isAvailabilityEnabled = new MutableLiveData<>(false);

    public MutableLiveData<List<GetServiceResponse>> getServices() {
        return services;
    }


    public void applyFilters() {
        ArrayList<Long> eventTypeIds = new ArrayList<>();
        if (eventTypeId.getValue() != null) {
            eventTypeIds.add(eventTypeId.getValue());
        }
        ServiceFilterRequest request = new ServiceFilterRequest(
                searchConstraint.getValue(), lowerBoundaryPrice.getValue(), upperBoundaryPrice.getValue(),
                categoryId.getValue(), eventTypeIds, isAvailabilityEnabled.getValue(), availability.getValue()
        );
        Call<GetServicesResponse> response = ClientUtils.serviceOfferingService.findAllByServiceProvider(1L, request);
        response.enqueue(new Callback<GetServicesResponse>() {
            @Override
            public void onResponse(Call<GetServicesResponse> call, Response<GetServicesResponse> response) {
                services.setValue(response.body().services);
            }

            @Override
            public void onFailure(Call<GetServicesResponse> call, Throwable t) {

            }
        });
    }

    public void resetFilters() {
        this.category.setValue("");
        this.categoryId.setValue(null);
        this.eventTypeId.setValue(null);
        this.eventType.setValue("");
        this.lowerBoundaryPrice.setValue(0.0);
        this.upperBoundaryPrice.setValue(10000.0);
        this.setIsAvailabilityEnabled(false);
        this.availability.setValue(true);
        applyFilters();
    }

    public void setData(List<GetServiceResponse> services) {
        this.services.setValue(services);
    }


    public MutableLiveData<String> getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category.setValue(category);
    }

    public MutableLiveData<String> getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType.setValue(eventType);
    }


    public MutableLiveData<Boolean> getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability.setValue(availability);
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
    }

    public MutableLiveData<Double> getUpperBoundaryPrice() {
        return upperBoundaryPrice;
    }

    public void setUpperBoundaryPrice(Double upperBoundaryPrice) {
        this.upperBoundaryPrice.setValue(upperBoundaryPrice);
    }

    public MutableLiveData<Boolean> getIsAvailabilityEnabled() {
        return isAvailabilityEnabled;
    }

    public void setIsAvailabilityEnabled(Boolean isAvailabilityEnabled) {
        this.isAvailabilityEnabled.setValue(isAvailabilityEnabled);
    }

    public MutableLiveData<Long> getCategoryId() {
        return categoryId;
    }

    public MutableLiveData<Long> getEventTypeId() {
        return eventTypeId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId.setValue(categoryId);;
    }

    public void setEventTypeId(Long eventTypeId) {
        this.eventTypeId.setValue(eventTypeId);
    }

    public void delete(int position) {
        Long id = this.services.getValue().get(position).getId();
        Call<Void> response = ClientUtils.serviceOfferingService.delete(id);
        response.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                List<GetServiceResponse> serviceResponses = services.getValue();
                serviceResponses.remove(position);
                services.setValue(serviceResponses);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


}
