package com.example.fusmobilni.activities;

import android.os.Bundle;
import android.util.Log;
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
import com.example.fusmobilni.adapters.users.register.RegistrationAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.ActivityRegisterBinding;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.fragments.users.register.regular.RoleSelectionFragment;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.interfaces.IRegisterCallback;
import com.example.fusmobilni.model.enums.UserType;
import com.example.fusmobilni.responses.register.regular.RegisterResponse;
import com.example.fusmobilni.viewModels.users.register.RegisterViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private ViewPager2 _viewPager;
    private RegistrationAdapter _adapter;
    private LinearLayout _signUpLayout;
    private List<Fragment> _fragments;
    private RegisterViewModel _registerViewModel;
    private SpinnerDialogFragment _loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ClientUtils.initalize(CustomSharedPrefs.getInstance(getApplicationContext()));

        ActivityRegisterBinding binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        _registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        _loader = new SpinnerDialogFragment();
        _loader.setCancelable(false);
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
                if(_viewPager.getCurrentItem() == _adapter.getItemCount() - 2){
                    submitRegistration();
                    return;
                }
                _viewPager.setCurrentItem(_viewPager.getCurrentItem() + 1);
            }
        }
    }

    public void onIntroFinished(UserType selectedRole) {
        _registerViewModel.setRole(selectedRole);
    }

    private void submitRegistration() {
        _loader.show(getSupportFragmentManager(), "loading_spinner");
        _registerViewModel.createUser(RegisterActivity.this, new IRegisterCallback(){
            @Override
            public void onSuccess(RegisterResponse response) {
                Log.d("tag", "Registration successful: " + response.toString());
                _signUpLayout.setVisibility(View.GONE);
                _viewPager.setCurrentItem(_viewPager.getCurrentItem() + 1);
                _loader.dismiss();
            }
            @Override
            public void onFailure(Throwable throwable) {
                Log.d("tag" , "Registration failed:" + throwable.getMessage());
                Toast.makeText(RegisterActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                _loader.dismiss();
            }
        });
    }


}