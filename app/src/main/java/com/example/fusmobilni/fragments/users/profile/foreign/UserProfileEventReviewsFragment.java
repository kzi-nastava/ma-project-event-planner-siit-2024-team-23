package com.example.fusmobilni.fragments.users.profile.foreign;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.events.reviews.EventReviewAdapter;
import com.example.fusmobilni.databinding.FragmentUserProfileEventReviewsBinding;
import com.example.fusmobilni.viewModels.users.ForeignProfileViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileEventReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileEventReviewsFragment extends Fragment {


    private FragmentUserProfileEventReviewsBinding _binding;
    private ForeignProfileViewModel viewModel;
    private EventReviewAdapter adapter;

    public UserProfileEventReviewsFragment() {
        // Required empty public constructor
    }

    public static UserProfileEventReviewsFragment newInstance(String param1, String param2) {
        UserProfileEventReviewsFragment fragment = new UserProfileEventReviewsFragment();
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
        _binding = FragmentUserProfileEventReviewsBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        viewModel = new ViewModelProvider(getParentFragment()).get(ForeignProfileViewModel.class);
        viewModel.getEventReviews().observe(getViewLifecycleOwner(), v -> {
            adapter = new EventReviewAdapter(viewModel.getEventReviews().getValue());
            _binding.profileEventReviews.setAdapter(adapter);
        });
        return root;
    }
}