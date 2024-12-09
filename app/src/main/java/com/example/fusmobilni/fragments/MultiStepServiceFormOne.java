package com.example.fusmobilni.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.fusmobilni.R;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentMultiStepServiceFormOneBinding;
import com.example.fusmobilni.requests.categories.GetCategoriesResponse;
import com.example.fusmobilni.requests.categories.GetCategoryResponse;
import com.example.fusmobilni.requests.eventTypes.GetEventTypesResponse;
import com.example.fusmobilni.viewModels.ServiceViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MultiStepServiceFormOne extends Fragment {


    private FragmentMultiStepServiceFormOneBinding binding;
    private ServiceViewModel viewModel;

    private GetCategoriesResponse categories;
    private GetEventTypesResponse eventTypes;

    public MultiStepServiceFormOne() {
    }

    public static MultiStepServiceFormOne newInstance() {
        return new MultiStepServiceFormOne();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMultiStepServiceFormOneBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);
        View view = binding.getRoot();

        Call<GetCategoriesResponse> categoriesCall = ClientUtils.categoryService.findAll();
        categoriesCall.enqueue(new Callback<GetCategoriesResponse>() {
            @Override
            public void onResponse(Call<GetCategoriesResponse> call, Response<GetCategoriesResponse> response) {
                categories = response.body();
                for(GetCategoryResponse category: categories.categories) {
                    Log.d("tag", category.name);
                }
            }

            @Override
            public void onFailure(Call<GetCategoriesResponse> call, Throwable t) {
                Log.d("tag", t.getMessage());
            }
        });

        Call<GetEventTypesResponse> eventTypesCall = ClientUtils.eventTypeService.findAll();
        eventTypesCall.enqueue(new Callback<GetEventTypesResponse>() {
            @Override
            public void onResponse(Call<GetEventTypesResponse> call, Response<GetEventTypesResponse> response) {
                eventTypes = response.body();
            }

            @Override
            public void onFailure(Call<GetEventTypesResponse> call, Throwable t) {

            }
        });

        String[] categories = getResources().getStringArray(R.array.categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories);
        binding.autoCompleteTextviewStep1.setAdapter(adapter);

        binding.eventTypesInputStep1.setOnClickListener(v -> showMultiSelectDialog());
        populateInputs();

        viewModel.getIsUpdating().observe(getViewLifecycleOwner(), isUpdating -> {
            if (!isUpdating)
                binding.dropdownMenuStep1.setVisibility(View.VISIBLE);
        });

        binding.autoCompleteTextviewStep1.setOnItemClickListener((parent, v, position, id) -> {
            String selectedCategory = (String) parent.getItemAtPosition(position);
            viewModel.setCategory(selectedCategory);

            if (Objects.equals(selectedCategory, "Custom")) {
                binding.customCategoryNameLabel.setVisibility(View.VISIBLE);
                binding.customCategoryDescriptionLabel.setVisibility(View.VISIBLE);
            } else {
                binding.customCategoryNameLabel.setVisibility(View.INVISIBLE);
                binding.customCategoryDescriptionLabel.setVisibility(View.INVISIBLE);
            }
        });


        binding.materialButton.setOnClickListener(v -> {
            setValues();
            Navigation.findNavController(view).navigate(R.id.action_serviceCreationStepOne_toServiceCreationStepTwo);
        });

        if (viewModel.getIsUpdating().getValue()) {
            binding.textView2.setText("Update Service Form");
        }

        return view;
    }

    private void setValues() {
        viewModel.setName(String.valueOf(binding.serviceName.getText()));
        viewModel.setDescription(String.valueOf(binding.descriptionText.getText()));
        viewModel.setPrice(Double.valueOf(String.valueOf(binding.priceText.getText())));
        viewModel.setDiscount(Double.valueOf(String.valueOf(binding.discountText.getText())));
        viewModel.setCustomCategoryName(String.valueOf(binding.customCategoryName.getText()));
        viewModel.setCustomCategoryDescription(String.valueOf(binding.customCategoryDescription.getText()));
    }


    private void populateInputs() {
        viewModel.getCategory().observe(getViewLifecycleOwner(), category -> {
            binding.autoCompleteTextviewStep1.setText(category, false);
        });

        viewModel.getEventTypes().observe(getViewLifecycleOwner(), eventTypes -> {
            binding.eventTypesInputStep1.setText(String.join(", ", eventTypes));
        });

        viewModel.getName().observe(getViewLifecycleOwner(), name -> {
            binding.serviceName.setText(name);
        });

        viewModel.getDescription().observe(getViewLifecycleOwner(), description -> {
            binding.descriptionText.setText(description);
        });

        viewModel.getPrice().observe(getViewLifecycleOwner(), price -> {
            binding.priceText.setText(String.format(String.valueOf(price)));
        });

        viewModel.getDiscount().observe(getViewLifecycleOwner(), discount -> {
            binding.discountText.setText(String.format(String.valueOf(discount)));
        });
    }

    private void showMultiSelectDialog() {
        String[] eventTypes = getResources().getStringArray(R.array.EventTypes);
        boolean[] checkedItems = new boolean[eventTypes.length];
        List<String> selectedEventTypes = new ArrayList<>(viewModel.getEventTypes().getValue());

        for (int i = 0; i < eventTypes.length; i++) {
            checkedItems[i] = selectedEventTypes.contains(eventTypes[i]);
        }

        new AlertDialog.Builder(requireContext())
                .setTitle("Select Event Types")
                .setMultiChoiceItems(eventTypes, checkedItems, (dialog, which, isChecked) -> {
                    if (isChecked) {
                        selectedEventTypes.add(eventTypes[which]);
                    } else {
                        selectedEventTypes.remove(eventTypes[which]);
                    }
                })
                .setPositiveButton("OK", (dialog, which) -> {
                    viewModel.setEventTypes(selectedEventTypes);
                    binding.eventTypesInputStep1.setText(String.join(", ", selectedEventTypes));
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}