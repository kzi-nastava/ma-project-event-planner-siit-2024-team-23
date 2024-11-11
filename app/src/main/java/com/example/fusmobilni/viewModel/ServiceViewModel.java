package com.example.fusmobilni.viewModel;

import android.net.Uri;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ServiceViewModel extends ViewModel {

    private final MutableLiveData<String> category = new MutableLiveData<>();
    private final MutableLiveData<String> name = new MutableLiveData<>();
    private final MutableLiveData<String> description = new MutableLiveData<>();
    private final MutableLiveData<String> specificities = new MutableLiveData<>();
    private final MutableLiveData<Double> price = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> discount = new MutableLiveData<>(0.0);
    private final MutableLiveData<ArrayList<Uri>> imageUris = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> eventType = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isVisible = new MutableLiveData<>(true);
    private final MutableLiveData<Boolean> isAvailable = new MutableLiveData<>(true);
    private final MutableLiveData<Double> duration = new MutableLiveData<>(0.0);
    private final MutableLiveData<Integer> cancellationDeadline = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> reservationDeadline = new MutableLiveData<>(0);
    private final MutableLiveData<Boolean> isAutomaticReservation = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isDeleted = new MutableLiveData<>(false);

    // Getters for LiveData
    public LiveData<String> getCategory() { return category; }
    public LiveData<String> getName() { return name; }
    public LiveData<String> getDescription() { return description; }
    public LiveData<String> getSpecificities() { return specificities; }
    public LiveData<Double> getPrice() { return price; }
    public LiveData<Double> getDiscount() { return discount; }
    public LiveData<ArrayList<Uri>> getImageUris() { return imageUris; }
    public LiveData<String> getEventType() { return eventType; }
    public LiveData<Boolean> getIsVisible() { return isVisible; }
    public LiveData<Boolean> getIsAvailable() { return isAvailable; }
    public LiveData<Double> getDuration() { return duration; }
    public LiveData<Integer> getCancellationDeadline() { return cancellationDeadline; }
    public LiveData<Integer> getReservationDeadline() { return reservationDeadline; }
    public LiveData<Boolean> getIsAutomaticReservation() { return isAutomaticReservation; }
    public LiveData<Boolean> getIsDeleted() { return isDeleted; }

    // Setters for MutableLiveData (can add validation if needed)
    public void setCategory(String value) { category.setValue(value); }
    public void setName(String value) { name.setValue(value); }
    public void setDescription(String value) { description.setValue(value); }
    public void setSpecificities(String value) { specificities.setValue(value); }
    public void setPrice(Double value) { price.setValue(value); }
    public void setDiscount(Double value) { discount.setValue(value); }
    public void setEventType(String value) { eventType.setValue(value); }
    public void addImageUri(Uri uri) {
        ArrayList<Uri> currentUris = imageUris.getValue();
        currentUris.add(uri);
        imageUris.setValue(currentUris);
    }
    public void setIsVisible(Boolean value) { isVisible.setValue(value); }
    public void setIsAvailable(Boolean value) { isAvailable.setValue(value); }
    public void setDuration(Double value) { duration.setValue(value); }
    public void setCancellationDeadline(Integer value) { cancellationDeadline.setValue(value); }
    public void setReservationDeadline(Integer value) { reservationDeadline.setValue(value); }
    public void setIsAutomaticReservation(Boolean value) { isAutomaticReservation.setValue(value); }

    public void clearImageUris() {
        this.imageUris.setValue(new ArrayList<>());
    }
}

