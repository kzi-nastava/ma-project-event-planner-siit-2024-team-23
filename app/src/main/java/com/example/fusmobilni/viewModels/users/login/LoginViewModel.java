package com.example.fusmobilni.viewModels.users.login;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.interfaces.ILoginCallback;
import com.example.fusmobilni.model.users.User;
import com.example.fusmobilni.model.enums.UserType;
import com.example.fusmobilni.requests.auth.LoginRequest;
import com.example.fusmobilni.responses.auth.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private final MutableLiveData<String> _email = new MutableLiveData<>();
    private final MutableLiveData<String> _password = new MutableLiveData<>();
    private final MutableLiveData<UserType> _role = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> _loginSuccess = new MutableLiveData<>();

    public MutableLiveData<String> getEmail() {
        return _email;
    }

    public MutableLiveData<String> getPassword() {
        return _password;
    }

    public MutableLiveData<UserType> getRole() {
        return _role;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return _isLoading;
    }

    public MutableLiveData<Boolean> getLoginSuccess() {
        return _loginSuccess;
    }

    public void setEmail(String email) {
        _email.setValue(email);
    }

    public void setPassword(String password) {
        _password.setValue(password);
    }

    public void setRole(UserType role) {
        _role.setValue(role);
    }

    public void setIsLoading(Boolean isLoading) {
        _isLoading.setValue(isLoading);
    }

    public void setLoginSuccess(Boolean loginSuccess) {
        _loginSuccess.setValue(loginSuccess);
    }

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void login(ILoginCallback callback) {
        _isLoading.setValue(true);
        Call<LoginResponse> request = ClientUtils.authService.login(new LoginRequest(_email.getValue(), _password.getValue()));
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    writeUserToPref(response.body());
                    _isLoading.setValue(false);
                    callback.onSuccess(response.body());
                }else{
                    callback.onFailure(new Throwable("Login failed!"));
                    _isLoading.setValue(false);
                }
            }
            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                callback.onFailure(t);
                _isLoading.setValue(false);
            }
        });
    }

    private void writeUserToPref(LoginResponse response) {
        Context appContext = getApplication();
        CustomSharedPrefs prefs = CustomSharedPrefs.getInstance(appContext);
        prefs.saveUser(response);
    }

}
