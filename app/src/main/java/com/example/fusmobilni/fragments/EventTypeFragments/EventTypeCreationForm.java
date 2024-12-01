package com.example.fusmobilni.fragments.EventTypeFragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentEventTypeCreationFormBinding;
import com.example.fusmobilni.model.OfferingsCategory;
import com.example.fusmobilni.viewModels.EventTypeViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class EventTypeCreationForm extends Fragment {
    private FragmentEventTypeCreationFormBinding _binding;
    private ArrayList<OfferingsCategory> _offeringCategory = new ArrayList<>();
    private ChipGroup _selectedCategoriesChipGroup;
    private List<OfferingsCategory> _selectedCategories = new ArrayList<>();
    private EventTypeViewModel _eventTypeViewModel;

    public EventTypeCreationForm() {
        // Required empty public constructor
    }
    public static EventTypeCreationForm newInstance(String param1, String param2) {
        return new EventTypeCreationForm();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _binding = FragmentEventTypeCreationFormBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();
        _eventTypeViewModel = new ViewModelProvider(requireActivity()).get(EventTypeViewModel.class);
        populateData();
        populateInputs();
        if(Boolean.TRUE.equals(_eventTypeViewModel.getIsUpdating().getValue())) {
            _binding.eventTitle.setText(R.string.update_event_type);
        }

        _selectedCategoriesChipGroup = _binding.selectedCategoriesChipGroup;
        MaterialButton addCategoryButton = _binding.addCategoryButton;

        addCategoryButton.setOnClickListener(v -> showCategorySelectionDialog());

        _binding.cancelButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.eventTypeCreation_to_eventType);
        });

        _binding.submitButton.setOnClickListener(v -> {
            setValues();
            //create and clean up the viewModel
            Navigation.findNavController(view).navigate(R.id.eventTypeCreation_to_eventType);
        });

        return view;
    }

    private void populateInputs() {
            _eventTypeViewModel.getName().observe(getViewLifecycleOwner(), name -> {
                _binding.nameInput.setText(String.valueOf(name));
            });
            _eventTypeViewModel.getDescription().observe(getViewLifecycleOwner(), description -> {
                _binding.descriptionInput.setText(String.valueOf(description));
            });
            _eventTypeViewModel.getSuggestedCategories().observe(getViewLifecycleOwner(), suggestedCategories -> {
                //TODO
            });
    }

    private void setValues() {
        _eventTypeViewModel.setName(String.valueOf(_binding.nameInput.getText()));
        _eventTypeViewModel.setDescription(String.valueOf(_binding.descriptionInput.getText()));
        _eventTypeViewModel.setSuggestedCategories(_selectedCategories);
    }

    private void populateData(){
        _offeringCategory.add(new OfferingsCategory(1, "Sport", "Sport je jako zanimljiv i zabavan"));
        _offeringCategory.add(new OfferingsCategory(2, "Food", "Sport je jako zanimljiv i zabavan"));
        _offeringCategory.add(new OfferingsCategory(3, "Slavlje", "Sport je jako zanimljiv i zabavan"));
        _offeringCategory.add(new OfferingsCategory(4, "Hronologija", "Sport je jako zanimljiv i zabavan"));
        _offeringCategory.add(new OfferingsCategory(5, "Jelo", "Sport je jako zanimljiv i zabavan"));
    }
    private void showCategorySelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Category");

        String[] categoryNames = _offeringCategory.stream()
                .map(OfferingsCategory::getName)
                .toArray(String[]::new);

        builder.setItems(categoryNames, (dialog, which) -> {
            OfferingsCategory selectedCategory = _offeringCategory.get(which);
            addCategoryChip(selectedCategory);
        });

        builder.show();
    }

    private void addCategoryChip(OfferingsCategory category) {
        if (!_selectedCategories.contains(category)) {
            _selectedCategories.add(category);
            Chip chip = new Chip(getContext());
            chip.setText(category.getName());
            chip.setChipBackgroundColorResource(R.color.white);
            chip.setCloseIconVisible(true);
            chip.setOnCloseIconClickListener(v -> _selectedCategoriesChipGroup.removeView(chip));

            chip.setOnCloseIconClickListener(v -> {
                _selectedCategories.remove(category); // Remove from tracking list
                _selectedCategoriesChipGroup.removeView(chip); // Remove chip from ChipGroup
            });

            _selectedCategoriesChipGroup.addView(chip);
        }else{
            Toast.makeText(getContext(), "Category already added", Toast.LENGTH_SHORT).show();
        }
    }
}