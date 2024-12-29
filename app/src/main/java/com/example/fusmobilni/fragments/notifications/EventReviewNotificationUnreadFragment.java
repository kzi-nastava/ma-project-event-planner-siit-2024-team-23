package com.example.fusmobilni.fragments.notifications;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.databinding.FragmentEventReviewNotificationUnreadBinding;

public class EventReviewNotificationUnreadFragment extends Fragment {


    private FragmentEventReviewNotificationUnreadBinding _binding;

    public EventReviewNotificationUnreadFragment() {
        // Required empty public constructor
    }

    public static EventReviewNotificationUnreadFragment newInstance(String param1, String param2) {
        EventReviewNotificationUnreadFragment fragment = new EventReviewNotificationUnreadFragment();
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
        _binding = FragmentEventReviewNotificationUnreadBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        return root;
    }
}