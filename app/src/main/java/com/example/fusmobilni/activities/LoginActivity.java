package com.example.fusmobilni.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import com.example.fusmobilni.R;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.ActivityLoginBinding;
import com.example.fusmobilni.viewModels.users.login.LoginViewModel;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding _binding;
    private LoginViewModel _loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ClientUtils.initalize(getApplicationContext());

        _binding = ActivityLoginBinding.inflate(getLayoutInflater());
        _loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        setContentView(_binding.getRoot());

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        _loginViewModel.getIsLoading().observe(this, isLoading -> {
            if(isLoading != null){
                _binding.loading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                _binding.buttonWithArrow.setEnabled(!isLoading);
                int color = ContextCompat.getColor(this, isLoading ?
                        R.color.primary_blue_500 : R.color.primary_blue_900);
                _binding.buttonWithArrow.setBackgroundColor(color);
            }
        });

        _loginViewModel.getLoginSuccess().observe(this, isSuccess -> {
            if (isSuccess != null) {
                handleLoginSuccess(isSuccess);
            }
        });

        _binding.buttonWithArrow.setOnClickListener(v->{
            if(validate()){
                login();
            }
        });

        _binding.registerText.setOnClickListener(v->{
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            // prevent going back once the register process is finished
            this.finish();
        });
    }

    private void handleLoginSuccess(Boolean isSuccess){
        if (isSuccess) {
            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void login(){
        String email = Objects.requireNonNull(_binding.emailInput.getEditText()).getText().toString().trim();
        String password = Objects.requireNonNull(_binding.passwordInput.getEditText()).getText().toString().trim();

        _loginViewModel.setEmail(email);
        _loginViewModel.setPassword(password);

        _loginViewModel.login();
    }

    private boolean validate(){
        String email = Objects.requireNonNull(_binding.emailInput.getEditText()).getText().toString().trim();
        String password = Objects.requireNonNull(_binding.passwordInput.getEditText()).getText().toString().trim();

        if (email.isEmpty()) {
            _binding.emailInput.setErrorEnabled(true);
            _binding.emailInput.setError("Email is required");
            return false;
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _binding.emailInput.setErrorEnabled(true);
            _binding.emailInput.setError("Invalid email format");
            return false;
        }
        else {
            _binding.emailInput.setError(null);
            _binding.emailInput.setErrorEnabled(false);
        }
        if (password.isEmpty()) {
            _binding.passwordInput.setErrorEnabled(true);
            _binding.passwordInput.setError("Password is required");
            return false;
        } else {
            _binding.passwordInput.setError(null);
            _binding.passwordInput.setErrorEnabled(false);
        }
        return true;
    }
}