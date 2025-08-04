package com.example.fusmobilni.viewModels.users;

import com.example.fusmobilni.responses.users.UpgradedUserRoleResponse;

public interface IUpgradeUserCallBack {
    void onSuccess(UpgradedUserRoleResponse response);

    void onFailure();
}
