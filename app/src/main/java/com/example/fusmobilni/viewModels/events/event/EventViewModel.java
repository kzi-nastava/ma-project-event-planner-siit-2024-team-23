package com.example.fusmobilni.viewModels.events.event;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.clients.GeocodingClient;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.interfaces.CreateEventListener;
import com.example.fusmobilni.model.event.Event;
import com.example.fusmobilni.model.event.eventTypes.EventType;
import com.example.fusmobilni.requests.events.event.CreateEventRequest;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.events.GetEventResponse;
import com.example.fusmobilni.responses.geoCoding.GeoCodingResponse;
import com.example.fusmobilni.responses.location.LocationResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventViewModel extends ViewModel {
    private final MutableLiveData<List<Fragment>> _fragments = new MutableLiveData<>();
    public LiveData<List<Fragment>> fragments = _fragments;
    private final MutableLiveData<String> title = new MutableLiveData<>("");
    private MutableLiveData<String> city = new MutableLiveData<>("");
    private MutableLiveData<String> streetAddress = new MutableLiveData<>("");
    private MutableLiveData<String> streetNumber = new MutableLiveData<>("");
    private final MutableLiveData<String> description = new MutableLiveData<>("");
    private final MutableLiveData<String> date = new MutableLiveData<>("");
    private final MutableLiveData<LocalDate> localDate = new MutableLiveData<>();
    private final MutableLiveData<String> location = new MutableLiveData<>("");
    private final MutableLiveData<String> category = new MutableLiveData<>("");
    private final MutableLiveData<EventType> eventType = new MutableLiveData<>(null);

    private final MutableLiveData<String> time = new MutableLiveData<>();
    private final MutableLiveData<LocalTime> localTime = new MutableLiveData<>();

    private final MutableLiveData<Integer> maxParticipants = new MutableLiveData<>(0);

    private final MutableLiveData<Boolean> isPublic = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isUpdating = new MutableLiveData<>(false);

    public Long eventId = null;



    public void submit(CreateEventListener listener){
        CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();
        LoginResponse user = prefs.getUser();
        Call<List<GeoCodingResponse>> request = GeocodingClient.geoCodingService.getGeoCode("pk.4a4083e362875d1ad824d7d1d981b2eb", transformAddress(), "json");
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<GeoCodingResponse>> call, @NonNull Response<List<GeoCodingResponse>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    listener.onFailure("Couldn't find your location!");
                    return;
                }
                Double lat = Double.valueOf(response.body().get(0).lat);
                Double lon = Double.valueOf(response.body().get(0).lon);
                CreateEventRequest req = new CreateEventRequest(title.getValue(), description.getValue(),
                        maxParticipants.getValue(), Boolean.TRUE.equals(isPublic.getValue()), date.getValue(),
                        time.getValue(), user.getId(), Objects.requireNonNull(eventType.getValue()).getId(),
                        new LocationResponse(getCity().getValue(), lat, lon, getStreetAddress().getValue(),
                                getStreetNumber().getValue()));
                createEvent(req, listener);
            }

            @Override
            public void onFailure(@NonNull Call<List<GeoCodingResponse>> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
    private String transformAddress() {
        String city = getCity().getValue();
        String street = getStreetAddress().getValue();
        String streetNumber =getStreetNumber().getValue();
        return streetNumber + ", " + street + ", " + city;
    }
    public void createEvent(CreateEventRequest req, CreateEventListener listener){
        if(eventId == null){
            Call<GetEventResponse> request = ClientUtils.eventsService.create(req);
            request.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<GetEventResponse> call, @NonNull Response<GetEventResponse> response) {
                    if (response.isSuccessful() && response.body() != null){
                        eventId = response.body().getId();
                        listener.onSuccess("Event created successfully!");
                    }else{
                        listener.onFailure("Unknown error!");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GetEventResponse> call, @NonNull Throwable t) {
                    listener.onFailure(t.getMessage());
                }
            });
        }
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

    public MutableLiveData<String> getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city.setValue(city);
    }

    public MutableLiveData<String> getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress.setValue(streetAddress);
    }

    public MutableLiveData<String> getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber.setValue(streetNumber);
    }
}
