package com.example.fusmobilni.fragments.items.service.Filters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.items.service.ServiceHorizontalAdapter;
import com.example.fusmobilni.databinding.FragmentServiceSearchBinding;
import com.example.fusmobilni.model.items.service.Service;
import com.example.fusmobilni.viewModels.items.service.filters.ServiceSearchViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceSearchFragment extends Fragment {
    private ServiceSearchViewModel _viewModel;
    private FragmentServiceSearchBinding _binding;
    private TextInputLayout _searchView;
    private ArrayList<Service> _services;
    private RecyclerView _listView;
    private ServiceHorizontalAdapter _servicesAdapter;
    private Spinner _paginationSpinner;
    private MaterialButton _prevButton;
    private MaterialButton _nextButton;

    public static ServiceSearchFragment newInstance(String param1, String param2) {
        ServiceSearchFragment fragment = new ServiceSearchFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        _binding = FragmentServiceSearchBinding.inflate(inflater, container, false);
        View view = _binding.getRoot();

        _listView = _binding.serviceList;
        _searchView = _binding.searchTextInputLayoutService;
        initializeButtons();

        _servicesAdapter = new ServiceHorizontalAdapter();
        _listView.setAdapter(_servicesAdapter);

        _viewModel = new ViewModelProvider(requireActivity()).get(ServiceSearchViewModel.class);

        _searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _viewModel.setConstraint(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        _binding.serviceFilterButton.setOnClickListener(v -> {
            openFilterFragment();
        });
        _services = fillServices();
        _viewModel.setData(_services);
        _servicesAdapter.setData(_viewModel.getPagedServices().getValue());

        _viewModel.getConstraint().observe(getViewLifecycleOwner(),observer->{
            _servicesAdapter.setData(_viewModel.getPagedServices().getValue());
        });

        _viewModel.getPagedServices().observe(getViewLifecycleOwner(),observer->{
            _servicesAdapter.setData(_viewModel.getPagedServices().getValue());
        });

        initalizepaginationspinner();

        return view;
    }

    private void initalizepaginationspinner() {
        _paginationSpinner = _binding.paginationSpinnerservice;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.paginationPageSizes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _paginationSpinner.setAdapter(adapter);
        _paginationSpinner.setSelection(0);
        _paginationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedItem = Integer.parseInt(String.valueOf(parent.getItemAtPosition(position)));
                _viewModel.setPageSize(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void openFilterFragment() {
        ServiceFragmentFilter filterFragment = new ServiceFragmentFilter();

        filterFragment.show(getParentFragmentManager(), filterFragment.getTag());
    }

    private void initializeButtons() {
        _prevButton = this._binding.serviceSearchPreviousButton;
        _nextButton = this._binding.serviceSearchNextButton;

        _prevButton.setOnClickListener(v -> _viewModel.prevPage());
        _nextButton.setOnClickListener(v -> _viewModel.nextPage());
    }

    private ArrayList<Service> fillServices() {
        ArrayList<Service> s = new ArrayList<>();
        s.add(new Service("Live band for weddings and parties", "Wedding Band", "New York", "Music"));
        s.add(new Service("Professional photography for events", "Photography Service", "Los Angeles", "Art"));
        s.add(new Service("Catering services for all occasions", "Catering Service", "Chicago", "Food"));
        s.add(new Service("Event decoration and setup", "Decoration Service", "San Francisco", "Art"));
        s.add(new Service("Spacious venue for corporate events", "Venue Rental", "Miami", "Travel"));
        s.add(new Service("DJ service with customized playlists", "DJ Service", "Las Vegas", "Music"));
        s.add(new Service("Makeup and styling for events", "Makeup Service", "New York", "Fashion"));
        s.add(new Service("Mobile food truck service for events", "Food Truck Service", "Austin", "Food"));
        s.add(new Service("Event photography with instant prints", "Instant Photography", "Orlando", "Art"));
        s.add(new Service("Luxury limousine rental for special events", "Limo Service", "Atlanta", "Travel"));
        s.add(new Service("Bartending services with custom cocktails", "Bartending Service", "Houston", "Food"));
        s.add(new Service("Floral arrangements for events", "Flower Arrangements", "Seattle", "Art"));
        s.add(new Service("Kids' party entertainment with clowns", "Kids Entertainment", "Dallas", "Sports"));
        s.add(new Service("Personal chef for private dinners", "Private Chef Service", "San Diego", "Food"));
        s.add(new Service("Sound and lighting setup for concerts", "Audio & Lighting", "Los Angeles", "Tech"));
        s.add(new Service("Photo booth rental with props", "Photo Booth", "Miami", "Art"));
        s.add(new Service("Wedding planning and coordination", "Wedding Planner", "Nashville", "Education"));
        s.add(new Service("Luxury car rental for events", "Car Rental Service", "Chicago", "Travel"));
        s.add(new Service("Balloon decorations for parties", "Balloon Decor", "Boston", "Art"));
        s.add(new Service("Catering for corporate lunches", "Corporate Catering", "New York", "Food"));
        s.add(new Service("Party tent rentals with seating", "Tent Rental", "Denver", "Travel"));
        s.add(new Service("Drone photography for outdoor events", "Drone Photography", "Phoenix", "Tech"));
        s.add(new Service("Live solo guitarist for ceremonies", "Guitarist Service", "San Antonio", "Music"));
        s.add(new Service("Custom invitation design", "Invitation Design", "Portland", "Art"));
        s.add(new Service("On-site hair styling for events", "Hair Styling", "Las Vegas", "Fashion"));
        s.add(new Service("Professional event video production", "Video Production", "New York", "Tech"));
        s.add(new Service("Corporate event planning and management", "Corporate Planner", "San Francisco", "Education"));
        s.add(new Service("Children's activity zone with games", "Kids Activity Zone", "Houston", "Sports"));
        s.add(new Service("Luxury yacht rental for parties", "Yacht Rental", "Miami", "Travel"));
        s.add(new Service("Event staff and security service", "Event Staffing", "Los Angeles", "Health"));
        s.add(new Service("Gourmet dessert catering", "Dessert Catering", "Philadelphia", "Food"));
        s.add(new Service("Karaoke machine rental", "Karaoke Service", "Seattle", "Music"));
        s.add(new Service("Elegant lighting decoration", "Lighting Decoration", "San Diego", "Art"));
        s.add(new Service("Corporate gift customization", "Gift Customization", "Chicago", "Art"));
        s.add(new Service("Photographer specializing in candids", "Candid Photography", "Boston", "Photography"));
        s.add(new Service("Private beach rental for events", "Beach Venue Rental", "Santa Monica", "Travel"));
        s.add(new Service("Magician for private events", "Magician Service", "Orlando", "Entertainment"));
        s.add(new Service("Event signage and branding setup", "Event Branding", "Las Vegas", "Art"));
        s.add(new Service("Luxury RV rental for glamping", "RV Rental", "Phoenix", "Travel"));
        s.add(new Service("Fireworks display setup for events", "Fireworks Service", "San Antonio", "Entertainment"));
        s.add(new Service("Eco-friendly catering with local ingredients", "Eco Catering", "Portland", "Food"));
        s.add(new Service("3D virtual tour creation for venues", "3D Tour Service", "New York", "Tech"));
        return s;
    }

}