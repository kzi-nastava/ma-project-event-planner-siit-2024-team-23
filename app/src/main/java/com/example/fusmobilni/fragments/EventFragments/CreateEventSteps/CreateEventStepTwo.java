package com.example.fusmobilni.fragments.EventFragments.CreateEventSteps;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.BudgetPlaningItemAdapter;
import com.example.fusmobilni.databinding.FragmentCreateEventStepTwoBinding;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.model.EventType;
import com.example.fusmobilni.model.OfferingsCategory;
import com.example.fusmobilni.viewModels.EventViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class CreateEventStepTwo extends Fragment  implements FragmentValidation {
    private FragmentCreateEventStepTwoBinding _binding;
    private EventViewModel _eventViewModel;
    private BudgetPlaningItemAdapter _budgetPlaningAdapter;
    private final List<OfferingsCategory> _suggestedCategoryOfferings = new ArrayList<>();
    private final ArrayList<OfferingsCategory> _allcategories = new ArrayList<>();

    public CreateEventStepTwo() {
        // Required empty public constructor
    }
    public static CreateEventStepTwo newInstance() {
        return new CreateEventStepTwo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentCreateEventStepTwoBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();
        MaterialButton addCategoryButton = _binding.addButton;
        addCategoryButton.setOnClickListener(v -> showCategorySelectionDialog());
        return view;
    }

    private void showCategorySelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Category");

        String[] categoryNames = _allcategories.stream()
                .map(OfferingsCategory::getName)
                .toArray(String[]::new);

        builder.setItems(categoryNames, (dialog, which) -> {
            OfferingsCategory selectedCategory = _allcategories.get(which);
            _budgetPlaningAdapter.addCategory(selectedCategory);
        });

        builder.show();
    }

    @Override
    public void onResume(){
        super.onResume();
        _eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        RecyclerView recyclerView = _binding.eventsRecycleView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        _suggestedCategoryOfferings.clear();
        _allcategories.clear();
        populateData();
        populateCategories();
        this._budgetPlaningAdapter = new BudgetPlaningItemAdapter(requireContext(), this._suggestedCategoryOfferings, (category, price) -> {
            Bundle bundle = new Bundle();
            bundle.putDouble("price", price);
            bundle.putInt("category", category.getId());
            Navigation.findNavController(_binding.getRoot()).navigate(R.id.action_stepTwoFragment_to_viewProductsFragment);
        });
        recyclerView.setAdapter(_budgetPlaningAdapter);
    }


    private void populateCategories() {
        _allcategories.add(new OfferingsCategory(1, "Sport", "Sport je jako zanimljiv i zabavan"));
        _allcategories.add(new OfferingsCategory(2, "Food", "Sport je jako zanimljiv i zabavan"));
        _allcategories.add(new OfferingsCategory(3, "Slavlje", "Sport je jako zanimljiv i zabavan"));
        _allcategories.add(new OfferingsCategory(4, "Hronologija", "Sport je jako zanimljiv i zabavan"));
        _allcategories.add(new OfferingsCategory(5, "Jelo", "Sport je jako zanimljiv i zabavan"));
    }

    private void populateData() {
        EventType eventType = _eventViewModel.getEventType().getValue();
        if (eventType != null && eventType.getSuggestedCategories() != null) {
            _suggestedCategoryOfferings.addAll(eventType.getSuggestedCategories());
        }
    }

    @Override
    public boolean validate() {
        return true;
    }
}