package com.example.fusmobilni.fragments.items.service;

import static com.example.fusmobilni.adapters.AdapterUtils.convertToUrisFromBase64;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.items.reviews.ItemReviewsAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentServiceDetailsRegularBinding;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.responses.items.IsBoughtItemResponse;
import com.example.fusmobilni.responses.items.services.ServiceOverviewResponse;
import com.example.fusmobilni.responses.location.LocationResponse;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceDetailsRegularFragment extends Fragment {
    Snackbar snackbar;
    private boolean favorite = false;
    private Long _serviceId;
    private ServiceOverviewResponse _service;
    private SpinnerDialogFragment _loader;
    private ItemReviewsAdapter _adapter;
    private boolean accordionOpen = false;
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
        _serviceId = getArguments().getLong("serviceId");
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
        try {
            _binding.imageView5.setImageURI(convertToUrisFromBase64(getContext(), _service.getProvider().getImage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            _binding.imageView4.setImageURI(convertToUrisFromBase64(getContext(), _service.getImage()));
        } catch (IOException e) {

        }

        initializeGradesAccordion();

        initializeFavoriteButton();

        checkIfBought();
    }

    private void showSnackBar() {
        View rootView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        snackbar = Snackbar.make(rootView, "You have recently reserved this item, give us a review", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Review", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("itemName", _service.getName());
                args.putLong("itemId", _serviceId);
                args.putLong("eoId", 224L);
                Navigation.findNavController(getView()).navigate(R.id.action_to_item_review_form, args);
            }
        });
        snackbar.show();


    }

    private void checkIfBought() {
        Call<IsBoughtItemResponse> call = ClientUtils.itemsService.checkIfBought(_serviceId, 224L);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<IsBoughtItemResponse> call, Response<IsBoughtItemResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isBought)
                        showSnackBar();
                }
            }

            @Override
            public void onFailure(Call<IsBoughtItemResponse> call, Throwable t) {

            }
        });
    }

    private void initializeGradesAccordion() {
        if (_service.getGrades().size() == 0) {
            _binding.expandForGrades.setVisibility(View.GONE);
            return;
        }
        _adapter = new ItemReviewsAdapter(_service.getGrades());
        _binding.gradesView.setAdapter(_adapter);
        _binding.expandForGrades.setOnClickListener(v -> {
            accordionOpen = !accordionOpen;
            _binding.gradesScrollView.setVisibility((accordionOpen) ? View.VISIBLE : View.GONE);
        });
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
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}