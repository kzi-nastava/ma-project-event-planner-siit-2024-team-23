package com.example.fusmobilni.fragments.items.pricelist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentCategoryCreationFormBinding;
import com.example.fusmobilni.databinding.FragmentEditPriceListItemBinding;
import com.example.fusmobilni.viewModels.items.category.CategoryViewModel;
import com.example.fusmobilni.viewModels.items.pricelist.PriceListViewModel;

public class EditPriceListItemFragment extends Fragment {

    FragmentEditPriceListItemBinding binding;
    PriceListViewModel viewModel;

    public EditPriceListItemFragment() {}


    public static EditPriceListItemFragment newInstance() {

        return new EditPriceListItemFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditPriceListItemBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(PriceListViewModel.class);

        populate();
        setUpButtons(view);

        return view;
    }
    private void populate() {
        viewModel.getPrice().observe(getViewLifecycleOwner(), price -> {
            binding.priceInput.setText(String.valueOf(price));
        });

        viewModel.getDiscount().observe(getViewLifecycleOwner(), discount -> {
            binding.discountInput.setText(String.valueOf(discount));
        });
    }

    private void setUpButtons(View view) {
        binding.cancelButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_priceListEdit_toPriceList);
        });

        binding.submitButton.setOnClickListener(v -> {
            setValues();
            if (validate()) {
                viewModel.submit(getContext());
                Navigation.findNavController(view).navigate(R.id.action_priceListEdit_toPriceList);
            }
        });
    }

    private void setValues() {
        viewModel.setPrice(Double.valueOf(String.valueOf(binding.priceInput.getText())));
        viewModel.setDiscount(Double.valueOf(String.valueOf(binding.discountInput.getText())));
    }

    private boolean validate() {
        if(viewModel.getPrice().getValue() == null || viewModel.getPrice().getValue() <= 0) {
            binding.priceInputLayout.setError("price must be greater than 0");
            binding.priceInputLayout.setErrorEnabled(true);
            return false;
        }
        if(viewModel.getDiscount().getValue() == null ||
                viewModel.getDiscount().getValue() < 0 || viewModel.getDiscount().getValue() > 100) {
            binding.discountInputLayout.setError("discount must be greater than 0 and less than 100");
            binding.discountInputLayout.setErrorEnabled(true);
            return false;
        }
        return true;
    }
}