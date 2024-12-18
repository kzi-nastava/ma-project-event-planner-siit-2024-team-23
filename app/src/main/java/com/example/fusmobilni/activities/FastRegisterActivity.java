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
import com.example.fusmobilni.clients.GeocodingClient;
import com.example.fusmobilni.databinding.ActivityFastRegisterBinding;
import com.example.fusmobilni.fragments.dialogs.FailiureDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SuccessDialogFragment;
import com.example.fusmobilni.fragments.users.register.fast.HashInvalidFragment;
import com.example.fusmobilni.fragments.users.register.fast.StepOneFastRegistrationFragment;
import com.example.fusmobilni.fragments.users.register.fast.StepTwoFastRegistrationFragment;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.requests.auth.AuthenticatedUserRequest;
import com.example.fusmobilni.requests.register.fast.FastRegisterRequest;
import com.example.fusmobilni.responses.FastRegisterInvitationResponse;
import com.example.fusmobilni.responses.geoCoding.GeoCodingResponse;
import com.example.fusmobilni.responses.location.LocationResponse;
import com.example.fusmobilni.responses.register.fast.FastRegisterResponse;
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

    private SpinnerDialogFragment _loader;
    private SuccessDialogFragment _success;
    private FailiureDialogFragment _failiure;

    private String _hash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ClientUtils.initalize(getApplicationContext());

        _binding = ActivityFastRegisterBinding.inflate(getLayoutInflater());
        setContentView(_binding.getRoot());
        getSupportActionBar();

        Intent intent = getIntent();
        Uri data = intent.getData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        _viewModel = new ViewModelProvider(this).get(FastRegisterViewModel.class);
        _viewPager = _binding.viewPager;

        _viewPager.setUserInputEnabled(false);

        _fragments = new ArrayList<>();

        getHash(data);
    }

    private void initalizePageInvalid() {
        _fragments.add(new HashInvalidFragment());
        _adapter = new RegistrationAdapter(this, _fragments);
        _viewPager.setAdapter(_adapter);
        _viewModel.fragments.observe(this, fragments -> {
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
        initializeDialogs();
    }

    private void initializeDialogs() {
        _loader = new SpinnerDialogFragment();
        _loader.setCancelable(false);
        _success = new SuccessDialogFragment();
        _success.setCancelable(false);
        _failiure = new FailiureDialogFragment();
        _failiure.setCancelable(false);
    }

    private void getHash(Uri data) {
        if (data == null) {
            initalizePageInvalid();
            return;
        }
        _hash = data.getLastPathSegment();
        Call<FastRegisterInvitationResponse> call = ClientUtils.fastRegisterService.getByHash(_hash);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<FastRegisterInvitationResponse> call, Response<FastRegisterInvitationResponse> response) {
                if (response.isSuccessful()) {
                    if (!_hash.equals(response.body().hash)) {
                        initalizePageInvalid();
                    }
                    _viewModel.setEmail(response.body().getEmail());
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
                submitRegistration();
            }
        }
    }

    private String transformAdress() {
        String city = _viewModel.getCity().getValue();
        String street = _viewModel.getAddress().getValue().split(" ")[0];
        String streetNumber = _viewModel.getAddress().getValue().split(" ")[1];
        return streetNumber + ", " + street + ", " + city;
    }

    private void submitRegistration() {

        _loader.show(getSupportFragmentManager(), "loading_spinner");
        Call<List<GeoCodingResponse>> call = GeocodingClient.geoCodingService.getGeoCode("pk.4a4083e362875d1ad824d7d1d981b2eb", transformAdress(), "json");
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<GeoCodingResponse>> call, Response<List<GeoCodingResponse>> response) {
                if (!response.isSuccessful()) {

                    openFailiureWindow();
                    return;
                }

                Double lat = Double.valueOf(response.body().get(0).lat);
                Double lon = Double.valueOf(response.body().get(0).lon);

                registerUser(lat, lon);

            }

            @Override
            public void onFailure(Call<List<GeoCodingResponse>> call, Throwable t) {
                openFailiureWindow();
            }
        });
    }

    private void registerUser(Double lat, Double lon) {
        String street = _viewModel.getAddress().getValue().split(" ")[0];
        String streetNumber = _viewModel.getAddress().getValue().split(" ")[1];


        FastRegisterRequest request = new FastRegisterRequest(
                _viewModel.getEmail().getValue(),
                _viewModel.getName().getValue(),
                _hash,
                new LocationResponse(_viewModel.getCity().getValue(), lat, lon, street, streetNumber),
                _viewModel.getPassword().getValue(),
                _viewModel.getPhone().getValue(),
                _viewModel.getPassword().getValue(),
                _viewModel.getLastName().getValue()
        );

        Call<FastRegisterResponse> call = ClientUtils.authService.fastRegister(request);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<FastRegisterResponse> call, Response<FastRegisterResponse> response) {
                if (response.isSuccessful()) {
                    openSuccessWindow();
                } else {
                    openFailiureWindow();
                }
            }

            @Override
            public void onFailure(Call<FastRegisterResponse> call, Throwable t) {
                openFailiureWindow();

            }
        });

    }

    void openSuccessWindow() {
        if (_loader != null) {
            _loader.dismiss();
        }
        Bundle args = new Bundle();
        args.putString("Title", "Success");
        args.putString("Message", "Verification email has been sent to " + _viewModel.getEmail().getValue());
        _success.setArguments(args);
        _success.show(getSupportFragmentManager(), "success_dialog");
    }

    void openFailiureWindow() {
        if (_loader != null) {
            _loader.dismiss();
        }
        Bundle args = new Bundle();
        args.putString("Title", "Failiure");
        args.putString("Message", "Failed to register");
        _failiure.setArguments(args);
        _failiure.show(getSupportFragmentManager(), "failiure_dialog");
    }

}