package com.example.fusmobilni.fragments.items.service;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentServiceDetailsBinding;
import com.example.fusmobilni.fragments.dialogs.FailiureDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SuccessDialogFragment;
import com.example.fusmobilni.model.items.service.Service;
import com.example.fusmobilni.responses.items.services.ServiceOverviewResponse;
import com.example.fusmobilni.responses.location.LocationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceDetailsFragment extends Fragment {

    private FragmentServiceDetailsBinding _binding;
    private ServiceOverviewResponse _service;
    private SpinnerDialogFragment _loader;

    private SuccessDialogFragment _success;
    private FailiureDialogFragment _failiure;
    private Long eventId;
    private double estimatedBudget;

    private Long _serviceId;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _binding = FragmentServiceDetailsBinding.inflate(inflater, container, false);
        View view = _binding.getRoot();
        _serviceId = getArguments().getLong("productId");
        eventId = getArguments().getLong("eventId");
        estimatedBudget = getArguments().getDouble("estimatedBudget");

        initializeDialogs();

        getServiceForOverview();

        return view;

    }

    private void initializePageSuccessful() {
        _binding.serviceDetailsText.setText(_service.getName());
        LocationResponse location = _service.getProvider().getCompanyLocation();
        _binding.textViewServiceLocationHorizontal.setText(location.getCity() + ", " + location.getStreet() + " " + location.getStreetNumber());
        _binding.textViewServiceCategory.setText(_service.getCategory().getName());
        _binding.textViewOrganizerNameServiceDetails.setText(_service.getProvider().getFirstName() + " " + _service.getProvider().getLastName());
        _binding.textViewServiceDescriptionDetails.setText(_service.getDescription());
        _binding.price.setText(String.valueOf(_service.getPrice()));
        _binding.imageView5.setImageResource(R.drawable.person);

        _binding.bookServiceButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_service_details_to_service_reservation,createBundle());
        });

        initializeFavoriteButton();
    }

    private Bundle createBundle() {
        Bundle args = new Bundle();
        args.putLong("serviceId", _serviceId);
        args.putDouble("estimatedBudget", estimatedBudget);
        args.putLong("eventId", eventId);
        return args;
    }

    private void initializeDialogs() {
        _loader = new SpinnerDialogFragment();
        _loader.setCancelable(false);

    }

    private void getServiceForOverview() {
        _loader.show(getFragmentManager(), "loading_spinner");
        Call<ServiceOverviewResponse> call = ClientUtils.serviceOfferingService.findServiceForOverview(_serviceId);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ServiceOverviewResponse> call, Response<ServiceOverviewResponse> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                _service = response.body();
                _loader.dismiss();
                initializePageSuccessful();

            }

            @Override
            public void onFailure(Call<ServiceOverviewResponse> call, Throwable t) {

            }
        });

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