package com.example.fusmobilni.fragments;



import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.PupServiceAdapter;
import com.example.fusmobilni.databinding.FragmentServiceViewBinding;
import com.example.fusmobilni.interfaces.DeleteServiceListener;
import com.example.fusmobilni.model.PrototypeService;
import com.example.fusmobilni.viewModels.ServiceViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ServiceView extends Fragment implements DeleteServiceListener {

    FragmentServiceViewBinding binding;
    private PupServiceAdapter serviceAdapter;
    private View deleteModal;
    private final List<PrototypeService> services = new ArrayList<>();

    public ServiceView() {
    }

    public static ServiceView newInstance() {
        return new ServiceView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentServiceViewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        requireActivity().getViewModelStore().clear();
        addDummyData();
        setUpAdapter();

        binding.floatingActionButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_serviceView_toServiceCreationStepOne);
        });
        setUpSearch();
        binding.filterBtn.setOnClickListener(v -> showFilterDialog());

        return view;
    }

    private void showFilterDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireActivity(), R.style.FullScreenBottomSheetDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.service_filter, null);

        setupDropdowns(dialogView);
        setupPriceRangeSlider(dialogView);

        bottomSheetDialog.setContentView(dialogView);
        bottomSheetDialog.show();

        setupDialogButtons(dialogView, bottomSheetDialog);
    }

    private void setupDropdowns(View dialogView) {
        AutoCompleteTextView categoryDropdown = dialogView.findViewById(R.id.text_services_category);
        AutoCompleteTextView eventTypeDropdown = dialogView.findViewById(R.id.text_event_type);

        String[] categories = getResources().getStringArray(R.array.categories);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories);
        categoryDropdown.setAdapter(categoryAdapter);

        String[] eventTypes = getResources().getStringArray(R.array.EventTypes);
        ArrayAdapter<String> eventTypeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, eventTypes);
        eventTypeDropdown.setAdapter(eventTypeAdapter);
    }

    @SuppressLint("DefaultLocale")
    private void setupPriceRangeSlider(View dialogView) {
        RangeSlider priceRangeSlider = dialogView.findViewById(R.id.range_slider_price);
        priceRangeSlider.setValueFrom(0f);
        priceRangeSlider.setValueTo(1000f);
        priceRangeSlider.setStepSize(1f);
        priceRangeSlider.setValues(100f, 500f);
        priceRangeSlider.setLabelFormatter(value -> "$" + String.format("%.0f", value));
    }

    private void setupDialogButtons(View dialogView, BottomSheetDialog bottomSheetDialog) {
        Button cancelBtn = dialogView.findViewById(R.id.cancelButton);
        Button applyBtn = dialogView.findViewById(R.id.applyButton);

        cancelBtn.setOnClickListener(v -> bottomSheetDialog.cancel());

        applyBtn.setOnClickListener(v -> {
            List<PrototypeService> filteredServicesList = filterServices(dialogView);
            serviceAdapter.updateServiceList(filteredServicesList);
            bottomSheetDialog.dismiss();
        });
    }

    private List<PrototypeService> filterServices(View dialogView) {
        AutoCompleteTextView categoryDropdown = dialogView.findViewById(R.id.text_services_category);
        AutoCompleteTextView eventTypeDropdown = dialogView.findViewById(R.id.text_event_type);
        RangeSlider priceRangeSlider = dialogView.findViewById(R.id.range_slider_price);
        SwitchMaterial availabilitySwitch = dialogView.findViewById(R.id.switch_availability);

        String selectedCategory = categoryDropdown.getText().toString();
        String selectedEventType = eventTypeDropdown.getText().toString();
        List<Float> priceRangeValues = priceRangeSlider.getValues();
        float minPrice = priceRangeValues.get(0);
        float maxPrice = priceRangeValues.get(1);
        boolean availability = availabilitySwitch.isChecked();

        List<PrototypeService> filteredServicesList = new ArrayList<>();
        for (PrototypeService service : services) {
            if (isServiceMatching(service, selectedCategory, selectedEventType, minPrice, maxPrice, availability)) {
                filteredServicesList.add(service);
            }
        }
        return filteredServicesList;
    }

    private boolean isServiceMatching(PrototypeService service, String category, String eventType, float minPrice, float maxPrice, boolean availability) {
        boolean matchesCategory = service.getCategory().equalsIgnoreCase(category) || category.isEmpty();
        boolean matchesEventType = service.getEventTypes().contains(eventType) || eventType.isEmpty();
        boolean matchesPrice = service.getPrice() >= minPrice && service.getPrice() <= maxPrice;
        boolean matchesAvailability = service.isAvailable() == availability;

        return matchesCategory && matchesEventType && matchesPrice && matchesAvailability;
    }

    @Override
    public void onDeleteService(int position) {
        deleteModal = binding.getRoot().findViewById(R.id.nigger);
        binding.modalBackground.setVisibility(View.VISIBLE);
        deleteModal.setVisibility(View.VISIBLE);
        Button cancelButton = deleteModal.findViewById(R.id.cancelButton);
        Button confirmButton = deleteModal.findViewById(R.id.confirmButton);

        cancelButton.setOnClickListener(v -> {
            binding.modalBackground.setVisibility(View.INVISIBLE);
            deleteModal.setVisibility(View.INVISIBLE);
        });

        confirmButton.setOnClickListener(v -> {
            this.services.remove(position);
            serviceAdapter.notifyItemRemoved(position);
            serviceAdapter.notifyItemRangeChanged(position, services.size());
            binding.modalBackground.setVisibility(View.INVISIBLE);
            deleteModal.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    public void onUpdateService(int position) {
        PrototypeService service = services.get(position);
        ServiceViewModel viewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);
        viewModel.populate(service);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_serviceView_toServiceCreationStepOne);
    }

    private void setUpAdapter() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        serviceAdapter = new PupServiceAdapter(services, this);
        binding.recyclerView.setAdapter(serviceAdapter);
    }

    private void setUpSearch() {
        binding.searchServices.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                serviceAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void addDummyData() {
        services.add(new PrototypeService("Music", "Orkestar", "Jako dobar orekstar", "Ovo je zaista jako specificno", 500, 25, true, false, 3.5, 3, 3, true, new ArrayList<>(), new ArrayList<>(Arrays.asList("Veselje"))));
        services.add(new PrototypeService("Food", "Ketering", "Jako dobra hrana", "Ovo je zaista jako specificno", 500, 25, false, true, 2.5, 5, 5, false, new ArrayList<>(), new ArrayList<>(Arrays.asList("Svabda"))));
        services.add(new PrototypeService("Beverages", "Hranilica", "Jako dobra hranilica", "Ovo je zaista jako specificno", 250, 25, true, true, 5, 1, 1, true, new ArrayList<>(), new ArrayList<>(Arrays.asList("SLavlje"))));
        services.add(new PrototypeService("Sport", "Kosarka", "Jako dobar sport", "Ovo je zaista jako specificno", 560, 25, false, false, 4, 2, 2, false, new ArrayList<>(), new ArrayList<>(Arrays.asList("Rodjendan"))));
    }

}