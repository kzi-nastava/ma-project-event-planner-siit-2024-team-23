package com.example.fusmobilni.fragments.items.reviews;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentItemReviewBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemReviewFragment extends Fragment {


    private FragmentItemReviewBinding _binding;

    public ItemReviewFragment() {
        // Required empty public constructor
    }


    public static ItemReviewFragment newInstance(String param1, String param2) {
        ItemReviewFragment fragment = new ItemReviewFragment();
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
        _binding = FragmentItemReviewBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        return root;
    }
}