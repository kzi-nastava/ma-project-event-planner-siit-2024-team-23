package com.example.fusmobilni.fragments.notifications;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.databinding.FragmentItemReviewNotificationUnreadBinding;

public class ItemReviewNotificationUnreadFragment extends Fragment {

    private FragmentItemReviewNotificationUnreadBinding _binding;

    public static ItemReviewNotificationUnreadFragment newInstance(String param1, String param2) {
        ItemReviewNotificationUnreadFragment fragment = new ItemReviewNotificationUnreadFragment();
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
        _binding = FragmentItemReviewNotificationUnreadBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        return root;
    }
}