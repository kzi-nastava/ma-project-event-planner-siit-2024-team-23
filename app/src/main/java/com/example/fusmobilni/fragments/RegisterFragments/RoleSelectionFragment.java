package com.example.fusmobilni.fragments.RegisterFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.fusmobilni.activities.RegisterActivity;
import com.example.fusmobilni.databinding.FragmentRoleSelectionBinding;
import com.example.fusmobilni.model.enums.RegisterUserRole;


public class RoleSelectionFragment extends Fragment {
    private FragmentRoleSelectionBinding _binding;

    public RoleSelectionFragment() {
        // Required empty public constructor
    }
    public static RoleSelectionFragment newInstance() {
        return  new RoleSelectionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        _binding = FragmentRoleSelectionBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();


        Button eventOrganizer = _binding.eventOrganizer;
        Button serviceProvider = _binding.serviceProvider;

        eventOrganizer.setOnClickListener(v->
                ((RegisterActivity) requireActivity()).onIntroFinished(RegisterUserRole.EVENT_ORGANIZER));

        serviceProvider.setOnClickListener(v->
                ((RegisterActivity) requireActivity()).onIntroFinished(RegisterUserRole.SERVICE_PROVIDER));

        return view;

    }
}