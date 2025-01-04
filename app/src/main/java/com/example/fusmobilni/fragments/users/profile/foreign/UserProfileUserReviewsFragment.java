package com.example.fusmobilni.fragments.users.profile.foreign;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.adapters.users.reviews.UserReviewAdapter;
import com.example.fusmobilni.databinding.FragmentUserProfileUserReviewsBinding;
import com.example.fusmobilni.viewModels.users.ForeignProfileViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileUserReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileUserReviewsFragment extends Fragment {

    private FragmentUserProfileUserReviewsBinding _binding;
    private ForeignProfileViewModel viewModel;
    private UserReviewAdapter adapter;

    public UserProfileUserReviewsFragment() {
        // Required empty public constructor
    }

    public static UserProfileUserReviewsFragment newInstance(String param1, String param2) {
        UserProfileUserReviewsFragment fragment = new UserProfileUserReviewsFragment();
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
        _binding = FragmentUserProfileUserReviewsBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        viewModel = new ViewModelProvider(getParentFragment()).get(ForeignProfileViewModel.class);
        viewModel.getUserReviews().observe(getViewLifecycleOwner(), v -> {
            adapter = new UserReviewAdapter(viewModel.getUserReviews().getValue());
            _binding.profileUserReviews.setAdapter(adapter);
        });
        return root;
    }
}