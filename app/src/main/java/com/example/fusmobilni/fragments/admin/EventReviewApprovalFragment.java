package com.example.fusmobilni.fragments.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentEventReviewApprovalBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventReviewApprovalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventReviewApprovalFragment extends Fragment {


    private FragmentEventReviewApprovalBinding _binding;

    public EventReviewApprovalFragment() {
        // Required empty public constructor
    }

    public static EventReviewApprovalFragment newInstance(String param1, String param2) {
        EventReviewApprovalFragment fragment = new EventReviewApprovalFragment();
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
        _binding = FragmentEventReviewApprovalBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        return root;
    }
}