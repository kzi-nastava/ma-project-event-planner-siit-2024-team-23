package com.example.fusmobilni.fragments.users.userUpgrade;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fusmobilni.activities.RegisterActivity;
import com.example.fusmobilni.adapters.users.UpgradeAccountAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentSucessDialogBinding;
import com.example.fusmobilni.databinding.FragmentUpgradeUserBinding;
import com.example.fusmobilni.fragments.dialogs.FailiureDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SuccessDialogFragment;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.interfaces.IRegisterCallback;
import com.example.fusmobilni.model.enums.UserType;
import com.example.fusmobilni.responses.register.regular.RegisterResponse;
import com.example.fusmobilni.responses.users.UpgradedUserRoleResponse;
import com.example.fusmobilni.viewModels.users.IUpgradeUserCallBack;
import com.example.fusmobilni.viewModels.users.UpgradeUserViewModel;

import java.util.ArrayList;
import java.util.List;

public class UpgradeUserFragment extends Fragment {

    private FragmentUpgradeUserBinding _binding;
    private UpgradeAccountAdapter fragmentAdapter;
    private ViewPager2 _viewPager;
    private LinearLayout _signUpLayout;

    private UpgradeUserViewModel _viewModel;
    private List<Fragment> _fragments;

    public UpgradeUserFragment() {
        // Required empty public constructor
    }

    public static UpgradeUserFragment newInstance(String param1, String param2) {
        UpgradeUserFragment fragment = new UpgradeUserFragment();
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
        _binding = FragmentUpgradeUserBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();

        _viewModel = new ViewModelProvider(requireActivity()).get(UpgradeUserViewModel.class);
        _signUpLayout = _binding.signUpLayout;
        _viewPager = _binding.viewPager;

        _fragments = new ArrayList<>();
        _fragments.add(new UpgradeUserRoleSelectionFragment());
        fragmentAdapter = new UpgradeAccountAdapter(this, _fragments);
        _viewPager.setAdapter(fragmentAdapter);

        _viewModel.fragments.observe(getViewLifecycleOwner(), fragments -> {
            _fragments = fragments;
            fragmentAdapter = new UpgradeAccountAdapter(UpgradeUserFragment.this, fragments);
            _viewPager.setAdapter(fragmentAdapter);
            _viewPager.setUserInputEnabled(false);

            _viewPager.setCurrentItem(_viewPager.getCurrentItem() + 1);
            _signUpLayout.setVisibility(View.VISIBLE);

        });
        _binding.nextButton.setOnClickListener(v -> nextButtonClick());

        _binding.backButton.setOnClickListener(v -> {
            if (_viewPager.getCurrentItem() > 0) {
                _viewPager.setCurrentItem(_viewPager.getCurrentItem() - 1);
            }
            if (_viewPager.getCurrentItem() == 0) {
                _signUpLayout.setVisibility(View.GONE);
            }
        });
        return root;
    }

    private void nextButtonClick() {
        if (_viewPager.getCurrentItem() < fragmentAdapter.getItemCount() - 1) {

            int currentItem = _viewPager.getCurrentItem();
            Fragment currentFragment = _fragments.get(currentItem);

            if (((FragmentValidation) currentFragment).validate()) {

                _viewPager.setCurrentItem(_viewPager.getCurrentItem() + 1);
            }
        }
    }

    public void onIntroFinished(UserType selectedRole) {
        _viewModel.setRole(selectedRole);
    }
}