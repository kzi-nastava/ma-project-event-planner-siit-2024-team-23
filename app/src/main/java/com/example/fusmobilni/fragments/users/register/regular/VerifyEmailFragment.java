package com.example.fusmobilni.fragments.users.register.regular;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.fusmobilni.activities.LoginActivity;
import com.example.fusmobilni.databinding.FragmentVerifyEmailBinding;


public class VerifyEmailFragment extends Fragment {
    private FragmentVerifyEmailBinding _binding;

    public VerifyEmailFragment() {
        // Required empty public constructor
    }

    public static VerifyEmailFragment newInstance() {
        return new VerifyEmailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentVerifyEmailBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();

        _binding.loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
            // prevent going back once the register process is finished
            requireActivity().finish();
        });

        return view;
    }
}