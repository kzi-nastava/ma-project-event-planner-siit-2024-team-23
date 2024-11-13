package com.example.fusmobilni.fragments;



import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
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
import com.example.fusmobilni.viewModels.ServiceProviderViewModel;
import com.example.fusmobilni.viewModels.ServiceSearchViewModel;
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
    private ServiceProviderViewModel viewModel;

    public ServiceView() {
    }

    public static ServiceView newInstance() {
        return new ServiceView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentServiceViewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        requireActivity().getViewModelStore().clear();
        viewModel = new ViewModelProvider(requireActivity()).get(ServiceProviderViewModel.class);
        addDummyData();
        setUpAdapter();

        binding.floatingActionButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_serviceView_toServiceCreationStepOne);
        });
        setUpSearch();
        binding.filterBtn.setOnClickListener(v -> openFilterFragment());

        viewModel.setData(services);

        viewModel.getSearchConstraint().observe(getViewLifecycleOwner(),observer->{
            serviceAdapter.setData(viewModel.getFilteredServices().getValue());
        });

        viewModel.getFilteredServices().observe(getViewLifecycleOwner(),observer->{
            serviceAdapter.setData(viewModel.getFilteredServices().getValue());
        });

        return view;
    }

    private void openFilterFragment() {
        ServiceProviderFilterFragment filterFragment = new ServiceProviderFilterFragment();

        filterFragment.show(getParentFragmentManager(), filterFragment.getTag());
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
                viewModel.setSearchConstraint(s.toString());
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