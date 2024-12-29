package com.example.fusmobilni.fragments.events.event.reviews;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentEventReviewApprovalBinding;

public class EventReviewApproveFragment extends Fragment {


    private FragmentEventReviewApprovalBinding _binding;

    public EventReviewApproveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventReviewApproveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventReviewApproveFragment newInstance(String param1, String param2) {
        EventReviewApproveFragment fragment = new EventReviewApproveFragment();
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
        _binding = FragmentEventReviewApprovalBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        return root;
    }
}