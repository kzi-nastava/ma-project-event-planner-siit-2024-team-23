package com.example.fusmobilni.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentServiceDetailsBinding;
import com.example.fusmobilni.model.Service;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceDetailsFragment extends Fragment {

    private FragmentServiceDetailsBinding _binding;

    public ServiceDetailsFragment() {
        // Required empty public constructor
    }

    private boolean favorite = false;

    public static ServiceDetailsFragment newInstance(String param1, String param2) {
        ServiceDetailsFragment fragment = new ServiceDetailsFragment();
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
        _binding = FragmentServiceDetailsBinding.inflate(inflater, container, false);
        View view = _binding.getRoot();

        Service service = getArguments().getParcelable("service");

        _binding.serviceDetailsText.setText(service.getName());
        _binding.textViewServiceLocationHorizontal.setText(service.getLocation());
        _binding.textViewServiceCategory.setText(service.getCategory());
        _binding.textViewOrganizerNameServiceDetails.setText("Ibrahimovic");
        _binding.textViewServiceDescriptionDetails.setText(service.getDescription());
        _binding.imageView5.setImageResource(R.drawable.person);

        initializeFavoriteButton();
        return view;

    }
    private void initializeFavoriteButton() {
        _binding.favoriteButton.setOnClickListener(v -> {
            favorite = !favorite;
            _binding.favoriteButton.animate()
                    .alpha(0f)
                    .setDuration(100)
                    .withEndAction(() -> {
                        _binding.favoriteButton.setIconResource(favorite ? R.drawable.ic_heart_full : R.drawable.ic_heart);

                        _binding.favoriteButton.animate()
                                .alpha(1f)
                                .setDuration(100)
                                .start();
                    })
                    .start();
        });
    }
}