package com.example.fusmobilni.fragments.users.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.users.register.RegistrationAdapter;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentUpdateProfileBinding;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.model.users.User;
import com.example.fusmobilni.model.enums.UserType;
import com.example.fusmobilni.viewModels.users.UpdateProfileViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class UpdateProfileFragment extends Fragment {
    private FragmentUpdateProfileBinding _binding;
    private MaterialButton _nextButton;
    private MaterialButton _backButton;
    private ViewPager2 _viewPager;
    private RegistrationAdapter _adapter;
    private LinearLayout _signUpLayout;
    private List<Fragment> _fragments;
    private UpdateProfileViewModel _updateProfileViewModel;

    public UpdateProfileFragment() {
        // Required empty public constructor
    }

    public static UpdateProfileFragment newInstance() {
        return new UpdateProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentUpdateProfileBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();

        _updateProfileViewModel = new ViewModelProvider(requireActivity()).get(UpdateProfileViewModel.class);
        _viewPager = _binding.viewPager;
        _signUpLayout = _binding.signUpLayout;
        User user = CustomSharedPrefs.getInstance().getUser();
        _updateProfileViewModel.setUser(user);

        _viewPager.setUserInputEnabled(false);

        _nextButton = _binding.nextButton;
        _backButton = _binding.backButton;

        // Set up _adapter and fragments
        _fragments = new ArrayList<>();
        _adapter = new RegistrationAdapter(requireActivity(), _fragments);
        _viewPager.setAdapter(_adapter);
        _updateProfileViewModel.fragments.observe(getViewLifecycleOwner(), fragments -> {
            _fragments = fragments;
            _adapter = new RegistrationAdapter(requireActivity(), fragments);
            _viewPager.setAdapter(_adapter);
            _viewPager.setUserInputEnabled(false);

            _viewPager.setCurrentItem(_viewPager.getCurrentItem());
            _signUpLayout.setVisibility(View.VISIBLE);
            if (_viewPager.getCurrentItem() == _adapter.getItemCount() - 1) {
                _nextButton.setText(R.string.finish);
            }

        });

        _nextButton.setOnClickListener(v -> nextButtonClick());

        _backButton.setOnClickListener(v -> {
            if (_viewPager.getCurrentItem() > 0) {
                _viewPager.setCurrentItem(_viewPager.getCurrentItem() - 1);
                _backButton.setVisibility(View.VISIBLE);
            }
            if (_viewPager.getCurrentItem() == 0) {
                _backButton.setVisibility(View.GONE);
            }
            _nextButton.setText(R.string.next);
        });
        onIntroFinished(user.getRole());
        return view;
    }


    private void nextButtonClick() {
        int currentItem = _viewPager.getCurrentItem();
        if (currentItem <= _adapter.getItemCount() - 1) {
            Fragment currentFragment = _fragments.get(currentItem);

            if (((FragmentValidation) currentFragment).validate()) {
                if (currentItem == _adapter.getItemCount() - 1) {
                    _backButton.setVisibility(View.GONE);
                    submitRegistration();
                    return;
                }
                _backButton.setVisibility(View.VISIBLE);
                _viewPager.setCurrentItem(currentItem + 1);
                // if we are on fragment change btn text
                if (_viewPager.getCurrentItem() == _adapter.getItemCount() - 1) {
                    _nextButton.setText(R.string.finish);
                }
            }
        }
    }

    public void onIntroFinished(UserType selectedRole) {
        _updateProfileViewModel.setRole(selectedRole);
    }

    private void submitRegistration() {
        Toast.makeText(getActivity(), "Profile updated successfully!", Toast.LENGTH_LONG).show();
        Navigation.findNavController(_binding.getRoot()).navigate(R.id.action_updateProfileFragment_to_viewProfileFragment);
    }
}