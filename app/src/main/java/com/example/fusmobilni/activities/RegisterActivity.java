package com.example.fusmobilni.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.RegistrationAdapter;
import com.example.fusmobilni.databinding.ActivityHomeBinding;
import com.example.fusmobilni.databinding.ActivityRegisterBinding;
import com.example.fusmobilni.fragments.RegisterFragments.RoleSelectionFragment;
import com.example.fusmobilni.fragments.RegisterFragments.StepOneFragment;
import com.example.fusmobilni.fragments.RegisterFragments.StepThreeFragment;
import com.example.fusmobilni.fragments.RegisterFragments.StepTwoFragment;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.model.enums.RegisterUserRole;
import com.example.fusmobilni.viewModels.RegisterViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding _binding;
    private ViewPager2 _viewPager;
    private RegistrationAdapter _adapter;
    private MaterialButton _nextButton, _backButton;
    private LinearLayout _signUpLayout;
    private List<Fragment> _fragments;
    private RegisterViewModel _registerViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(_binding.getRoot());
        getSupportActionBar();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        _registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        _viewPager = _binding.viewPager;
        _nextButton = _binding.nextButton;
        _backButton = _binding.backButton;
        _signUpLayout = _binding.signUpLayout;

        _viewPager.setUserInputEnabled(false);

        // Set up _adapter and fragments
        _fragments = new ArrayList<>();
        _fragments.add(new RoleSelectionFragment());
        _adapter = new RegistrationAdapter(this, _fragments);
        _viewPager.setAdapter(_adapter);

        _registerViewModel.fragments.observe(this, new Observer<List<Fragment>>() {
            @Override
            public void onChanged(List<Fragment> fragments) {
                _fragments = fragments;
                _adapter = new RegistrationAdapter(RegisterActivity.this, fragments);
                _viewPager.setAdapter(_adapter);
                _viewPager.setUserInputEnabled(false);

                // Optionally, move to the first fragment
//                _viewPager.setCurrentItem(0, true);
                // Replace IntroFragment with ViewPagerFragment and show navigation buttons
                _viewPager.setCurrentItem(_viewPager.getCurrentItem()+1);
                // Show navigation buttons if required
                _signUpLayout.setVisibility(View.VISIBLE);

            }
        });

        // Navigation buttons
        _nextButton.setOnClickListener(v -> {
            if (_viewPager.getCurrentItem() < _adapter.getItemCount() - 1) {

                // validate before the next fragment
                int currentItem = _viewPager.getCurrentItem();

                // Ensure the fragment implements the required interface
                Fragment currentFragment = _fragments.get(currentItem);

                if (((FragmentValidation) currentFragment).validate()){
                    _viewPager.setCurrentItem(_viewPager.getCurrentItem() + 1);

                    // if we are on fragment before success message we remove buttons
                    if(_viewPager.getCurrentItem() == _adapter.getItemCount() - 1){
                        _signUpLayout.setVisibility(View.GONE);
                        // Final step, submit registration
                        submitRegistration();
                    }
                }
            }
        });

        _backButton.setOnClickListener(v -> {
            if (_viewPager.getCurrentItem() > 0) {
                _viewPager.setCurrentItem(_viewPager.getCurrentItem() - 1);
            }
            if (_viewPager.getCurrentItem() == 0){
                _signUpLayout.setVisibility(View.GONE); // Show navigation buttons
            }
        });

    }

    public void onIntroFinished(RegisterUserRole selectedRole) {
        _registerViewModel.setRole(selectedRole);

//        _fragments.add(new StepOneFragment());

//        _signUpLayout.setVisibility(View.VISIBLE); // Show navigation buttons
    }

    private void submitRegistration() {
    }


}