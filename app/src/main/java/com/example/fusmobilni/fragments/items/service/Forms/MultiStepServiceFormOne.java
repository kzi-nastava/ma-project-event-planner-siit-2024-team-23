package com.example.fusmobilni.fragments.items.service.Forms;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.fusmobilni.R;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentMultiStepServiceFormOneBinding;
import com.example.fusmobilni.requests.categories.GetCategoriesResponse;
import com.example.fusmobilni.requests.eventTypes.GetEventTypesResponse;
import com.example.fusmobilni.viewModels.items.service.ServiceViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
                ArrayList<String> categoryNames = categories.categories.stream()
                        .map(category -> category.name)
                        .collect(Collectors.toCollection(ArrayList::new));
                categoryNames.add("Custom");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categoryNames);
                binding.autoCompleteTextviewStep1.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GetCategoriesResponse> call, Throwable t) {

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

        binding.eventTypesInputStep1.setOnClickListener(v -> showMultiSelectDialog());
        populateInputs();

        viewModel.getIsUpdating().observe(getViewLifecycleOwner(), isUpdating -> {
            if (!isUpdating)
                binding.dropdownMenuStep1.setVisibility(View.VISIBLE);
        });

        binding.autoCompleteTextviewStep1.setOnItemClickListener((parent, v, position, id) -> {
            String selectedCategory = (String) parent.getItemAtPosition(position);
            viewModel.setCategory(selectedCategory);
            if (position < categories.categories.size()) {
                viewModel.setCategoryId(categories.categories.get(position).id);
            }
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
            if (validate()) {
                Navigation.findNavController(view).navigate(R.id.action_serviceCreationStepOne_toServiceCreationStepTwo);
            }
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
        boolean[] checkedItems = new boolean[eventTypes.eventTypes.size()];
        List<String> selectedEventTypes = new ArrayList<>(viewModel.getEventTypes().getValue());
        ArrayList<Long> eventTypeIds = new ArrayList<>();

        for (int i = 0; i < eventTypes.eventTypes.size(); i++) {
            checkedItems[i] = selectedEventTypes.contains(eventTypes.eventTypes.get(i).name);
        }

        String[] eventTypeNames = eventTypes.eventTypes.stream()
                .map(eventType -> eventType.name)
                .toArray(String[]::new);

        new AlertDialog.Builder(requireContext())
                .setTitle("Select Event Types")
                .setMultiChoiceItems(eventTypeNames, checkedItems, (dialog, which, isChecked) -> {
                    if (isChecked) {
                        selectedEventTypes.add(eventTypeNames[which]);
                    } else {
                        selectedEventTypes.remove(eventTypeNames[which]);
                    }
                })
                .setPositiveButton("OK", (dialog, which) -> {
                    viewModel.setEventTypes(selectedEventTypes);
                    for(int i = 0; i < checkedItems.length; i++) {
                        if(checkedItems[i]) {
                            eventTypeIds.add(eventTypes.eventTypes.get(i).id);
                        }
                    }
                    String concatEventTypeIds = String.join(",", eventTypeIds.stream()
                            .map(String::valueOf)
                            .toArray(String[]::new));
                    viewModel.setEventTypeIds(concatEventTypeIds);
                    binding.eventTypesInputStep1.setText(String.join(", ", selectedEventTypes));
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private boolean validate() {
        if(viewModel.getPrice().getValue() == null || viewModel.getPrice().getValue() <= 0) {
            binding.priceFieldStep1.setError("price must be greater than 0");
            binding.priceFieldStep1.setErrorEnabled(true);
            return false;
        }
        if(viewModel.getDiscount().getValue() == null ||
                viewModel.getDiscount().getValue() < 0 || viewModel.getDiscount().getValue() > 100) {
            binding.discountFieldStep1.setError("discount must be greater than 0 and less than 100");
            binding.discountFieldStep1.setErrorEnabled(true);
            return false;
        }
        if (viewModel.getName().getValue().isEmpty()) {
            binding.textFieldStep1.setErrorEnabled(true);
            binding.textFieldStep1.setError("Name is required");
            return false;
        }
        if (viewModel.getDescription().getValue().isEmpty()) {
            binding.descriptionFieldStep1.setErrorEnabled(true);
            binding.descriptionFieldStep1.setError("Description is required");
            return false;
        }
        if (viewModel.getCategory().getValue().isEmpty() && !viewModel.getIsUpdating().getValue()) {
            binding.dropdownMenuStep1.setError("Category is required");
            binding.dropdownMenuStep1.setErrorEnabled(true);
            return false;
        }
        if(viewModel.getEventTypes().getValue().isEmpty()) {
            binding.eventTypesFieldStep1.setErrorEnabled(true);
            binding.eventTypesFieldStep1.setError("Event type is reuired");
            return false;
        }

        if (Objects.equals(viewModel.getCategory().getValue(), "Custom")) {
            if (viewModel.getCustomCategoryName().getValue().isEmpty()) {
                binding.customCategoryNameLabel.setErrorEnabled(true);
                binding.customCategoryNameLabel.setError("Required");
                return false;
            }
            if (viewModel.getCustomCategoryDescription().getValue().isEmpty()) {
                binding.customCategoryDescriptionLabel.setErrorEnabled(true);
                binding.customCategoryDescriptionLabel.setError("Rqeuired");
                return false;
            }
        }
        return true;
    }
}