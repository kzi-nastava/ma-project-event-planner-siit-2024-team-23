package com.example.fusmobilni.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
    private Service _service;

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

        _service = getArguments().getParcelable("service");

        _binding.serviceDetailsText.setText(_service.getName());
        _binding.textViewServiceLocationHorizontal.setText(_service.getLocation());
        _binding.textViewServiceCategory.setText(_service.getCategory());
        _binding.textViewOrganizerNameServiceDetails.setText("Abramovich");
        _binding.textViewServiceDescriptionDetails.setText(_service.getDescription());
        _binding.imageView5.setImageResource(R.drawable.person);

        _binding.bookServiceButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_service_details_to_service_reservation, createBundle(_service));
        });

        initializeFavoriteButton();
        return view;

    }

    private Bundle createBundle(Service service) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("service", service);
        return bundle;
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