package com.example.fusmobilni.fragments.items.service;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentServiceDetailsRegularBinding;
import com.example.fusmobilni.fragments.dialogs.FailiureDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SuccessDialogFragment;
import com.example.fusmobilni.responses.items.services.ServiceOverviewResponse;
import com.example.fusmobilni.responses.location.LocationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceDetailsRegularFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceDetailsRegularFragment extends Fragment {
    private boolean favorite = false;
    private Long _serviceId;
    private ServiceOverviewResponse _service;
    private SpinnerDialogFragment _loader;

    private SuccessDialogFragment _success;
    private FailiureDialogFragment _failiure;

    private FragmentServiceDetailsRegularBinding _binding;

    public ServiceDetailsRegularFragment() {
        // Required empty public constructor
    }


    public static ServiceDetailsRegularFragment newInstance(String param1, String param2) {
        ServiceDetailsRegularFragment fragment = new ServiceDetailsRegularFragment();
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
        _binding = FragmentServiceDetailsRegularBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        _serviceId = getArguments().getLong("productId");
        initializeDialogs();

        getServiceForOverview();
        return root;
    }

    private void initializeDialogs() {
        _loader = new SpinnerDialogFragment();
        _loader.setCancelable(false);

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


        initializeFavoriteButton();
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
}