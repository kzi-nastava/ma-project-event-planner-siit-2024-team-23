package com.example.fusmobilni.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.model.EventType;
import com.example.fusmobilni.model.OfferingsCategory;

import java.util.ArrayList;
import java.util.List;

public class EventTypeViewModel extends ViewModel {
    private MutableLiveData<String> name = new MutableLiveData<>("");
    private MutableLiveData<String> description = new MutableLiveData<>("");
    private MutableLiveData<List<OfferingsCategory>> suggestedCategories = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> isUpdating = new MutableLiveData<>(false);

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
        this.name.setValue(eventType.getName());
        this.description.setValue(eventType.getDescription());
        this.suggestedCategories.setValue(eventType.getSuggestedCategories());
        this.isUpdating.setValue(true);
    }

    public void cleanUp() {
        this.name.setValue("");
        this.description.setValue("");
        suggestedCategories.setValue(new ArrayList<>());
        this.isUpdating.setValue(false);
    }
}
