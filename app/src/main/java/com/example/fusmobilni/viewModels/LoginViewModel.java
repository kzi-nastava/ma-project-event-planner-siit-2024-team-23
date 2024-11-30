package com.example.fusmobilni.viewModels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.model.User;
import com.example.fusmobilni.model.enums.UserType;

public class LoginViewModel extends AndroidViewModel {
    private final MutableLiveData<String> _email = new MutableLiveData<>();
    private final MutableLiveData<String> _password = new MutableLiveData<>();
    private final MutableLiveData<UserType> _role = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> _loginSuccess = new MutableLiveData<>();

    public MutableLiveData<String> getEmail() {return _email;}
    public MutableLiveData<String> getPassword() {return _password;}
    public MutableLiveData<UserType> getRole(){return _role;}
    public MutableLiveData<Boolean> getIsLoading() {return _isLoading;}
    public MutableLiveData<Boolean> getLoginSuccess() {return _loginSuccess;}
    public void setEmail(String email){_email.setValue(email);}
    public void setPassword(String password){_password.setValue(password);}
    public void setRole(UserType role){_role.setValue(role);}
    public void setIsLoading(Boolean isLoading){_isLoading.setValue(isLoading);}
    public void setLoginSuccess(Boolean loginSuccess){_loginSuccess.setValue(loginSuccess);}
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }
    public void login() {
        _isLoading.setValue(true);

        // TODO: Call backend here
                if ("admin@gmail.com".equals(_email.getValue()) && "123".equals(_password.getValue())) {
                    setRole(UserType.ADMIN);
                    writeUserToPref();
                    setLoginSuccess(true);
                } else if ("au@gmail.com".equals(_email.getValue()) && "123".equals(_password.getValue())) {
                    setRole(UserType.AUTHENTICATED_USER);
                    writeUserToPref();
                    setLoginSuccess(true);
                } else if ("eo@gmail.com".equals(_email.getValue()) && "123".equals(_password.getValue())) {
                    setRole(UserType.EVENT_ORGANIZER);
                    writeUserToPref();
                    setLoginSuccess(true);
                } else if ("sp@gmail.com".equals(_email.getValue()) && "123".equals(_password.getValue())) {
                    setRole(UserType.SERVICE_PROVIDER);
                    writeUserToPref();
                    setLoginSuccess(true);
                } else {
                    _loginSuccess.setValue(false);
                }
                _isLoading.setValue(false);
    }

    private void writeUserToPref() {
        Context appContext = getApplication();
        CustomSharedPrefs prefs = new CustomSharedPrefs(appContext);
        // TODO: created dummy user here replace with the real one later!
        User user = new User();
        user.setId(1L);
        user.setFirstName("Milan");
        user.setLastName("Lazarevic");
        user.setEmail(getEmail().getValue());
        user.setJwt("myToken123");
        user.setRole(getRole().getValue());
        user.setAvatar("https://w7.pngwing.com/pngs/340/946/png-transparent-avatar-user-computer-icons-software-developer-avatar-child-face-heroes-thumbnail.png");
        prefs.saveUser(user);
    }

}
