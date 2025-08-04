package com.example.fusmobilni.fragments.notifications;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentEventUpdateNotificationBinding;
import com.example.fusmobilni.databinding.FragmentEventUpdateNotificationUnreadBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventUpdateNotificationUnreadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventUpdateNotificationUnreadFragment extends Fragment {


    private FragmentEventUpdateNotificationUnreadBinding _binding;

    public EventUpdateNotificationUnreadFragment() {
        // Required empty public constructor
    }

    public static EventUpdateNotificationUnreadFragment newInstance(String param1, String param2) {
        EventUpdateNotificationUnreadFragment fragment = new EventUpdateNotificationUnreadFragment();
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
        _binding = FragmentEventUpdateNotificationUnreadBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        return root;
    }
}