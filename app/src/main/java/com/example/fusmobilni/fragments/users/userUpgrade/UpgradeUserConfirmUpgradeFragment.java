package com.example.fusmobilni.fragments.users.userUpgrade;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.activities.SplashScreenActivity;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentUpgradeUserConfirmUpgradeBinding;
import com.example.fusmobilni.fragments.dialogs.FailiureDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SuccessDialogFragment;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.users.UpgradedUserRoleResponse;
import com.example.fusmobilni.viewModels.users.IUpgradeUserCallBack;
import com.example.fusmobilni.viewModels.users.UpgradeUserViewModel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpgradeUserConfirmUpgradeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpgradeUserConfirmUpgradeFragment extends Fragment {


    private FragmentUpgradeUserConfirmUpgradeBinding _binding;


    private SpinnerDialogFragment _loader;
    private SuccessDialogFragment _success;
    private FailiureDialogFragment _failiure;
    private UpgradeUserViewModel _viewModel;

    public UpgradeUserConfirmUpgradeFragment() {
        // Required empty public constructor
    }

    public static UpgradeUserConfirmUpgradeFragment newInstance(String param1, String param2) {
        UpgradeUserConfirmUpgradeFragment fragment = new UpgradeUserConfirmUpgradeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _binding = FragmentUpgradeUserConfirmUpgradeBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        initializeDialogs();
        _viewModel = new ViewModelProvider(requireActivity()).get(UpgradeUserViewModel.class);

        _binding.buttonUpgradeUser.setOnClickListener(v -> {
            submitUpgrade();
        });
        return root;
    }

    private void initializeDialogs() {
        _loader = new SpinnerDialogFragment();
        _loader.setCancelable(false);
        _success = new SuccessDialogFragment();
        _success.setCancelable(false);
        _failiure = new FailiureDialogFragment();
    }

    void openSuccessWindow() {
        if (_loader != null) {
            _loader.dismiss();
        }
        Bundle args = new Bundle();
        args.putString("Title", "Success");
        args.putString("Message", "Account uptaded to " + _viewModel.getRole().getValue().toString());
        _success.setArguments(args);
        _success.show(getParentFragmentManager(), "success_dialog");
    }

    void openFailiureWindow() {
        if (_loader != null) {
            _loader.dismiss();
        }
        Bundle args = new Bundle();
        args.putString("Title", "Failiure");
        args.putString("Message", "Failed to update account to " + _viewModel.getRole().getValue().toString());
        _failiure.setArguments(args);
        _failiure.show(getParentFragmentManager(), "failiure_dialog");
    }

    private void submitUpgrade() {
        CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();
        if (prefs == null || prefs.getUser() == null) {
            return;
        }
        _loader.show(getFragmentManager(), "loading_spinner");
        _viewModel.upgradeUser(prefs.getUser().getId(), new IUpgradeUserCallBack() {
            @Override
            public void onSuccess(UpgradedUserRoleResponse response) {
                openSuccessWindow();
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    LoginResponse user = prefs.getUser();
                    user.setRole(response.getRole());
                    prefs.saveUserSynchronously(user);
                    relaunch();
                }, 3000);
            }

            @Override
            public void onFailure() {
                openFailiureWindow();
            }
        });
    }

    void relaunch() {
        Intent intent = new Intent(getContext(), SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        Runtime.getRuntime().exit(0);
    }
}