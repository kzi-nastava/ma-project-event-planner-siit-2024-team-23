package com.example.fusmobilni.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.RegistrationAdapter;
import com.example.fusmobilni.databinding.ActivityRegisterBinding;
import com.example.fusmobilni.fragments.RegisterFragments.RoleSelectionFragment;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.model.enums.RegisterUserRole;
import com.example.fusmobilni.viewModels.RegisterViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private ViewPager2 _viewPager;
    private RegistrationAdapter _adapter;
    private LinearLayout _signUpLayout;
    private List<Fragment> _fragments;
    private RegisterViewModel _registerViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterBinding binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        _registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        _viewPager = binding.viewPager;
        _signUpLayout = binding.signUpLayout;

        _viewPager.setUserInputEnabled(false);

        MaterialButton nextButton = binding.nextButton;
        MaterialButton backButton = binding.backButton;

        // Set up _adapter and fragments
        _fragments = new ArrayList<>();
        _fragments.add(new RoleSelectionFragment());
        _adapter = new RegistrationAdapter(this, _fragments);
        _viewPager.setAdapter(_adapter);

        _registerViewModel.fragments.observe(this, fragments -> {
            _fragments = fragments;
            _adapter = new RegistrationAdapter(RegisterActivity.this, fragments);
            _viewPager.setAdapter(_adapter);
            _viewPager.setUserInputEnabled(false);

            _viewPager.setCurrentItem(_viewPager.getCurrentItem()+1);
            _signUpLayout.setVisibility(View.VISIBLE);

        });

        nextButton.setOnClickListener(v -> nextButtonClick());

        backButton.setOnClickListener(v -> {
            if (_viewPager.getCurrentItem() > 0) {
                _viewPager.setCurrentItem(_viewPager.getCurrentItem() - 1);
            }
            if (_viewPager.getCurrentItem() == 0){
                _signUpLayout.setVisibility(View.GONE);
            }
        });

    }

    private void nextButtonClick() {
        if (_viewPager.getCurrentItem() < _adapter.getItemCount() - 1) {

            int currentItem = _viewPager.getCurrentItem();
            Fragment currentFragment = _fragments.get(currentItem);

            if (((FragmentValidation) currentFragment).validate()){
                _viewPager.setCurrentItem(_viewPager.getCurrentItem() + 1);

                // if we are on fragment before success message we remove buttons and submit
                if(_viewPager.getCurrentItem() == _adapter.getItemCount() - 1){
                    _signUpLayout.setVisibility(View.GONE);
                    // TODO: implement request here afterwards
                    submitRegistration();
                }
            }
        }
    }

    public void onIntroFinished(RegisterUserRole selectedRole) {
        _registerViewModel.setRole(selectedRole);
    }

    private void submitRegistration() {
    }


}