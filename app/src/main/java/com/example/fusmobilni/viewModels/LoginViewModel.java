package com.example.fusmobilni.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<String> _email = new MutableLiveData<>();
    private final MutableLiveData<String> _password = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> _loginSuccess = new MutableLiveData<>();

    public MutableLiveData<String> getEmail() {return _email;}
    public MutableLiveData<String> getPassword() {return _password;}
    public MutableLiveData<Boolean> getIsLoading() {return _isLoading;}
    public MutableLiveData<Boolean> getLoginSuccess() {return _loginSuccess;}

    public void setEmail(String email){_email.setValue(email);}
    public void setPassword(String password){_password.setValue(password);}
    public void setIsLoading(Boolean isLoading){_isLoading.setValue(isLoading);}
    public void setLoginSuccess(Boolean loginSuccess){_loginSuccess.setValue(loginSuccess);}

    public void login() {
        _isLoading.setValue(true);

        // TODO: Call backend here
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                boolean success = "user@example.com".equals(_email.getValue()) && "password".equals(_password.getValue());
                _loginSuccess.postValue(success);
            } catch (InterruptedException e) {
                _loginSuccess.postValue(false);
            } finally {
                _isLoading.postValue(false);
            }
        }).start();
    }

}
