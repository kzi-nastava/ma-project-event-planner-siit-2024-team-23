package com.example.fusmobilni.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentProductDetailsBinding;
import com.example.fusmobilni.model.Product;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailsFragment extends Fragment {

    private FragmentProductDetailsBinding _binding;

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

        Product product = getArguments().getParcelable("product");
        Boolean displayPurchase = getArguments().getBoolean("displayPurchase");

        if (displayPurchase == false) {
            _binding.purchaseProductCard.setVisibility(View.INVISIBLE);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) _binding.productDetailsText.getLayoutParams();
            params.setMargins(0, 32, 0, 16);
        }
        _binding.productDetailsText.setText(product.getName());
        _binding.productsHorizontalPrice.setText(String.valueOf(product.getPrice()));
        _binding.textViewProductLocationHorizontal.setText(product.getLocation());
        _binding.textViewOrganizerNameProductDetails.setText("Ibrahimovic");
        _binding.textViewProductDescriptionDetails.setText(product.getDescription());
        _binding.imageView5.setImageResource(R.drawable.person);
        _binding.buyProductButton.setOnClickListener(v->{
            Toast.makeText(requireContext(), "Product bought successfully", Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putInt("currFragment", 1);
            Navigation.findNavController(_binding.getRoot()).navigate(R.id.action_viewProductsFragment_to_stepTwoFragment, bundle);
        });

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