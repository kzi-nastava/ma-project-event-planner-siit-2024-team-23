package com.example.fusmobilni.fragments.users.reviews;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.databinding.FragmentUserReviewBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserReviewFragment extends Fragment {

    private FragmentUserReviewBinding _binding;

    public UserReviewFragment() {
        // Required empty public constructor
    }


    public static UserReviewFragment newInstance(String param1, String param2) {
        UserReviewFragment fragment = new UserReviewFragment();
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
        _binding = FragmentUserReviewBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        return root;
    }
}