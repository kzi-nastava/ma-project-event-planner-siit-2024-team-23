package com.example.fusmobilni.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentCategoryCreationFormBinding;
import com.example.fusmobilni.databinding.FragmentModifyCategoryProposalBinding;
import com.example.fusmobilni.viewModels.CategoryProposalViewModel;
import com.example.fusmobilni.viewModels.CategoryViewModel;


public class ModifyCategoryProposalFragment extends Fragment {

    private FragmentModifyCategoryProposalBinding binding;
    private CategoryProposalViewModel viewModel;

    public ModifyCategoryProposalFragment() {}


    public static ModifyCategoryProposalFragment newInstance(String param1, String param2) {
        return new ModifyCategoryProposalFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentModifyCategoryProposalBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(CategoryProposalViewModel.class);

        populate();

        binding.cancelButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.categoryModificationForm_toCategoryProposals);
        });

        binding.submitButton.setOnClickListener(v -> {
            setValues();
            //create and clean up the viewModel
            Navigation.findNavController(view).navigate(R.id.categoryModificationForm_toCategoryProposals);
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
}