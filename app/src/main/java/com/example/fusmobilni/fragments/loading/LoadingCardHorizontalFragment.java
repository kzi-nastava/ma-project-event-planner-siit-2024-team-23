package com.example.fusmobilni.fragments.loading;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentLoadingCardHorizontalBinding;

public class LoadingCardHorizontalFragment extends Fragment {


    private FragmentLoadingCardHorizontalBinding _binding;

    public LoadingCardHorizontalFragment() {
        // Required empty public constructor
    }


    public static LoadingCardHorizontalFragment newInstance(String param1, String param2) {
        LoadingCardHorizontalFragment fragment = new LoadingCardHorizontalFragment();
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
        _binding = FragmentLoadingCardHorizontalBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        return root;
    }
}