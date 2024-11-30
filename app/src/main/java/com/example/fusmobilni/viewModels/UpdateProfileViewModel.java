package com.example.fusmobilni.viewModels;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.fragments.UserProfileFragments.StepOneUpdateFragment;
import com.example.fusmobilni.fragments.UserProfileFragments.StepTwoUpdateFragment;
import com.example.fusmobilni.model.User;
import com.example.fusmobilni.model.enums.UserType;

import java.util.ArrayList;
import java.util.List;

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
    private final  MutableLiveData<String> _phone = new MutableLiveData<>();
    private final  MutableLiveData<byte[]> _profileImage = new MutableLiveData<>();
    private final MutableLiveData<String> _companyName = new MutableLiveData<>();
    private final MutableLiveData<String> _companyDescriptionName = new MutableLiveData<>();


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
    public void setUser(User user) {
        if (user != null) {
            _name.setValue(user.getFirstName());
            _lastName.setValue(user.getLastName());
            _email.setValue(user.getEmail());
            _role.setValue(user.getRole());
            // TODO update this when you link with backend
            _city.setValue("Novi Sad");
            _address.setValue("Jovana J. Zmaja");
            _phone.setValue("065/088-290");
            if (user.getRole() == UserType.SERVICE_PROVIDER){
               _companyName.setValue("MaxMare");
               _companyDescriptionName.setValue("He had three simple rules by which he lived. " +
                       "The first was to never eat blue food. There was nothing in nature that " +
                       "was edible that was blue. People often asked about blueberries, but " +
                       "everyone knows those are actually purple. He understood it was one of the stranger" +
                       " rules to live by, but it had served him well thus far in the 50+ years of his life.");
            }
        }
    }

}
