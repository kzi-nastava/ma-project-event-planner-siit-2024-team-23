package com.example.fusmobilni.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.ActivityHomeBinding;
import com.example.fusmobilni.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding _binding;

    private TextView _loginLink;

    private Button _registerBtn;



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

        _loginLink = _binding.loginText;
        _registerBtn = _binding.buttonWithArrow;

        _registerBtn.setOnClickListener(v->{
//            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//
//            startActivity(intent);
            Toast.makeText(this, "REGISTER", Toast.LENGTH_LONG).show();
        });

        _loginLink.setOnClickListener(v->{
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                startActivity(intent);
            Toast.makeText(this, "MRS NA LOGIn", Toast.LENGTH_LONG).show();
        });



    }


}