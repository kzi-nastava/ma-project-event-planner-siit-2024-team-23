package com.example.fusmobilni.fragments.users.userUpgrade;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fusmobilni.R;
import com.example.fusmobilni.activities.RegisterActivity;
import com.example.fusmobilni.databinding.FragmentUpgradeUserRoleSelectionBinding;
import com.example.fusmobilni.model.enums.UserType;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpgradeUserRoleSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpgradeUserRoleSelectionFragment extends Fragment {

    private FragmentUpgradeUserRoleSelectionBinding _binding;

    public UpgradeUserRoleSelectionFragment() {
        // Required empty public constructor
    }

    public static UpgradeUserRoleSelectionFragment newInstance(String param1, String param2) {
        UpgradeUserRoleSelectionFragment fragment = new UpgradeUserRoleSelectionFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _binding = FragmentUpgradeUserRoleSelectionBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();


        Button eventOrganizer = _binding.eventOrganizer;
        Button serviceProvider = _binding.serviceProvider;

        eventOrganizer.setOnClickListener(v->
                ((UpgradeUserFragment) requireParentFragment()).onIntroFinished(UserType.EVENT_ORGANIZER));

        serviceProvider.setOnClickListener(v->
                ((UpgradeUserFragment) requireParentFragment()).onIntroFinished(UserType.SERVICE_PROVIDER));
        return root;
    }
}