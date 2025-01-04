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
import com.example.fusmobilni.adapters.items.reviews.ItemReviewsAdapter;
import com.example.fusmobilni.databinding.FragmentUserProfileItemReviewsBinding;
import com.example.fusmobilni.viewModels.users.ForeignProfileViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileItemReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileItemReviewsFragment extends Fragment {


    private FragmentUserProfileItemReviewsBinding _binding;
    private ForeignProfileViewModel viewModel;
    private ItemReviewsAdapter adapter;

    public UserProfileItemReviewsFragment() {
        // Required empty public constructor
    }

    public static UserProfileItemReviewsFragment newInstance(String param1, String param2) {
        UserProfileItemReviewsFragment fragment = new UserProfileItemReviewsFragment();
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
        _binding = FragmentUserProfileItemReviewsBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        viewModel = new ViewModelProvider(getParentFragment()).get(ForeignProfileViewModel.class);
        viewModel.getItemReviews().observe(getViewLifecycleOwner(), v -> {
            adapter = new ItemReviewsAdapter(viewModel.getItemReviews().getValue());
            _binding.profileItemReviews.setAdapter(adapter);
        });
        return root;
    }
}