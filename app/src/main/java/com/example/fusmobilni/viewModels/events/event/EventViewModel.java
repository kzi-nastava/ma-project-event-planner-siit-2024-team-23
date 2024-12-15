package com.example.fusmobilni.viewModels.events.event;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.model.event.Event;
import com.example.fusmobilni.model.event.eventTypes.EventType;
import com.example.fusmobilni.requests.events.event.CreateEventRequest;
import com.example.fusmobilni.responses.events.GetEventResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventViewModel extends ViewModel {
    private final MutableLiveData<List<Fragment>> _fragments = new MutableLiveData<>();
    public LiveData<List<Fragment>> fragments = _fragments;
    private MutableLiveData<String> title = new MutableLiveData<>("");
    private MutableLiveData<String> description = new MutableLiveData<>("");
    private MutableLiveData<String> date = new MutableLiveData<>("");
    private MutableLiveData<LocalDate> localDate = new MutableLiveData<>();
    private MutableLiveData<String> location = new MutableLiveData<>("");
    private MutableLiveData<String> category = new MutableLiveData<>("");
    private MutableLiveData<EventType> eventType = new MutableLiveData<>(null);

    private MutableLiveData<String> time = new MutableLiveData<>();
    private MutableLiveData<LocalTime> localTime = new MutableLiveData<>();

    private MutableLiveData<Integer> maxParticipants = new MutableLiveData<Integer>();

    private MutableLiveData<Boolean> isPublic = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isUpdating = new MutableLiveData<>(false);

    public Long eventId = null;


    public void submit(){
        CreateEventRequest req = new CreateEventRequest(title.getValue(), description.getValue(), maxParticipants.getValue(), isPublic.getValue(), date.getValue(),
                time.getValue(), 1L, eventType.getValue().getId());
        Call<GetEventResponse> request = ClientUtils.eventsService.create(req);
        request.enqueue(new Callback<GetEventResponse>() {
            @Override
            public void onResponse(Call<GetEventResponse> call, Response<GetEventResponse> response) {
                eventId = response.body().getId();
            }

            @Override
            public void onFailure(Call<GetEventResponse> call, Throwable t) {
            }
        });
    }

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

    public LiveData<EventType> getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType.setValue(eventType);
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

    public MutableLiveData<String> getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time.setValue(time);
    }

    public MutableLiveData<Integer> getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants.setValue(maxParticipants);
    }

    public MutableLiveData<Boolean> getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic.setValue(isPublic);
    }
}
