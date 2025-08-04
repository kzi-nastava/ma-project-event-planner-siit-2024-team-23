package com.example.fusmobilni.viewModels.users;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.clients.GeocodingClient;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.fragments.users.profile.StepOneUpdateFragment;
import com.example.fusmobilni.fragments.users.profile.StepTwoUpdateFragment;
import com.example.fusmobilni.helper.UriConverter;
import com.example.fusmobilni.interfaces.ILocationCallback;
import com.example.fusmobilni.interfaces.IRegisterCallback;
import com.example.fusmobilni.interfaces.IUpdateProfileCallback;
import com.example.fusmobilni.model.users.User;
import com.example.fusmobilni.model.enums.UserType;
import com.example.fusmobilni.requests.register.regular.RegisterRequest;
import com.example.fusmobilni.requests.users.AuthenticatedUserUpdateRequest;
import com.example.fusmobilni.requests.users.ServiceProviderUpdateRequest;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.geoCoding.GeoCodingResponse;
import com.example.fusmobilni.responses.register.regular.RegisterResponse;
import com.example.fusmobilni.responses.users.UserInfoResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileViewModel extends ViewModel {

    private final MutableLiveData<List<Fragment>> _fragments = new MutableLiveData<>();
    public LiveData<List<Fragment>> fragments = _fragments;
    private final MutableLiveData<UserType> _role = new MutableLiveData<>();

    private final  MutableLiveData<String> _name = new MutableLiveData<>();
    private final  MutableLiveData<String> _lastName = new MutableLiveData<>();
    private final  MutableLiveData<String> _email = new MutableLiveData<>();
    private final  MutableLiveData<String> _newPassword = new MutableLiveData<>();
    private final  MutableLiveData<String> _city = new MutableLiveData<>();
    private final  MutableLiveData<String> _address = new MutableLiveData<>();
    private final MutableLiveData<String> _streetNumber = new MutableLiveData<>();
    private final  MutableLiveData<String> _phone = new MutableLiveData<>();
    private final  MutableLiveData<byte[]> _profileImage = new MutableLiveData<>();
    private final MutableLiveData<String> _companyName = new MutableLiveData<>();
    private final MutableLiveData<String> _companyDescriptionName = new MutableLiveData<>();
    private final MutableLiveData<Uri> imageUri = new MutableLiveData<>();
    private final MutableLiveData<String> longitude = new MutableLiveData<>();
    private final MutableLiveData<String> latitude = new MutableLiveData<>();
    private final MutableLiveData<MultipartBody.Part> sendImage = new MutableLiveData<>();
    public void setStreetNumber(String number){
        this._streetNumber.setValue(number);
    }
    public LiveData<String> getStreetNumber(){
        return this._streetNumber;
    }
    public void setImageUri(Uri uri){
        imageUri.setValue(uri);
    }
    public Uri getImageUri(){
        return imageUri.getValue();
    }

    public void setRole(UserType role){
        _role.setValue(role);
        fillFragments(role);
    }

    private void fillFragments( UserType role) {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new StepOneUpdateFragment());
        if (role.equals(UserType.SERVICE_PROVIDER)) {
            fragments.add(new StepTwoUpdateFragment());
        }
        _fragments.setValue(fragments);
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
        _newPassword.setValue(password);
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

    public LiveData<String> getName() {
        return _name;
    }

    public LiveData<String> getLastName() {
        return _lastName;
    }

    public LiveData<String> getEmail() {
        return _email;
    }

    public LiveData<String> getPassword() {
        return _newPassword;
    }

    public LiveData<String> getCity() {
        return _city;
    }

    public LiveData<String> getAddress() {
        return _address;
    }

    public LiveData<String> getPhone() {
        return _phone;
    }

    public LiveData<byte[]> getProfileImage() {
        return _profileImage;
    }

    public LiveData<String> getCompanyDescriptionName() {
        return _companyDescriptionName;
    }

    public LiveData<String> getCompanyName() {
        return _companyName;
    }
    public void setUser(UserInfoResponse user) {
        if (user != null) {
            _name.setValue(user.getFirstName());
            _lastName.setValue(user.getSurname());
            _email.setValue(user.getEmail());
            _role.setValue(user.getUserRole());
            _city.setValue(user.getCity());
            _address.setValue(user.getAddress());
            _streetNumber.setValue(user.getStreetNumber());
            _phone.setValue(user.getPhoneNumber());
            byte[] imageBytes = Base64.decode(user.getAvatar(), Base64.DEFAULT);
            _profileImage.setValue(imageBytes);
            if (user.getUserRole() == UserType.SERVICE_PROVIDER){
               _companyName.setValue(user.getCompanyName());
               _companyDescriptionName.setValue(user.getCompanyDescription());
            }
        }
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

    public void update(Context context, IUpdateProfileCallback callback){
        findLocation(new ILocationCallback() {
            @Override
            public void onLocationSuccess(String lat, String lon) {
                latitude.setValue(lat);
                longitude.setValue(lon);

                AuthenticatedUserUpdateRequest requestBody = buildUpdateRequest();
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

                executeUpdateRequest(requestBody, callback);
            }

            @Override
            public void onLocationFailure(Throwable t) {
                callback.onFailure(new RuntimeException("Location fetch failed: " + t.getMessage()));
            }
        });
    }
    private AuthenticatedUserUpdateRequest buildUpdateRequest() {
        if (latitude.getValue() == null || longitude.getValue() == null) {
            return null;
        }
        if(Objects.equals(_role.getValue(), UserType.SERVICE_PROVIDER)){
            return new ServiceProviderUpdateRequest(
                    _name.getValue(), _lastName.getValue(), _city.getValue(), _address.getValue()
                    , _streetNumber.getValue(), _phone.getValue(), _companyName.getValue(), _companyDescriptionName.getValue()
                    , latitude.getValue(), longitude.getValue()
            );
        }
        return new AuthenticatedUserUpdateRequest(
                _name.getValue(), _lastName.getValue(), _city.getValue(), _address.getValue(), _phone.getValue()
                , _streetNumber.getValue(), latitude.getValue(), longitude.getValue()
        );
    }

    private void executeUpdateRequest(AuthenticatedUserUpdateRequest requestBody, IUpdateProfileCallback callback) {
        Gson gson = new Gson();
        CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();
        String json = "";
        if(requestBody instanceof ServiceProviderUpdateRequest){
            json = gson.toJson(requestBody);
        }else{
            json = gson.toJson(requestBody);
        }
        RequestBody jsonRequestBody = RequestBody.create(json, MediaType.parse("application/json"));
        Call<UserInfoResponse> request = null;
        if(Objects.equals(_role.getValue(), UserType.SERVICE_PROVIDER)){
            request = ClientUtils.userService.updateServiceProvider(jsonRequestBody, sendImage.getValue(), prefs.getUser().getId());
        }else{
            request = ClientUtils.userService.updateAuthenticatedUser(jsonRequestBody, sendImage.getValue(), prefs.getUser().getId());
        }

        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<UserInfoResponse> call, @NonNull Response<UserInfoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();
                    LoginResponse user = prefs.getUser();
                    user.setFirstName(response.body().getFirstName());
                    user.setSurname(response.body().getSurname());
                    prefs.saveUser(user);
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new RuntimeException("Registration failed. Response was unsuccessful."));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfoResponse> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void setSendImage(Context context) throws IOException {
        if(imageUri.getValue() == null) return;
        sendImage.setValue(UriConverter.getImageFromUri(context, imageUri.getValue()));
    }
}
