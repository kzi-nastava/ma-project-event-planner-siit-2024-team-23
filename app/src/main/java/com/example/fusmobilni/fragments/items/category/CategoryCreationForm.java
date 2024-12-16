package com.example.fusmobilni.fragments.items.category;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentCategoryCreationFormBinding;
import com.example.fusmobilni.viewModels.items.category.CategoryViewModel;


public class CategoryCreationForm extends Fragment {

    private FragmentCategoryCreationFormBinding binding;
    private CategoryViewModel viewModel;

    public CategoryCreationForm() {
    }

    public static CategoryCreationForm newInstance(String param1, String param2) {
        return new CategoryCreationForm();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryCreationFormBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);

        populate();
        if(Boolean.TRUE.equals(viewModel.getIsUpdating().getValue())) {
            binding.categoryTitle.setText(R.string.update_category);
        }

        binding.cancelButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.creationForm_toCategories);
        });

        binding.submitButton.setOnClickListener(v -> {
            setValues();
            if (validate()) {
                viewModel.submit();
                Navigation.findNavController(view).navigate(R.id.creationForm_toCategories);
            }
        });

        return view;
    }

    private void populate() {
        viewModel.getName().observe(getViewLifecycleOwner(), name -> {
            binding.nameInput.setText(String.valueOf(name));
        });

        viewModel.getDescription().observe(getViewLifecycleOwner(), description -> {
            binding.descriptionInput.setText(String.valueOf(description));
        });
    }

    private void setValues() {
        viewModel.setName(String.valueOf(binding.nameInput.getText()));
        viewModel.setDescription(String.valueOf(binding.descriptionInput.getText()));
    }

    private boolean validate() {
        if(viewModel.getName().getValue().isEmpty()) {
            binding.nameInputLayout.setError("Name is required");
            binding.nameInputLayout.setErrorEnabled(true);
            return false;
        }
        if (viewModel.getDescription().getValue().isEmpty()) {
            binding.descriptionInputLayout.setErrorEnabled(true);
            binding.descriptionInputLayout.setError("Description in enabled");
            return false;
        }
        return true;
    }

}