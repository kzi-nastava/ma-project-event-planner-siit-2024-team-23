package com.example.fusmobilni.fragments.loading;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentLoadingCardVerticalBinding;

public class LoadingCardVerticalFragment extends Fragment {

    private FragmentLoadingCardVerticalBinding _binding;

    public LoadingCardVerticalFragment() {
        // Required empty public constructor
    }

    public static LoadingCardVerticalFragment newInstance(String param1, String param2) {
        LoadingCardVerticalFragment fragment = new LoadingCardVerticalFragment();
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
        _binding = FragmentLoadingCardVerticalBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        return root;
    }
}