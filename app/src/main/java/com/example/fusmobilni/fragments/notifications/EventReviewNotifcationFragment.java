package com.example.fusmobilni.fragments.notifications;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentEventReviewNotifcationBinding;

public class EventReviewNotifcationFragment extends Fragment {

    private FragmentEventReviewNotifcationBinding _binding;

    public EventReviewNotifcationFragment() {
        // Required empty public constructor
    }

    public static EventReviewNotifcationFragment newInstance(String param1, String param2) {
        EventReviewNotifcationFragment fragment = new EventReviewNotifcationFragment();
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
        _binding = FragmentEventReviewNotifcationBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        return root;
    }
}