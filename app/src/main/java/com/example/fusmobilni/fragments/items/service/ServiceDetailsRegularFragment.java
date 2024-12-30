package com.example.fusmobilni.fragments.items.service;

import static com.example.fusmobilni.adapters.AdapterUtils.convertToUrisFromBase64;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.items.reviews.ItemReviewsAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentServiceDetailsRegularBinding;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.requests.communication.chat.ChatCreateRequest;
import com.example.fusmobilni.requests.products.GetProductResponse;
import com.example.fusmobilni.requests.users.favorites.FavoriteEventRequest;
import com.example.fusmobilni.requests.users.favorites.FavoriteProductRequest;
import com.example.fusmobilni.requests.users.favorites.FavoriteServiceRequest;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.items.IsBoughtItemResponse;
import com.example.fusmobilni.responses.items.products.ProductOverviewResponse;
import com.example.fusmobilni.responses.items.services.ServiceOverviewResponse;
import com.example.fusmobilni.responses.location.LocationResponse;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceDetailsRegularFragment extends Fragment {
    Snackbar snackbar;
    private boolean favorite = false;
    private Long itemId;
    private boolean isService;
    private ServiceOverviewResponse _service;
    private ProductOverviewResponse _product;
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
        initializeDialogs();
        if(getArguments() != null){
            long serviceId = getArguments().getLong("serviceId", -1);
            long productId = getArguments().getLong("productId", -1);
            if(serviceId != -1){
                itemId = serviceId;
                isService = true;
                getServiceForOverview();
            }else{
                itemId = productId;
                isService = false;
                getServiceForOverview();
            }
        }


        _binding.button2.setOnClickListener(v -> createChat());
        _binding.textViewOrganizerNameServiceDetails.setOnClickListener(v -> visitCompany());
        return root;
    }

    private void initializeDialogs() {
        _loader = new SpinnerDialogFragment();
        _loader.setCancelable(false);

    }

    private void initializePageSuccessful() {
        if(isService){
            _binding.serviceDetailsText.setText(_service.getName());
            LocationResponse location = _service.getProvider().getCompanyLocation();
            _binding.textViewServiceLocationHorizontal.setText(location.getCity() + ", " + location.getStreet() + " " + location.getStreetNumber());
            _binding.textViewServiceCategory.setText(_service.getCategory().getName());
            _binding.textViewOrganizerNameServiceDetails.setText(_service.getProvider().getFirstName() + " " + _service.getProvider().getLastName());
            _binding.textViewServiceDescriptionDetails.setText(_service.getDescription());
            _binding.price.setText(String.valueOf(_service.getPrice()));
            if (_service.getProvider().getImage() != null) {
                try {
                    _binding.imageView5.setImageURI(convertToUrisFromBase64(getContext(), _service.getProvider().getImage()));
                } catch (IOException e) {

                }
            }
            try {
                _binding.imageView4.setImageURI(convertToUrisFromBase64(getContext(), _service.getImage()));
            } catch (IOException e) {

            }
        }else{
            _binding.serviceDetailsText.setText(_product.getName());
            LocationResponse location = _product.getProvider().getCompanyLocation();
            _binding.textViewServiceLocationHorizontal.setText(location.getCity() + ", " + location.getStreet() + " " + location.getStreetNumber());
            _binding.textViewServiceCategory.setText(_product.getCategory().getName());
            _binding.textViewOrganizerNameServiceDetails.setText(_product.getProvider().getFirstName() + " " + _product.getProvider().getLastName());
            _binding.textViewServiceDescriptionDetails.setText(_product.getDescription());
            _binding.price.setText(String.valueOf(_product.getPrice()));
            if (_product.getProvider().getImage() != null) {
                try {
                    _binding.imageView5.setImageURI(convertToUrisFromBase64(getContext(), _product.getProvider().getImage()));
                } catch (IOException e) {

                }
            }
            try {
                _binding.imageView4.setImageURI(convertToUrisFromBase64(requireContext(), _product.getImage().get(0)));
            } catch (IOException e) {

            }
        }


        initializeGradesAccordion();

        initializeFavoriteButton();

        checkIfBought();
    }

    private void showSnackBar() {
        if (getUserId() == null) {
            return;
        }
        View rootView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        snackbar = Snackbar.make(rootView, "You have recently reserved this item, give us a review", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Review", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                if(isService){
                    args.putString("itemName", _service.getName());
                    args.putLong("itemId", _service.getId());
                }else{
                    args.putString("itemName", _product.getName());
                    args.putLong("itemId", _product.getId());
                }
                

                args.putLong("eoId", getUserId());

                Navigation.findNavController(getView()).navigate(R.id.action_to_item_review_form, args);
            }
        });
        snackbar.show();


    }

    private void checkIfBought() {
        Call<IsBoughtItemResponse> call = ClientUtils.itemsService.checkIfBought(itemId, 224L);
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
        if (isService && _service.getGrades().isEmpty()) {
            _binding.expandForGrades.setVisibility(View.GONE);
            return;
        }
        if(!isService && _product.getGrades().isEmpty()){
            _binding.expandForGrades.setVisibility(View.GONE);
            return;
        }

        _adapter = new ItemReviewsAdapter(isService ? _service.getGrades() : _product.getGrades());
        _binding.gradesView.setAdapter(_adapter);
        _binding.expandForGrades.setOnClickListener(v -> {
            accordionOpen = !accordionOpen;
            _binding.gradesScrollView.setVisibility((accordionOpen) ? View.VISIBLE : View.GONE);
        });
    }

    private void initializeFavoriteButton() {
        _binding.favoriteButton.setOnClickListener(v -> {
            Long userId = getUserId();
            if (userId == null) {
                Toast.makeText(v.getContext(), "You must be logged in first!", Toast.LENGTH_SHORT).show();
                return;
            }
            Call<Void> request = isService
                    ? ClientUtils.userService.addToServiceFavorites(
                    userId, new FavoriteServiceRequest(_service.getId(), userId)
            )
                    : ClientUtils.userService.addToProductFavorites(
                    userId, new FavoriteProductRequest(_product.getId(), userId)
            );
            request.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(v.getContext(), "Success!", Toast.LENGTH_SHORT).show();
                        favorite = !favorite;
                        animateFavoriteButton();
                    }else{
                        Toast.makeText(v.getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void animateFavoriteButton() {
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
    }

    private void getServiceForOverview() {
        _loader.show(requireActivity().getSupportFragmentManager(), "loading_spinner");
        if(isService){
            Call<ServiceOverviewResponse> call = ClientUtils.serviceOfferingService.findServiceForOverview(itemId);
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
        }else{
            Call<ProductOverviewResponse> call = ClientUtils.productsService.findOverviewById(itemId);
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<ProductOverviewResponse> call, @NonNull Response<ProductOverviewResponse> response) {
                    if (!response.isSuccessful()) {
                        return;
                    }
                    _product = response.body();
                    _loader.dismiss();
                    initializePageSuccessful();
                }
                @Override
                public void onFailure(@NonNull Call<ProductOverviewResponse> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }


    }

    private Long getUserId() {
        LoginResponse user = CustomSharedPrefs.getInstance(getContext()).getUser();
        if (user == null)
            return null;
        return user.getId();
    }

    private void createChat() {
        Long userId = getUserId();
        if (userId == null) {
            return;
        }
        ChatCreateRequest request = new ChatCreateRequest(userId,isService ? _service.getProvider().getId() : _product.getProvider().getId());
        ClientUtils.chatService.create(userId, request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Navigation.findNavController(_binding.getRoot()).navigate(R.id.action_toChatsFragment);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void visitCompany() {
        Bundle bundle = new Bundle();
        bundle.putLong("spId", isService ? _service.getProvider().getId() : _product.getProvider().getId());
        Navigation.findNavController(_binding.getRoot()).navigate(R.id.action_toCompanyOverview, bundle);
    }
}