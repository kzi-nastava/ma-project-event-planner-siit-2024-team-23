package com.example.fusmobilni.viewModels;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.helper.UriConverter;
import com.example.fusmobilni.model.PrototypeService;
import com.example.fusmobilni.model.enums.ReservationConfirmation;
import com.example.fusmobilni.requests.categories.GetCategoriesResponse;
import com.example.fusmobilni.requests.eventTypes.GetEventTypesResponse;
import com.example.fusmobilni.requests.services.CreateServiceRequest;
import com.example.fusmobilni.requests.services.GetServiceResponse;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceViewModel extends ViewModel {

    private final MutableLiveData<String> category = new MutableLiveData<>();
    private final MutableLiveData<String> name = new MutableLiveData<>("");
    private final MutableLiveData<String> description = new MutableLiveData<>("");

    private final MutableLiveData<Long> categoryId = new MutableLiveData<>();

    private final MutableLiveData<String> eventTypeIds = new MutableLiveData<>("");

    private final MutableLiveData<String> customCategoryName = new MutableLiveData<>("");
    private final MutableLiveData<String> customCategoryDescription = new MutableLiveData<>("");
    private final MutableLiveData<String> specificities = new MutableLiveData<>("");
    private final MutableLiveData<Double> price = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> discount = new MutableLiveData<>(0.0);
    private final MutableLiveData<ArrayList<Uri>> imageUris = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> isVisible = new MutableLiveData<>(true);
    private final MutableLiveData<Boolean> isAvailable = new MutableLiveData<>(true);
    private final MutableLiveData<Integer> duration = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> cancellationDeadline = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> reservationDeadline = new MutableLiveData<>(0);
    private final MutableLiveData<Boolean> isAutomaticReservation = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isDeleted = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isUpdating = new MutableLiveData<>(false);

    private final MutableLiveData<ReservationConfirmation> reservationConfirmation = new MutableLiveData<>(ReservationConfirmation.MANUAL);

    private final MutableLiveData<List<String>> eventTypes = new MutableLiveData<>(new ArrayList<>());

    private final MutableLiveData<ArrayList<MultipartBody.Part>> sendImages = new MutableLiveData<>();

    private GetEventTypesResponse eventTypeResponses;


    // Getters for LiveData
    public LiveData<String> getCategory() { return category; }
    public LiveData<String> getName() { return name; }
    public LiveData<String> getDescription() { return description; }
    public LiveData<String> getSpecificities() { return specificities; }
    public LiveData<Double> getPrice() { return price; }
    public LiveData<Double> getDiscount() { return discount; }
    public LiveData<ArrayList<Uri>> getImageUris() { return imageUris; }
    public LiveData<Boolean> getIsVisible() { return isVisible; }
    public LiveData<Boolean> getIsAvailable() { return isAvailable; }
    public LiveData<Integer> getDuration() { return duration; }
    public LiveData<Integer> getCancellationDeadline() { return cancellationDeadline; }
    public LiveData<Integer> getReservationDeadline() { return reservationDeadline; }
    public LiveData<Boolean> getIsAutomaticReservation() { return isAutomaticReservation; }
    public LiveData<Boolean> getIsDeleted() { return isDeleted; }
    public LiveData<Boolean> getIsUpdating() {return isUpdating; }
    public LiveData<List<String>> getEventTypes() { return eventTypes; }

    // Setters for MutableLiveData (can add validation if needed)
    public void setCategory(String value) { category.setValue(value); }
    public void setName(String value) { name.setValue(value); }
    public void setDescription(String value) { description.setValue(value); }
    public void setSpecificities(String value) { specificities.setValue(value); }
    public void setPrice(Double value) { price.setValue(value); }
    public void setDiscount(Double value) { discount.setValue(value); }
    public void addImageUri(Uri uri) {
        ArrayList<Uri> currentUris = imageUris.getValue();
        currentUris.add(uri);
        imageUris.setValue(currentUris);
    }
    public void setIsVisible(Boolean value) { isVisible.setValue(value); }
    public void setIsAvailable(Boolean value) { isAvailable.setValue(value); }
    public void setDuration(Integer value) { duration.setValue(value); }
    public void setCancellationDeadline(Integer value) { cancellationDeadline.setValue(value); }
    public void setReservationDeadline(Integer value) { reservationDeadline.setValue(value); }
    public void setIsAutomaticReservation(Boolean value) { isAutomaticReservation.setValue(value); }

    public void setEventTypes(List<String> selectedEventTypes) {
        eventTypes.setValue(selectedEventTypes);
    }

    public void clearImageUris() {
        this.imageUris.setValue(new ArrayList<>());
    }


    public void populate(GetServiceResponse service, Context context) {

        Call<GetEventTypesResponse> eventTypesCall = ClientUtils.eventTypeService.findAll();
        eventTypesCall.enqueue(new Callback<GetEventTypesResponse>() {
            @Override
            public void onResponse(Call<GetEventTypesResponse> call, Response<GetEventTypesResponse> response) {
                eventTypeResponses = response.body();
            }

            @Override
            public void onFailure(Call<GetEventTypesResponse> call, Throwable t) {

            }
        });
        eventTypeIds.setValue(String.join(",", service.getEventTypeIds().stream()
                .map(String::valueOf)
                .toArray(String[]::new)));
        this.eventTypes.setValue(this.eventTypeResponses.eventTypes.stream().
                filter(et -> service.getEventTypeIds().contains(et.id)).
                map(et -> et.name).collect(Collectors.toList()));
        this.categoryId.setValue(service.getCategoryId());
        this.name.setValue(service.getName());
        this.description.setValue(service.getDescription());
        this.specificities.setValue(service.getSpecificities());
        this.price.setValue(service.getPrice());
        this.discount.setValue(service.getDiscount());
        try {
            this.imageUris.setValue(convertToUrisFromBase64(context, service.getImages()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.isVisible.setValue(service.isVisible());
        this.isAvailable.setValue(service.isAvailable());
        this.duration.setValue(service.getDuration());
        this.cancellationDeadline.setValue(service.getCancellationDeadline());
        this.reservationDeadline.setValue(service.getReservationDeadline());
        this.isAutomaticReservation.setValue(
                service.getReservationConfirmation() == ReservationConfirmation.AUTOMATIC);
        this.reservationConfirmation.setValue(service.getReservationConfirmation());
        this.isDeleted.setValue(false);
        this.isUpdating.setValue(true);
    }


    public static ArrayList<Uri> convertToUrisFromBase64(Context context, List<String> base64List) throws IOException {
        ArrayList<Uri> uriList = new ArrayList<>();

        for (String base64String : base64List) {
            byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);

            File tempFile = File.createTempFile("image_", ".jpg", context.getCacheDir());
            tempFile.deleteOnExit();

            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(decodedBytes);
            }

            Uri uri = Uri.fromFile(tempFile);
            uriList.add(uri);
        }

        return uriList;
    }


    public MutableLiveData<String> getCustomCategoryName() {
        return customCategoryName;
    }

    public MutableLiveData<String> getCustomCategoryDescription() {
        return customCategoryDescription;
    }

    public void setCustomCategoryName(String value) { customCategoryName.setValue(value); }
    public void setCustomCategoryDescription(String value) { customCategoryDescription.setValue(value); }

    public MutableLiveData<Long> getCategoryId() {
        return categoryId;
    }

    public MutableLiveData<String> getEventTypeIds() {
        return eventTypeIds;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId.setValue(categoryId);
    }

    public void setEventTypeIds(String eventTypeIds) {
        this.eventTypeIds.setValue(eventTypeIds);
    }

    public MutableLiveData<ReservationConfirmation> getReservationConfirmation() {
        return reservationConfirmation;
    }

    public void setReservationConfirmation(ReservationConfirmation reservationConfirmation) {
        this.reservationConfirmation.setValue(reservationConfirmation);
    }

    public MutableLiveData<ArrayList<MultipartBody.Part>> getSendImages() {
        return sendImages;
    }

    public void setSendImages(Context context) throws IOException {
        ArrayList<MultipartBody.Part> images = new ArrayList<>();
        for(Uri imageUri: imageUris.getValue()) {
            images.add(UriConverter.getImageFromUri(context, imageUri));
        }
        sendImages.setValue(images);
    }


    public void submit(Context context) {
        // Serialize the object to JSON
        Gson gson = new Gson();
        CreateServiceRequest request = new CreateServiceRequest(
                name.getValue(), description.getValue(), categoryId.getValue(), eventTypeIds.getValue(),
                price.getValue(), discount.getValue(), 1L, specificities.getValue(),
                isVisible.getValue(), isAvailable.getValue(), duration.getValue(), reservationDeadline.getValue(),
                cancellationDeadline.getValue(), reservationConfirmation.getValue()
        );
        String json = gson.toJson(request);
        Log.d("CreateServiceRequest", "Name: " + request.getName());
        Log.d("CreateServiceRequest", "Description: " + request.getDescription());
        Log.d("CreateServiceRequest", "Category ID: " + request.getCategoryId());
        Log.d("CreateServiceRequest", "Event Type IDs: " + request.getEventTypeIds());
        Log.d("CreateServiceRequest", "Price: " + request.getPrice());
        Log.d("CreateServiceRequest", "Discount: " + request.getDiscount());
        Log.d("CreateServiceRequest", "Service Provider ID: " + request.getServiceProviderId());
        Log.d("CreateServiceRequest", "Specificities: " + request.getSpecificities());
        Log.d("CreateServiceRequest", "Is Visible: " + request.isVisible());
        Log.d("CreateServiceRequest", "Is Available: " + request.isAvailable());
        Log.d("CreateServiceRequest", "Duration: " + request.getDuration());
        Log.d("CreateServiceRequest", "Reservation Deadline: " + request.getReservationDeadline());
        Log.d("CreateServiceRequest", "Cancellation Deadline: " + request.getCancellationDeadline());
        Log.d("CreateServiceRequest", "Reservation Confirmation: " + request.getReservationConfirmation());


        // Create RequestBody for the JSON
        RequestBody jsonRequestBody = RequestBody.create(MediaType.parse("application/json"), json);

        try {
            setSendImages(context);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Call<GetServiceResponse> call = ClientUtils.serviceOfferingService.create(jsonRequestBody, sendImages.getValue());
        call.enqueue(new Callback<GetServiceResponse>() {
            @Override
            public void onResponse(Call<GetServiceResponse> call, Response<GetServiceResponse> response) {
                Log.d("tag", "Service created successfully");
            }

            @Override
            public void onFailure(Call<GetServiceResponse> call, Throwable t) {
                Log.d("tag", "Error: " + t.getMessage());
            }
        });
    }


}

