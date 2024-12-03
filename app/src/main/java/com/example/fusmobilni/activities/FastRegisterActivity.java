package com.example.fusmobilni.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.RegistrationAdapter;
import com.example.fusmobilni.databinding.ActivityFastRegisterBinding;
import com.example.fusmobilni.fragments.RegisterFragments.Fast.StepOneFastRegistrationFragment;
import com.example.fusmobilni.fragments.RegisterFragments.Fast.StepTwoFastRegistrationFragment;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.viewModels.FastRegisterViewModel;

import java.util.ArrayList;
import java.util.List;

public class FastRegisterActivity extends AppCompatActivity {

    private ViewPager2 _viewPager;
    private ActivityFastRegisterBinding _binding;

    private FastRegisterViewModel _viewModel;

    private RegistrationAdapter _adapter;
    private List<Fragment> _fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _binding = ActivityFastRegisterBinding.inflate(getLayoutInflater());
        setContentView(_binding.getRoot());
        getSupportActionBar();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        _viewModel = new ViewModelProvider(this).get(FastRegisterViewModel.class);
        _viewPager = _binding.viewPager;

        _viewPager.setUserInputEnabled(true);

        _fragments = new ArrayList<>();
        _fragments.add(new StepOneFastRegistrationFragment());
        _fragments.add(new StepTwoFastRegistrationFragment());
        _adapter = new RegistrationAdapter(this, _fragments);

        _viewPager.setAdapter(_adapter);

        _viewModel.fragments.observe(this, fragments -> {
            observeFragments(fragments);
        });
        _binding.nextButton.setOnClickListener(v -> {
            nextButtonClick();
        });
        _binding.backButton.setOnClickListener(v -> {
            backButtonClick();
        });
    }

    private void observeFragments(List<Fragment> fragments) {
        _fragments = fragments;
        _adapter = new RegistrationAdapter(FastRegisterActivity.this, fragments);
        _viewPager.setAdapter(_adapter);
        _viewPager.setUserInputEnabled(false);

        _viewPager.setCurrentItem(_viewPager.getCurrentItem() + 1);

    }

    private void backButtonClick() {
        if (_viewPager.getCurrentItem() > 0) {
            _viewPager.setCurrentItem(_viewPager.getCurrentItem() - 1);
        }
        if(_viewPager.getCurrentItem()==0){
            _binding.nextButton.setText("Next");
        }
    }

    private void nextButtonClick() {
        if (_viewPager.getCurrentItem() < _adapter.getItemCount() - 1) {

            int currentItem = _viewPager.getCurrentItem();
            Fragment currentFragment = _fragments.get(currentItem);

            if (((FragmentValidation) currentFragment).validate()) {
                _viewPager.setCurrentItem(_viewPager.getCurrentItem() + 1);
            }
            if(_viewPager.getCurrentItem() == _adapter.getItemCount() - 1){
                _binding.nextButton.setText("Finish");
            }
        }
        else if(_viewPager.getCurrentItem() == _adapter.getItemCount()-1){
            int currentItem = _viewPager.getCurrentItem();
            Fragment currentFragment = _fragments.get(currentItem);

            if (((FragmentValidation) currentFragment).validate()) {

            }
        }
    }

    private void submitRegistration() {
    }

}