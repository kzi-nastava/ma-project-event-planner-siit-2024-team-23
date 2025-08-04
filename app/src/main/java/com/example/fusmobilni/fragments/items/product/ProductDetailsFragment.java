package com.example.fusmobilni.fragments.items.product;

import android.content.ClipData;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentProductDetailsBinding;
import com.example.fusmobilni.model.items.item.ItemDetails;
import com.example.fusmobilni.model.items.product.Product;
import com.example.fusmobilni.requests.communication.chat.ChatCreateRequest;
import com.example.fusmobilni.requests.items.BuyItemRequest;
import com.example.fusmobilni.responses.auth.LoginResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailsFragment extends Fragment {

    private FragmentProductDetailsBinding _binding;
    private Long productId;
    private Long eventId;
    private double estimatedBudget;
    private ItemDetails product;

    private boolean favorite = false;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    public static ProductDetailsFragment newInstance(String param1, String param2) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
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
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false);
        View view = _binding.getRoot();

        productId = getArguments().getLong("productId");
        eventId = getArguments().getLong("eventId");
        estimatedBudget = getArguments().getDouble("estimatedBudget");
        Call<ItemDetails> request = ClientUtils.itemsService.findById(productId);
        request.enqueue(new Callback<ItemDetails>() {
            @Override
            public void onResponse(Call<ItemDetails> call, Response<ItemDetails> response) {
                product = response.body();
                _binding.productDetailsText.setText(product.getName());
                _binding.productsHorizontalPrice.setText(String.valueOf(product.getPrice()));
                _binding.textViewOrganizerNameProductDetails.setText(product.getServiceProvider().getLastName());
                _binding.textViewProductDescriptionDetails.setText(product.getDescription());
                try {
                    _binding.imageView4.setImageURI(convertToUrisFromBase64(getContext(), product.getImages().get(0)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                _binding.imageView5.setImageResource(R.drawable.person);
                initializeUserProfileActions();
            }

            @Override
            public void onFailure(Call<ItemDetails> call, Throwable t) {

            }

        });

        _binding.button2.setOnClickListener(v -> createChat());
        _binding.textViewOrganizerNameProductDetails.setOnClickListener(v -> visitCompany());

        _binding.buyProductButton.setOnClickListener(v->{
            BuyItemRequest buyItemRequest = new BuyItemRequest(productId, estimatedBudget, "", "", eventId);
            Call<Void> req = ClientUtils.eventsService.createEventComponent(eventId, buyItemRequest);
            req.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(requireContext(), "Product bought successfully", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putInt("currFragment", 1);
                        bundle.putLong("eventId", eventId);
                        Navigation.findNavController(_binding.getRoot()).navigate(R.id.action_viewProductsFragment_to_stepTwoFragment, bundle);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        });

        initializeFavoriteButton();
        return view;
    }
    private void initializeUserProfileActions() {
        _binding.imageView5.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_toForeignUserProfile, createUserProfileBundle());
        });
    }

    private Bundle createUserProfileBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong("userId", product.getServiceProvider().getId());
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

    public static Uri convertToUrisFromBase64(Context context, String base64String) throws IOException {


        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);

        File tempFile = File.createTempFile("image_", ".jpg", context.getCacheDir());
        tempFile.deleteOnExit();

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(decodedBytes);
        }

        return Uri.fromFile(tempFile);
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
        ChatCreateRequest request = new ChatCreateRequest(userId, product.getServiceProvider().getId());
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
        bundle.putLong("spId", product.getServiceProvider().getId());
        Navigation.findNavController(_binding.getRoot()).navigate(R.id.action_toCompanyOverview, bundle);
    }
}