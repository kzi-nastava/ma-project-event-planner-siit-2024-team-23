package com.example.fusmobilni.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.fusmobilni.databinding.ActivityFastRegisterBinding;
import com.example.fusmobilni.fragments.users.register.fast.HashInvalidFragment;
import com.example.fusmobilni.fragments.users.register.fast.StepOneFastRegistrationFragment;
import com.example.fusmobilni.fragments.users.register.fast.StepTwoFastRegistrationFragment;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.responses.FastRegisterInvitationResponse;
import com.example.fusmobilni.viewModels.users.register.FastRegisterViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FastRegisterActivity extends AppCompatActivity {

    private ViewPager2 _viewPager;
    private ActivityFastRegisterBinding _binding;

    private FastRegisterViewModel _viewModel;

    private RegistrationAdapter _adapter;
    private List<Fragment> _fragments;

    private String _hash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _binding = ActivityFastRegisterBinding.inflate(getLayoutInflater());
        setContentView(_binding.getRoot());
        getSupportActionBar();

        Intent intent = getIntent();
        Uri data = intent.getData();

        if (data != null) {
            _hash = data.getLastPathSegment();
            Toast.makeText(this, "Received hash: " + _hash, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Received nothing", Toast.LENGTH_LONG).show();

        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        _viewModel = new ViewModelProvider(this).get(FastRegisterViewModel.class);
        _viewPager = _binding.viewPager;

        _viewPager.setUserInputEnabled(true);

        _fragments = new ArrayList<>();

        getHash(data);
    }

    private void initalizePageInvalid() {
        _fragments.add(new HashInvalidFragment());
        _adapter = new RegistrationAdapter(this, _fragments);
        _viewPager.setAdapter(_adapter);
        _viewModel.fragments.observe(this,fragments -> {
            observeFragments(fragments);
        });
    }

    private void initalizePageValid() {
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

    private void getHash(Uri data) {
        if (data == null) {
            initalizePageInvalid();
            return;
        }
        Call<FastRegisterInvitationResponse> call = ClientUtils.fastRegisterService.getByHash(_hash);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<FastRegisterInvitationResponse> call, Response<FastRegisterInvitationResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), response.body().hash, Toast.LENGTH_LONG).show();
                    if (!_hash.equals(response.body().hash)) {
                        initalizePageInvalid();
                    }
                    initalizePageValid();
                }
            }

            @Override
            public void onFailure(Call<FastRegisterInvitationResponse> call, Throwable t) {
                initalizePageInvalid();
            }
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
        if (_viewPager.getCurrentItem() == 0) {
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
            if (_viewPager.getCurrentItem() == _adapter.getItemCount() - 1) {
                _binding.nextButton.setText("Finish");
            }
        } else if (_viewPager.getCurrentItem() == _adapter.getItemCount() - 1) {
            int currentItem = _viewPager.getCurrentItem();
            Fragment currentFragment = _fragments.get(currentItem);

            if (((FragmentValidation) currentFragment).validate()) {

            }
        }
    }

    private void submitRegistration() {
    }

}