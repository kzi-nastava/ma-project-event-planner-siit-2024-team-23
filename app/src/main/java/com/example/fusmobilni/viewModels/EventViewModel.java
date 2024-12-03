package com.example.fusmobilni.viewModels;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.model.Event;

import java.util.List;

public class EventViewModel extends ViewModel {
    private final MutableLiveData<List<Fragment>> _fragments = new MutableLiveData<>();
    public LiveData<List<Fragment>> fragments = _fragments;
    private MutableLiveData<String> title = new MutableLiveData<>("");
    private MutableLiveData<String> description = new MutableLiveData<>("");
    private MutableLiveData<String> date = new MutableLiveData<>("");
    private MutableLiveData<String> location = new MutableLiveData<>("");
    private MutableLiveData<String> category = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> isUpdating = new MutableLiveData<>(false);

    // Setters for the fields
    public void setTitle(String title) {
        this.title.setValue(title);
    }

    public void setDescription(String description) {
        this.description.setValue(description);
    }

    public void setDate(String date) {
        this.date.setValue(date);
    }

    public void setLocation(String location) {
        this.location.setValue(location);
    }

    public void setCategory(String category) {
        this.category.setValue(category);
    }

    public void setIsUpdating(boolean isUpdating) {
        this.isUpdating.setValue(isUpdating);
    }

    // Getters for the fields
    public MutableLiveData<String> getTitle() {
        return title;
    }

    public MutableLiveData<String> getDescription() {
        return description;
    }

    public MutableLiveData<String> getDate() {
        return date;
    }

    public MutableLiveData<String> getLocation() {
        return location;
    }

    public MutableLiveData<String> getCategory() {
        return category;
    }

    public LiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

    // Method to populate the ViewModel with an Event object
    public void populate(Event event) {
        this.title.setValue(event.getTitle());
        this.description.setValue(event.getDescription());
        this.date.setValue(event.getDate());
        this.location.setValue(event.getLocation());
        this.category.setValue(event.getCategory());
        this.isUpdating.setValue(true);
    }

    // Method to clean up and reset the fields
    public void cleanUp() {
        this.title.setValue("");
        this.description.setValue("");
        this.date.setValue("");
        this.location.setValue("");
        this.category.setValue("");
        this.isUpdating.setValue(false);
    }
}
