package com.example.fusmobilni.viewModels.users.register;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.clients.GeocodingClient;
import com.example.fusmobilni.fragments.users.register.regular.RoleSelectionFragment;
import com.example.fusmobilni.fragments.users.register.regular.StepOneFragment;
import com.example.fusmobilni.fragments.users.register.regular.StepThreeFragment;
import com.example.fusmobilni.fragments.users.register.regular.StepTwoFragment;
import com.example.fusmobilni.fragments.users.register.regular.VerifyEmailFragment;
import com.example.fusmobilni.helper.UriConverter;
import com.example.fusmobilni.interfaces.ILocationCallback;
import com.example.fusmobilni.interfaces.IRegisterCallback;
import com.example.fusmobilni.model.enums.UserType;
import com.example.fusmobilni.requests.register.regular.RegisterRequest;
import com.example.fusmobilni.responses.geoCoding.GeoCodingResponse;
import com.example.fusmobilni.responses.register.regular.RegisterResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterViewModel extends ViewModel {

    private final MutableLiveData<List<Fragment>> _fragments = new MutableLiveData<>();
    public LiveData<List<Fragment>> fragments = _fragments;
    private final MutableLiveData<UserType> _role = new MutableLiveData<>();
    private final  MutableLiveData<String> _name = new MutableLiveData<>();
    private final  MutableLiveData<String> _lastName = new MutableLiveData<>();
    private final  MutableLiveData<String> _email = new MutableLiveData<>();
    private final  MutableLiveData<String> _password = new MutableLiveData<>();
    private final  MutableLiveData<String> _city = new MutableLiveData<>();
    private final  MutableLiveData<String> _address = new MutableLiveData<>();
    private final  MutableLiveData<String> _streetNumber = new MutableLiveData<>();
    private final  MutableLiveData<String> _phone = new MutableLiveData<>();
    private final  MutableLiveData<byte[]> _profileImage = new MutableLiveData<>();
    private final MutableLiveData<String> _companyName = new MutableLiveData<>();
    private final MutableLiveData<String> _companyDescriptionName = new MutableLiveData<>();
    private final MutableLiveData<MultipartBody.Part> sendImage = new MutableLiveData<>();
    private final MutableLiveData<Uri> imageUri = new MutableLiveData<>();
    private final MutableLiveData<String> longitude = new MutableLiveData<>();
    private final MutableLiveData<String> latitude = new MutableLiveData<>();


    public void setRole(UserType role){
        _role.setValue(role);
        fillFragments(role);
    }

    private void fillFragments( UserType role) {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new RoleSelectionFragment());
        fragments.add(new StepOneFragment());
        fragments.add(new StepTwoFragment());

        if (role.equals(UserType.EVENT_ORGANIZER)) {
            fragments.add(new VerifyEmailFragment());
        } else {
            fragments.add(new StepThreeFragment());
            fragments.add(new VerifyEmailFragment());
        }
        _fragments.setValue(fragments);
    }

    public MutableLiveData<Uri> getImageUri() {
        return imageUri;
    }
    public void setImageUri(Uri uri){
        imageUri.setValue(uri);
    }

    public void setName(String name) {
        _name.setValue(name);
    }

    public void setLastName(String lastName) {
        _lastName.setValue(lastName);
    }

    public void setEmail(String email) {
        _email.setValue(email);
    }

    public void setPassword(String password) {
        _password.setValue(password);
    }

    public void setCity(String city) {
        _city.setValue(city);
    }

    public void setAddress(String address) {
        _address.setValue(address);
    }

    public void setPhone(String phone) {
        _phone.setValue(phone);
    }

    public void setProfileImage(byte[] profileImage) {
        _profileImage.setValue(profileImage);
    }
    public void setCompanyName(String companyName) {
        _companyName.setValue(companyName);
    }
    public void setCompanyDescription(String companyDescription) {
        _companyDescriptionName.setValue(companyDescription);
    }
    public void setStreetNumber(String streetNumber){
        _streetNumber.setValue(streetNumber);
    }
    public MutableLiveData<String> getStreetNumber() {
        return _streetNumber;
    }

    public MutableLiveData<String> getName() {
        return _name;
    }

    public MutableLiveData<String> getLastName() {
        return _lastName;
    }

    public MutableLiveData<String> getEmail() {
        return _email;
    }

    public MutableLiveData<String> getPassword() {
        return _password;
    }

    public MutableLiveData<String> getCity() {
        return _city;
    }

    public MutableLiveData<String> getAddress() {
        return _address;
    }

    public MutableLiveData<String> getPhone() {
        return _phone;
    }

    public MutableLiveData<byte[]> getProfileImage() {
        return _profileImage;
    }

    public MutableLiveData<String> getCompanyDescriptionName() {
        return _companyDescriptionName;
    }

    public MutableLiveData<String> getCompanyName() {
        return _companyName;
    }
    private String transformAddress() {
        String city = _city.getValue();
        String street = _address.getValue();
        String streetNumber = _streetNumber.getValue();
        return streetNumber + ", " + street + ", " + city;
    }
    private void findLocation(ILocationCallback iLocationCallback) {
        Call<List<GeoCodingResponse>> call = GeocodingClient.geoCodingService.getGeoCode("pk.4a4083e362875d1ad824d7d1d981b2eb", transformAddress(), "json");
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<GeoCodingResponse>> call, @NonNull Response<List<GeoCodingResponse>> response) {
                if (!response.isSuccessful() || response.body() == null || response.body().isEmpty()) {
                    iLocationCallback.onLocationFailure(new RuntimeException("Failed to fetch location data."));
                    return;
                }
                iLocationCallback.onLocationSuccess(response.body().get(0).lat, response.body().get(0).lon);
            }
            @Override
            public void onFailure(@NonNull Call<List<GeoCodingResponse>> call, @NonNull Throwable t) {
                iLocationCallback.onLocationFailure(t);
            }
        });
    }

    public void createUser(Context context, IRegisterCallback callback){
        findLocation(new ILocationCallback() {
            @Override
            public void onLocationSuccess(String lat, String lon) {
                latitude.setValue(lat);
                longitude.setValue(lon);

                RegisterRequest requestBody = buildRegisterRequest();
                if (requestBody == null) {
                    callback.onFailure(new RuntimeException("Invalid user data or location!"));
                    return;
                }

                try {
                    setSendImage(context);
                } catch (IOException e) {
                    callback.onFailure(new RuntimeException("Failed to process image: " + e.getMessage()));
                    return;
                }

                executeRegistrationRequest(requestBody, callback);
            }

            @Override
            public void onLocationFailure(Throwable t) {
                callback.onFailure(new RuntimeException("Location fetch failed: " + t.getMessage()));
            }
        });
    }
    private RegisterRequest buildRegisterRequest() {
        if (latitude.getValue() == null || longitude.getValue() == null) {
            return null;
        }
        return new RegisterRequest(
                _name.getValue(), _lastName.getValue(), _email.getValue(), _password.getValue(),
                _phone.getValue(), _city.getValue(), _address.getValue(), _streetNumber.getValue(),
                latitude.getValue(), longitude.getValue(),
                _role.getValue(), _companyName.getValue(), _companyDescriptionName.getValue()
        );
    }
    private void executeRegistrationRequest(RegisterRequest requestBody, IRegisterCallback callback) {
        Gson gson = new Gson();
        String json = gson.toJson(requestBody);
        RequestBody jsonRequestBody = RequestBody.create(json, MediaType.parse("application/json"));

        Call<RegisterResponse> request = ClientUtils.authService.register(jsonRequestBody, sendImage.getValue());
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new RuntimeException("Registration failed. Response was unsuccessful."));
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegisterResponse> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }
    public void setSendImage(Context context) throws IOException {
        if(imageUri.getValue() == null) return;
        sendImage.setValue(UriConverter.getImageFromUri(context, imageUri.getValue()));
    }
}
