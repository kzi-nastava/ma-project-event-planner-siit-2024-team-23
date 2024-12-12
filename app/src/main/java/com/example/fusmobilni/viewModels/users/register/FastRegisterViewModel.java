package com.example.fusmobilni.viewModels.users.register;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.fragments.users.register.fast.StepOneFastRegistrationFragment;
import com.example.fusmobilni.fragments.users.register.fast.StepTwoFastRegistrationFragment;

import java.util.ArrayList;
import java.util.List;

public class FastRegisterViewModel extends ViewModel {
    private MutableLiveData<List<Fragment>> _fragments = new MutableLiveData<>();
    public LiveData<List<Fragment>> fragments = _fragments;

    private MutableLiveData<String> _name = new MutableLiveData<>();
    private MutableLiveData<String> _lastName = new MutableLiveData<>();
    private MutableLiveData<String> _email = new MutableLiveData<>();
    private MutableLiveData<String> _password = new MutableLiveData<>();
    private MutableLiveData<String> _city = new MutableLiveData<>();
    private MutableLiveData<String> _address = new MutableLiveData<>();
    private MutableLiveData<String> _phone = new MutableLiveData<>();
    private MutableLiveData<byte[]> _profileImage = new MutableLiveData<>();
    private void fillFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new StepOneFastRegistrationFragment());
        fragments.add(new StepTwoFastRegistrationFragment());

        _fragments.setValue(fragments);
    }
    public void setAddress(String _address) {
        this._address.setValue(_address);
    }

    public void setCity(String _city) {
        this._city.setValue(_city);
    }

    public void setEmail(String _email) {
        this._email.setValue(_email);
    }

    public void setFragments(List<Fragment> _fragments) {
        this._fragments.setValue(_fragments);
    }

    public void setLastName(String _lastName) {
        this._lastName.setValue(_lastName);
    }

    public void setName(String _name) {
        this._name.setValue(_name);
    }

    public void setPassword(String _password) {
        this._password.setValue(_password);
    }

    public void setPhone(String _phone) {
        this._phone.setValue(_phone);
    }

    public void setProfileImage(byte[] _profileImage) {
        this._profileImage.setValue(_profileImage);
    }


    public MutableLiveData<String> getAddress() {
        return _address;
    }

    public MutableLiveData<String> getCity() {
        return _city;
    }

    public MutableLiveData<String> getEmail() {
        return _email;
    }

    public MutableLiveData<List<Fragment>> get_fragments() {
        return _fragments;
    }

    public MutableLiveData<String> getLastName() {
        return _lastName;
    }

    public MutableLiveData<String> getName() {
        return _name;
    }

    public MutableLiveData<String> getPassword() {
        return _password;
    }

    public MutableLiveData<String> getPhone() {
        return _phone;
    }

    public MutableLiveData<byte[]> getProfileImage() {
        return _profileImage;
    }


    public LiveData<List<Fragment>> getFragments() {
        return fragments;
    }

    public void setFragments(LiveData<List<Fragment>> fragments) {
        this.fragments = fragments;
    }
}
