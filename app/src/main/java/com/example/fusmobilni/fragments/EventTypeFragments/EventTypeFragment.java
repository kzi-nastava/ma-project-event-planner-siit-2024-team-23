package com.example.fusmobilni.fragments.EventTypeFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.databinding.FragmentEventTypeBinding;

public class EventTypeFragment extends Fragment {
    private FragmentEventTypeBinding _binding;

    public EventTypeFragment() {
        // Required empty public constructor
    }
    public static EventTypeFragment newInstance(String param1, String param2) {
        return new EventTypeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentEventTypeBinding.inflate(getLayoutInflater());
        return _binding.getRoot();
    }
}