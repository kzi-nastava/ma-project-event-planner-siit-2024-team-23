package com.example.fusmobilni.viewModels;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.fusmobilni.fragments.RegisterFragments.RoleSelectionFragment;
import com.example.fusmobilni.fragments.RegisterFragments.StepOneFragment;
import com.example.fusmobilni.fragments.RegisterFragments.StepThreeFragment;
import com.example.fusmobilni.fragments.RegisterFragments.StepTwoFragment;
import com.example.fusmobilni.fragments.RegisterFragments.VerifyEmailFragment;
import com.example.fusmobilni.model.enums.UserType;
import java.util.ArrayList;
import java.util.List;

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
}
