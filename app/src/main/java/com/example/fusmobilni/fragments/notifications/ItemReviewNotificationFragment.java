package com.example.fusmobilni.fragments.notifications;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.databinding.FragmentItemReviewNotificationBinding;

public class ItemReviewNotificationFragment extends Fragment {

    private FragmentItemReviewNotificationBinding _binding;

    public static ItemReviewNotificationFragment newInstance(String param1, String param2) {
        ItemReviewNotificationFragment fragment = new ItemReviewNotificationFragment();
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
        _binding = FragmentItemReviewNotificationBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();


        return root;
    }
}