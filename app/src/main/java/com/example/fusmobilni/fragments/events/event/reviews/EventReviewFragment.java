package com.example.fusmobilni.fragments.events.event.reviews;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.databinding.FragmentEventReviewBinding;

public class EventReviewFragment extends Fragment {

    private FragmentEventReviewBinding _binding;

    public EventReviewFragment() {
        // Required empty public constructor
    }


    public static EventReviewFragment newInstance(String param1, String param2) {
        EventReviewFragment fragment = new EventReviewFragment();
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
        _binding = FragmentEventReviewBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        return root;
    }
}