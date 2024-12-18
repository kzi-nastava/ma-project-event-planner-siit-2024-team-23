package com.example.fusmobilni.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fusmobilni.R;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.ActivityVerifyEmailBinding;
import com.example.fusmobilni.fragments.dialogs.FailiureDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SuccessDialogFragment;
import com.example.fusmobilni.responses.auth.EmailVerificationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyEmailActivity extends AppCompatActivity {

    private SpinnerDialogFragment _loader;
    private SuccessDialogFragment _success;
    private FailiureDialogFragment _failiure;
    private ActivityVerifyEmailBinding _binding;
    private String _token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ClientUtils.initalize(getApplicationContext());

        _binding = ActivityVerifyEmailBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_verify_email);
        getSupportActionBar();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeDialogs();

        Intent intent = getIntent();
        Uri data = intent.getData();

        verifyEmail(data);

    }
    private void initializeDialogs(){
        _loader = new SpinnerDialogFragment();
        _loader.setCancelable(false);
        _success = new SuccessDialogFragment();
        _success.setCancelable(false);
        _failiure = new FailiureDialogFragment();
        _failiure.setCancelable(false);
    }
    private void verifyEmail(Uri data) {
        if (data == null) {
            return;
        }
        _token = data.getLastPathSegment();
        _loader.show(getSupportFragmentManager(), "loading_spinner");
        Call<EmailVerificationResponse> call = ClientUtils.authService.verifyEmail(_token);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<EmailVerificationResponse> call, Response<EmailVerificationResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().status.equals("ACCEPTED")) {
                        openSuccessWindow(response.body().getMessage());
                    } else {
                        openFailiureWindow(response.body().getMessage());
                    }
                } else {

                    openFailiureWindow(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<EmailVerificationResponse> call, Throwable t) {
                openFailiureWindow("Request failed");
            }
        });
    }
    void openSuccessWindow(String message) {
        if (_loader != null) {
            _loader.dismiss();
        }
        Bundle args = new Bundle();
        args.putString("Title","Success");
        args.putString("Message",message);
        _success.setArguments(args);
        _success.show(getSupportFragmentManager(),"success_dialog");
    }

    void openFailiureWindow(String message) {
        if (_loader != null) {
            _loader.dismiss();
        }
        Bundle args = new Bundle();
        args.putString("Title","Failure");
        args.putString("Message",message);
        _failiure.setArguments(args);
        _failiure.show(getSupportFragmentManager(),"failiure_dialog");
    }
}