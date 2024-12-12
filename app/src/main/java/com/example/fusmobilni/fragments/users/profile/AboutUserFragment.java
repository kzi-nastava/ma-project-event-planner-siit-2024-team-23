package com.example.fusmobilni.fragments.users.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.databinding.FragmentAboutUserBinding;

public class AboutUserFragment extends Fragment {
    private FragmentAboutUserBinding _binding;

    public AboutUserFragment() {
        // Required empty public constructor
    }

    public static AboutUserFragment newInstance() {
        return new AboutUserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentAboutUserBinding.inflate(getLayoutInflater());
        return _binding.getRoot();
    }
}