package com.example.fusmobilni.fragments.items.reviews;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.databinding.FragmentItemReviewApproveBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemReviewApproveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemReviewApproveFragment extends Fragment {

    private FragmentItemReviewApproveBinding _binding;

    public ItemReviewApproveFragment() {
        // Required empty public constructor
    }

    public static ItemReviewApproveFragment newInstance(String param1, String param2) {
        ItemReviewApproveFragment fragment = new ItemReviewApproveFragment();
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
        _binding = FragmentItemReviewApproveBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        return root;
    }
}