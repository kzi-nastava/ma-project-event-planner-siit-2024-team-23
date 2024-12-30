package com.example.fusmobilni.fragments.notifications;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentEventUpdateNotificationBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventUpdateNotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventUpdateNotificationFragment extends Fragment {


    private FragmentEventUpdateNotificationBinding _binding;

    public EventUpdateNotificationFragment() {
        // Required empty public constructor
    }

    public static EventUpdateNotificationFragment newInstance(String param1, String param2) {
        EventUpdateNotificationFragment fragment = new EventUpdateNotificationFragment();
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

        _binding = FragmentEventUpdateNotificationBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        return root;
    }
}