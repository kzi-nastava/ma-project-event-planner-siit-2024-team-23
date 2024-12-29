package com.example.fusmobilni.viewModels.items.product;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.helper.UriConverter;
import com.example.fusmobilni.model.enums.ReservationConfirmation;
import com.example.fusmobilni.requests.eventTypes.GetEventTypesResponse;
import com.example.fusmobilni.requests.products.CreateProductProposalRequest;
import com.example.fusmobilni.requests.products.CreateProductRequest;
import com.example.fusmobilni.requests.products.GetProductResponse;
import com.example.fusmobilni.requests.products.UpdateProductRequest;
import com.example.fusmobilni.requests.services.GetServiceResponse;
import com.example.fusmobilni.requests.services.ServiceFilterRequest;
import com.example.fusmobilni.requests.services.cardView.GetServiceCardResponse;
import com.example.fusmobilni.requests.services.cardView.GetServicesCardResponse;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.items.products.home.ProductHomeResponse;
import com.example.fusmobilni.responses.items.products.home.ProductsHomeResponse;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductViewModel extends ViewModel {

    private final MutableLiveData<String> category = new MutableLiveData<>("");
    private final MutableLiveData<String> name = new MutableLiveData<>("");
    private final MutableLiveData<String> description = new MutableLiveData<>("");
    private final MutableLiveData<String> eventType = new MutableLiveData<>("");
    private final MutableLiveData<Long> categoryId = new MutableLiveData<>();

    private final MutableLiveData<String> eventTypeIds = new MutableLiveData<>("");
    private final MutableLiveData<Long> eventTypeId = new MutableLiveData<>(null);

    private final MutableLiveData<String> customCategoryName = new MutableLiveData<>("");
    private final MutableLiveData<String> customCategoryDescription = new MutableLiveData<>("");
    private final MutableLiveData<String> specificities = new MutableLiveData<>("");
    private final MutableLiveData<Double> price = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> discount = new MutableLiveData<>(0.0);
    private final MutableLiveData<ArrayList<Uri>> imageUris = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> isVisible = new MutableLiveData<>(true);
    private final MutableLiveData<Boolean> isAvailable = new MutableLiveData<>(true);
    private final MutableLiveData<Boolean> isDeleted = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isUpdating = new MutableLiveData<>(false);
    private final MutableLiveData<Double> lowerBoundaryPrice = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> upperBoundaryPrice = new MutableLiveData<>(8000.0);
    private final MutableLiveData<Boolean> availability = new MutableLiveData<>(true);
    private final MutableLiveData<Boolean> isAvailabilityEnabled = new MutableLiveData<>(false);

    private final MutableLiveData<ReservationConfirmation> reservationConfirmation = new MutableLiveData<>(ReservationConfirmation.MANUAL);

    private final MutableLiveData<List<String>> eventTypes = new MutableLiveData<>(new ArrayList<>());

    private final MutableLiveData<ArrayList<MultipartBody.Part>> sendImages = new MutableLiveData<>();

    private GetEventTypesResponse eventTypeResponses;
    private Long serviceId;
    private final MutableLiveData<List<ProductHomeResponse>> products = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> searchConstraint = new MutableLiveData<>("");


    // Getters for LiveData
    public LiveData<String> getCategory() { return category; }
    public LiveData<String> getName() { return name; }
    public LiveData<String> getDescription() { return description; }
    public LiveData<String> getSpecificities() { return specificities; }
    public LiveData<Double> getPrice() { return price; }
    public LiveData<Double> getDiscount() { return discount; }
    public MutableLiveData<String> getSearchConstraint() {
        return searchConstraint;
    }
    public LiveData<ArrayList<Uri>> getImageUris() { return imageUris; }
    public LiveData<Boolean> getIsVisible() { return isVisible; }
    public LiveData<Boolean> getIsAvailable() { return isAvailable; }
    public LiveData<Boolean> getIsDeleted() { return isDeleted; }
    public LiveData<Boolean> getIsUpdating() {return isUpdating; }
    public LiveData<List<String>> getEventTypes() { return eventTypes; }
    public MutableLiveData<List<ProductHomeResponse>> getProducts() { return products; }

    // Setters for MutableLiveData (can add validation if needed)
    public void setCategory(String value) { category.setValue(value); }
    public void setName(String value) { name.setValue(value); }
    public void setDescription(String value) { description.setValue(value); }
    public void setSpecificities(String value) { specificities.setValue(value); }
    public void setPrice(Double value) { price.setValue(value); }
    public void setDiscount(Double value) { discount.setValue(value); }
    public void setSearchConstraint(String searchConstraint, Context context) {
        this.searchConstraint.setValue(searchConstraint);
        applyFilters(context);
    }
    public MutableLiveData<String> getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType.setValue(eventType);
    }
    public void setEventTypeId(Long eventTypeId) {
        this.eventTypeId.setValue(eventTypeId);
    }
    public void addImageUri(Uri uri) {
        ArrayList<Uri> currentUris = new ArrayList<>(imageUris.getValue());
        currentUris.add(uri);
        imageUris.setValue(currentUris);
    }
    public void setIsVisible(Boolean value) { isVisible.setValue(value); }
    public void setIsAvailable(Boolean value) { isAvailable.setValue(value); }

    public void setEventTypes(List<String> selectedEventTypes) {
        eventTypes.setValue(selectedEventTypes);
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
    public MutableLiveData<Boolean> getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability.setValue(availability);
    }

    public void clearImageUris() {
        this.imageUris.setValue(new ArrayList<>());
    }
    public void setData(List<ProductHomeResponse> products) {
        this.products.setValue(products);
    }


    public void populate(GetProductResponse service, Context context) {
        isUpdating.setValue(true);
        serviceId = service.getId();
        Call<GetEventTypesResponse> eventTypesCall = ClientUtils.eventTypeService.findAll();
        eventTypesCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<GetEventTypesResponse> call, @NonNull Response<GetEventTypesResponse> response) {
                eventTypeResponses = response.body();
                eventTypeIds.setValue(String.join(",", service.getEventTypeIds().stream()
                        .map(String::valueOf)
                        .toArray(String[]::new)));
                eventTypes.setValue(eventTypeResponses.eventTypes.stream().
                        filter(et -> service.getEventTypeIds().contains(et.id)).
                        map(et -> et.name).collect(Collectors.toList()));
                categoryId.setValue(service.getCategoryId());
                name.setValue(service.getName());
                description.setValue(service.getDescription());
                specificities.setValue(service.getSpecificities());
                price.setValue(service.getPrice());
                discount.setValue(service.getDiscount());
                try {
                    imageUris.setValue(convertToUrisFromBase64(context, service.getImages()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                isVisible.setValue(service.isVisible());
                isAvailable.setValue(service.isAvailable());
                isDeleted.setValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<GetEventTypesResponse> call, @NonNull Throwable t) {

            }
        });
    }

    public void applyFilters(Context context) {
        ServiceFilterRequest request = getServiceFilterRequest();
        Long userId = getUserId(context);
        if (userId == null)
            return;

        Call<ProductsHomeResponse> response = ClientUtils.productsService.findAllByServiceProvider(userId, request);
        response.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ProductsHomeResponse> call, @NonNull Response<ProductsHomeResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    products.setValue(response.body().products);
                }
            }
            @Override
            public void onFailure(@NonNull Call<ProductsHomeResponse> call, @NonNull Throwable t) {

            }
        });
    }
    public void resetFilters(Context context) {
        this.category.setValue("");
        this.categoryId.setValue(null);
        this.eventTypeId.setValue(null);
        this.eventType.setValue("");
        this.lowerBoundaryPrice.setValue(0.0);
        this.upperBoundaryPrice.setValue(10000.0);
        this.setIsAvailabilityEnabled(false);
        this.availability.setValue(true);
        applyFilters(context);
    }
    @NonNull
    private ServiceFilterRequest getServiceFilterRequest() {
        ArrayList<Long> eventTypeIds = new ArrayList<>();
        if (eventTypeId.getValue() != null) {
            eventTypeIds.add(eventTypeId.getValue());
        }
        ServiceFilterRequest request = new ServiceFilterRequest(
                searchConstraint.getValue(), lowerBoundaryPrice.getValue(), upperBoundaryPrice.getValue(),
                categoryId.getValue(), eventTypeIds, Boolean.TRUE.equals(isAvailabilityEnabled.getValue()),
                Boolean.TRUE.equals(availability.getValue())
        );
        return request;
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
        for(Uri imageUri: Objects.requireNonNull(imageUris.getValue())) {
            images.add(UriConverter.getImageFromUri(context, imageUri));
        }
        sendImages.setValue(images);
    }


    public void submit(Context context) {
        if(Objects.equals(category.getValue(), "Custom")) {
            createProposal(context);
            return;
        }
        if (isUpdating.getValue()) {
            updateService(context);
        } else {
            createService(context);
        }
    }

    private void updateService(Context context) {
        Gson gson = new Gson();
        Long userId = getUserId(context);
        if (userId == null)
            return;
        UpdateProductRequest request = new UpdateProductRequest(
                name.getValue(), description.getValue(), eventTypeIds.getValue(),
                price.getValue(), discount.getValue(), userId, specificities.getValue(),
                Boolean.TRUE.equals(isVisible.getValue()), Boolean.TRUE.equals(isAvailable.getValue())
        );
        String json = gson.toJson(request);
        RequestBody jsonRequestBody = RequestBody.create(json, MediaType.parse("application/json"));
        try {
            setSendImages(context);
        } catch (IOException e) {
            return;
        }
        for (MultipartBody.Part image : Objects.requireNonNull(sendImages.getValue())) {
            Log.d("tag", image.toString());
        }
        try {
            Call<GetProductResponse> call = ClientUtils.productsService.update(jsonRequestBody, sendImages.getValue(), serviceId);
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<GetProductResponse> call, @NonNull Response<GetProductResponse> response) {
                }

                @Override
                public void onFailure(@NonNull Call<GetProductResponse> call, @NonNull Throwable t) {
                }
            });
        } catch (Exception e) {
            Log.d("tag", "Exception during Retrofit call"+ e.getMessage());
        }
    }

    public void createService(Context context) {
        Gson gson = new Gson();
        Long userId = getUserId(context);
        if (userId == null)
            return;
        CreateProductRequest request = new CreateProductRequest(
                name.getValue(), description.getValue(), categoryId.getValue(), eventTypeIds.getValue(),
                price.getValue(), discount.getValue(), userId, specificities.getValue(),
                Boolean.TRUE.equals(isVisible.getValue()), Boolean.TRUE.equals(isAvailable.getValue())
        );
        String json = gson.toJson(request);
        // Create RequestBody for the JSON
        RequestBody jsonRequestBody = RequestBody.create(json, MediaType.parse("application/json"));

        try {
            setSendImages(context);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Call<GetProductResponse> call = ClientUtils.productsService.create(jsonRequestBody, sendImages.getValue());
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<GetProductResponse> call, @NonNull Response<GetProductResponse> response) {
            }

            @Override
            public void onFailure(@NonNull Call<GetProductResponse> call, @NonNull Throwable t) {
            }
        });
    }

    private Long getUserId(Context context) {
        LoginResponse user = CustomSharedPrefs.getInstance(context).getUser();
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    private void createProposal(Context context) {
        Gson gson = new Gson();
        Long userId = getUserId(context);
        if (userId == null)
            return;
        CreateProductProposalRequest request = new CreateProductProposalRequest(
                customCategoryName.getValue(), customCategoryDescription.getValue(),
                name.getValue(), description.getValue(), eventTypeIds.getValue(),
                price.getValue(), discount.getValue(), userId, specificities.getValue(),
                Boolean.TRUE.equals(isVisible.getValue()), Boolean.TRUE.equals(isAvailable.getValue())
        );
        String json = gson.toJson(request);
        // Create RequestBody for the JSON
        RequestBody jsonRequestBody = RequestBody.create(json, MediaType.parse("application/json"));

        try {
            setSendImages(context);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Call<Void> call = ClientUtils.productsService.createProposal(jsonRequestBody, sendImages.getValue());
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d("Tag", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }
}

