package com.example.fusmobilni.fragments;



import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.example.fusmobilni.model.DummyService;
import com.example.fusmobilni.model.PrototypeService;
import com.example.fusmobilni.viewModel.ServiceViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class ServiceView extends Fragment implements DeleteServiceListener {

    FragmentServiceViewBinding binding;
    private RecyclerView recyclerView;
    private PupServiceAdapter serviceAdapter;
    private View modalBackground;
    private View deleteModal;
    private ImageButton filterBtn;
    private SearchView searchView;
    private FloatingActionButton floatingActionButton;
    private List<PrototypeService> services = new ArrayList<>();

    public ServiceView() {
        // Required empty public constructor
    }

    public static ServiceView newInstance(String param1, String param2) {
        ServiceView fragment = new ServiceView();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentServiceViewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        requireActivity().getViewModelStore().clear();
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        addDummyData();

        serviceAdapter = new PupServiceAdapter(services, this);
        recyclerView.setAdapter(serviceAdapter);

        floatingActionButton = binding.floatingActionButton;
        floatingActionButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_serviceView_toServiceCreationStepOne);
        });
        modalBackground = binding.modalBackground;
        deleteModal = view.findViewById(R.id.nigger);
        searchView = binding.searchView;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                serviceAdapter.getFilter().filter(newText);
                return false;
            }
        });

        filterBtn = binding.filterBtn;
        filterBtn.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireActivity(), R.style.FullScreenBottomSheetDialog);
            View dialogView = getLayoutInflater().inflate(R.layout.service_filter, null);
            bottomSheetDialog.setContentView(dialogView);
            AutoCompleteTextView categoryDropdown = dialogView.findViewById(R.id.text_services_category);
            AutoCompleteTextView eventTypeDropdown = dialogView.findViewById(R.id.text_event_type);
            RangeSlider priceRangeSlider = dialogView.findViewById(R.id.range_slider_price);
            priceRangeSlider.setValueFrom(0f);
            priceRangeSlider.setValueTo(1000f);
            priceRangeSlider.setStepSize(1f);
            priceRangeSlider.setValues(100f, 500f);
            priceRangeSlider.setLabelFormatter(value -> "$" + String.format("%.0f", value));
            SwitchMaterial availabilitySwitch = dialogView.findViewById(R.id.switch_availability);
            String[] categories = getResources().getStringArray(R.array.categories);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories);
            categoryDropdown.setAdapter(adapter);
            String[] eventTypes = getResources().getStringArray(R.array.EventTypes);
            ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, eventTypes);
            eventTypeDropdown.setAdapter(eventAdapter);
            bottomSheetDialog.setContentView(dialogView);
            bottomSheetDialog.show();

            Button cancelBtn = dialogView.findViewById(R.id.cancelButton);
            Button applyBtn = dialogView.findViewById(R.id.applyButton);
            cancelBtn.setOnClickListener(filterView -> bottomSheetDialog.cancel());

            applyBtn.setOnClickListener(filteredServices -> {
                String selectedCategory = categoryDropdown.getText().toString();
                String selectedEventType = eventTypeDropdown.getText().toString();
                List<Float> priceRangeValues = priceRangeSlider.getValues();
                float minPrice = priceRangeValues.get(0);
                float maxPrice = priceRangeValues.get(1);
                boolean availability = availabilitySwitch.isChecked();

                List<PrototypeService> filteredServicesList = new ArrayList<>();
                for (PrototypeService service : services) {
                    boolean matchesCategory = service.getCategory().equalsIgnoreCase(selectedCategory) || selectedCategory.isEmpty();
                    boolean matchesEventType = service.getEventTypes().contains(selectedEventType) || selectedEventType.isEmpty();
                    boolean matchesPrice = service.getPrice() >= minPrice && service.getPrice() <= maxPrice;
                    boolean matchesAvailability = service.isAvailable() == availability;

                    if (matchesCategory && matchesEventType && matchesPrice && matchesAvailability) {
                        filteredServicesList.add(service);
                    }
                }

                // Update the adapter with the filtered list
                serviceAdapter.updateServiceList(filteredServicesList);

                // Close the bottom sheet dialog
                bottomSheetDialog.dismiss();
            });


        });

        return view;
    }

    @Override
    public void onDeleteService(int position) {
        modalBackground.setVisibility(View.VISIBLE);
        deleteModal.setVisibility(View.VISIBLE);
        Button cancelButton = deleteModal.findViewById(R.id.cancelButton);
        Button confirmButton = deleteModal.findViewById(R.id.confirmButton);

        cancelButton.setOnClickListener(v -> {
            modalBackground.setVisibility(View.INVISIBLE);
            deleteModal.setVisibility(View.INVISIBLE);
        });

        confirmButton.setOnClickListener(v -> {
            this.services.remove(position);
            serviceAdapter.notifyItemRemoved(position);
            serviceAdapter.notifyItemRangeChanged(position, services.size());
            modalBackground.setVisibility(View.INVISIBLE);
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

    public void addDummyData() {
        services.add(new PrototypeService("Music", "Orkestar", "Jako dobar orekstar", "Ovo je zaista jako specificno", 500, 25, true, false, 3.5, 3, 3, true, new ArrayList<>(), new ArrayList<>(Arrays.asList("Veselje"))));
        services.add(new PrototypeService("Food", "Ketering", "Jako dobra hrana", "Ovo je zaista jako specificno", 500, 25, false, true, 2.5, 5, 5, false, new ArrayList<>(), new ArrayList<>(Arrays.asList("Svabda"))));
        services.add(new PrototypeService("Beverages", "Hranilica", "Jako dobra hranilica", "Ovo je zaista jako specificno", 250, 25, true, true, 5, 1, 1, true, new ArrayList<>(), new ArrayList<>(Arrays.asList("SLavlje"))));
        services.add(new PrototypeService("Sport", "Kosarka", "Jako dobar sport", "Ovo je zaista jako specificno", 560, 25, false, false, 4, 2, 2, false, new ArrayList<>(), new ArrayList<>(Arrays.asList("Rodjendan"))));
    }

}