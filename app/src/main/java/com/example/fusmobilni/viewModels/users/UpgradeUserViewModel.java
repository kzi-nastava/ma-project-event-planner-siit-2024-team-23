package com.example.fusmobilni.viewModels.users;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.activities.RegisterActivity;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.fragments.users.userUpgrade.UpgradeUserConfirmUpgradeFragment;
import com.example.fusmobilni.fragments.users.userUpgrade.UpgradeUserRoleSelectionFragment;
import com.example.fusmobilni.fragments.users.userUpgrade.UpgradeUserServiceProviderFragment;
import com.example.fusmobilni.interfaces.IRegisterCallback;
import com.example.fusmobilni.model.enums.UserType;
import com.example.fusmobilni.requests.users.UpgradeUserRequest;
import com.example.fusmobilni.responses.users.UpgradedUserRoleResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpgradeUserViewModel extends ViewModel {
    private MutableLiveData<List<Fragment>> _fragments = new MutableLiveData<>();
    public LiveData<List<Fragment>> fragments = _fragments;

    private MutableLiveData<UserType> _role = new MutableLiveData<>();


    private MutableLiveData<String> _companyName = new MutableLiveData<>();
    private MutableLiveData<String> _companyDescriptionName = new MutableLiveData<>();

    public void setRole(UserType role) {
        _role.setValue(role);
        fillFragments(role);
    }

    private void fillFragments(UserType role) {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new UpgradeUserRoleSelectionFragment());
        if (role.equals(UserType.SERVICE_PROVIDER)) {
            fragments.add(new UpgradeUserServiceProviderFragment());
        }
        fragments.add(new UpgradeUserConfirmUpgradeFragment());
        _fragments.setValue(fragments);
    }

    public MutableLiveData<String> getCompanyDescriptionName() {
        return _companyDescriptionName;
    }

    public MutableLiveData<String> getCompanyName() {
        return _companyName;
    }

    public MutableLiveData<List<Fragment>> get_fragments() {
        return _fragments;
    }


    public MutableLiveData<UserType> getRole() {
        return _role;
    }


    public LiveData<List<Fragment>> getFragments() {
        return fragments;
    }

    public void setCompanyName(String _companyName) {
        this._companyName.setValue(_companyName);
    }

    public void setCompanyDescriptionName(String _companyDescriptionName) {
        this._companyDescriptionName.setValue(_companyDescriptionName);
    }

    public void upgradeUser(Long userId, IUpgradeUserCallBack callBack) {

        UpgradeUserRequest request = new UpgradeUserRequest(
                _companyName.getValue(),
                _companyDescriptionName.getValue(),
                userId,
                _role.getValue()
        );

        Call<UpgradedUserRoleResponse> call = ClientUtils.userService.upgradeUser(request);
        call.enqueue(new Callback<UpgradedUserRoleResponse>() {
            @Override
            public void onResponse(Call<UpgradedUserRoleResponse> call, Response<UpgradedUserRoleResponse> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                    return;
                }
                callBack.onFailure();

            }

            @Override
            public void onFailure(Call<UpgradedUserRoleResponse> call, Throwable t) {
                callBack.onFailure();
            }
        });
    }
}
