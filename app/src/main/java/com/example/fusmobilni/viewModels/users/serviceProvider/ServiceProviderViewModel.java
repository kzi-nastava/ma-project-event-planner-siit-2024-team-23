package com.example.fusmobilni.viewModels.users.serviceProvider;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.requests.services.GetServiceResponse;
import com.example.fusmobilni.requests.services.GetServicesResponse;
import com.example.fusmobilni.requests.services.ServiceFilterRequest;
import com.example.fusmobilni.requests.services.cardView.GetServiceCardResponse;
import com.example.fusmobilni.requests.services.cardView.GetServicesCardResponse;
import com.example.fusmobilni.responses.items.products.home.ProductHomeResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceProviderViewModel extends ViewModel {

    private final MutableLiveData<List<GetServiceCardResponse>> services = new MutableLiveData<List<GetServiceCardResponse>>();
    private final MutableLiveData<String> category = new MutableLiveData<>("");
    private final MutableLiveData<String> eventType = new MutableLiveData<>("");

    private final MutableLiveData<Long> categoryId = new MutableLiveData<>(null);
    private final MutableLiveData<Long> eventTypeId = new MutableLiveData<>(null);
    private final MutableLiveData<Double> lowerBoundaryPrice = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> upperBoundaryPrice = new MutableLiveData<>(100000.0);
    private final MutableLiveData<Boolean> availability = new MutableLiveData<>(true);
    private final MutableLiveData<String> searchConstraint = new MutableLiveData<>("");

    private final MutableLiveData<Boolean> isAvailabilityEnabled = new MutableLiveData<>(false);

    public MutableLiveData<List<GetServiceCardResponse>> getServices() {
        return services;
    }


    public void applyFilters(Context context) {
        ArrayList<Long> eventTypeIds = new ArrayList<>();
        if (eventTypeId.getValue() != null) {
            eventTypeIds.add(eventTypeId.getValue());
        }
        ServiceFilterRequest request = new ServiceFilterRequest(
                searchConstraint.getValue(), lowerBoundaryPrice.getValue(), upperBoundaryPrice.getValue(),
                categoryId.getValue(), eventTypeIds, isAvailabilityEnabled.getValue(), availability.getValue()
        );

        Long spId = CustomSharedPrefs.getInstance(context).getUser().getId();
        Call<GetServicesCardResponse> response = ClientUtils.serviceOfferingService.findAllByServiceProvider(spId, request);
        response.enqueue(new Callback<GetServicesCardResponse>() {
            @Override
            public void onResponse(Call<GetServicesCardResponse> call, Response<GetServicesCardResponse> response) {
                services.setValue(response.body().services);
            }

            @Override
            public void onFailure(Call<GetServicesCardResponse> call, Throwable t) {

            }
        });
    }

    public void resetFilters(Context context) {
        this.category.setValue("");
        this.categoryId.setValue(null);
        this.eventTypeId.setValue(null);
        this.eventType.setValue("");
        this.lowerBoundaryPrice.setValue(0.0);
        this.upperBoundaryPrice.setValue(100000.0);
        this.setIsAvailabilityEnabled(false);
        this.availability.setValue(true);
        applyFilters(context);
    }

    public ServiceFilterRequest buildCurrentFilter() {
        ArrayList<Long> eventTypeIds = new ArrayList<>();
        if (eventTypeId.getValue() != null) {
            eventTypeIds.add(eventTypeId.getValue());
        }

        return new ServiceFilterRequest(
                searchConstraint.getValue(),
                lowerBoundaryPrice.getValue() != null ? lowerBoundaryPrice.getValue() : 0.0,
                upperBoundaryPrice.getValue() != null ? upperBoundaryPrice.getValue() : 100000.0,
                categoryId.getValue(),
                eventTypeIds,
                isAvailabilityEnabled.getValue() != null ? isAvailabilityEnabled.getValue() : false,
                availability.getValue() != null ? availability.getValue() : true
        );
    }



    public void setData(List<GetServiceCardResponse> services) {
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

    public void setSearchConstraint(String searchConstraint, Context context) {
        this.searchConstraint.setValue(searchConstraint);
        applyFilters(context);
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
}
