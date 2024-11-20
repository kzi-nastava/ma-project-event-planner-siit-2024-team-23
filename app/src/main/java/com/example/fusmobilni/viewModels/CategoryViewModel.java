package com.example.fusmobilni.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.fragments.OfferingsPage;
import com.example.fusmobilni.model.OfferingsCategory;

public class CategoryViewModel extends ViewModel {

    private MutableLiveData<String> name = new MutableLiveData<>("");
    private MutableLiveData<String> description = new MutableLiveData<>("");
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

    public void populate(OfferingsCategory category) {
        this.name.setValue(category.getName());
        this.description.setValue(category.getDescription());
        this.isUpdating.setValue(true);
    }

    public void cleanUp() {
        this.name.setValue("");
        this.description.setValue("");
        this.isUpdating.setValue(false);
    }
}
