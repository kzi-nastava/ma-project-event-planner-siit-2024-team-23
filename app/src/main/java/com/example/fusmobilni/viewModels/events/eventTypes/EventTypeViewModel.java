package com.example.fusmobilni.viewModels.events.eventTypes;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.adapters.events.eventType.EventTypeAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.interfaces.IEventTypeCallback;
import com.example.fusmobilni.model.event.eventTypes.EventType;
import com.example.fusmobilni.model.event.eventTypes.EventTypeStatus;
import com.example.fusmobilni.model.items.category.OfferingsCategory;
import com.example.fusmobilni.requests.eventTypes.EventTypeCreateRequest;
import com.example.fusmobilni.requests.eventTypes.EventTypeUpdateRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventTypeViewModel extends ViewModel {
    private MutableLiveData<Long> id = new MutableLiveData<>(-1L);
    private MutableLiveData<String> name = new MutableLiveData<>("");
    private MutableLiveData<String> description = new MutableLiveData<>("");
    private MutableLiveData<List<OfferingsCategory>> suggestedCategories = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> isUpdating = new MutableLiveData<>(false);
    private MutableLiveData<List<OfferingsCategory>> allCategories = new MutableLiveData<>(new ArrayList<>());
    public void setAllCategories(List<OfferingsCategory> categories){
        allCategories.setValue(categories);
    }
    public LiveData<List<OfferingsCategory>> getAllCategories(){
        return allCategories;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public void setDescription(String description) {
        this.description.setValue(description);
    }
    public void setIsUpdating(boolean isUpdating) {
        this.isUpdating.setValue(isUpdating);
    }

    public MutableLiveData<String> getName() {
        return this.name;
    }

    public MutableLiveData<String> getDescription() {
        return this.description;
    }
    public LiveData<Boolean> getIsUpdating() {return isUpdating; }
    public MutableLiveData<List<OfferingsCategory>> getSuggestedCategories() {return suggestedCategories;}
    public void setSuggestedCategories(List<OfferingsCategory> suggestedCategories) {
        this.suggestedCategories.setValue(suggestedCategories);
    }

    public void populate(EventType eventType) {
        this.id.setValue(eventType.getId());
        this.name.setValue(eventType.getName());
        this.description.setValue(eventType.getDescription());
        this.suggestedCategories.setValue(eventType.getSuggestedCategories().categories);
        this.isUpdating.setValue(true);
    }

    public void cleanUp() {
        this.name.setValue("");
        this.description.setValue("");
        suggestedCategories.setValue(new ArrayList<>());
        this.isUpdating.setValue(false);
    }

    public void submitForm(List<OfferingsCategory> offeringsCategories, IEventTypeCallback callback) {
        ArrayList<Long> categories = Objects.requireNonNull(offeringsCategories).stream()
                .map(OfferingsCategory::getId).collect(Collectors.toCollection(ArrayList::new));
        Call<EventType> request = null;
        if (Boolean.TRUE.equals(isUpdating.getValue())) {
             request = ClientUtils.eventTypeService.updateEventType(id.getValue(),
                    new EventTypeUpdateRequest(name.getValue(), description.getValue(), categories, EventTypeStatus.ACTIVATED));
        }else{
            request = ClientUtils.eventTypeService.createEventType(new EventTypeCreateRequest
                    (name.getValue(), description.getValue(), categories));
        }
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<EventType> call, @NonNull Response<EventType> response) {
                if(response.isSuccessful() && response.body() != null){
                    setIsUpdating(response.body().getActive().equals(EventTypeStatus.ACTIVATED));
                    callback.onSuccess(response.body());
//                    eventTypeAdapter.updateItem(position, response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventType> call, @NonNull Throwable t) {
               callback.onFailure(t);
            }
        });
    }
}
