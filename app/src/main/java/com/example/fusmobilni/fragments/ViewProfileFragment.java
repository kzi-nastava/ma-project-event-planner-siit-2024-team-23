package com.example.fusmobilni.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.activities.HomeActivity;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentRoleSelectionBinding;
import com.example.fusmobilni.databinding.FragmentViewProfileBinding;
import com.example.fusmobilni.model.enums.UserType;


public class ViewProfileFragment extends Fragment {
    private FragmentViewProfileBinding _binding;

    public ViewProfileFragment() {
        // Required empty public constructor
    }
    public static ViewProfileFragment newInstance() {
        return new ViewProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _binding = FragmentViewProfileBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();

        _binding.logout.setOnClickListener(v -> {
            logout();
        });

        return  view;

    }

    private void logout() {
        CustomSharedPrefs sharedPrefs = new CustomSharedPrefs(requireContext());
        sharedPrefs.clearAll();
        Intent intent = new Intent(requireContext(), HomeActivity.class);
        startActivity(intent);
        requireActivity().finish();

    }
}