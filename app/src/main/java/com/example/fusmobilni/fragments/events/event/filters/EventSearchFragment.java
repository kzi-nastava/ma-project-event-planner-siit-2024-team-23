package com.example.fusmobilni.fragments.events.event.filters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.events.event.EventsHorizontalAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.EventFragmentSearchBinding;
import com.example.fusmobilni.responses.events.EventTypesResponse;
import com.example.fusmobilni.responses.events.filter.EventLocationsResponse;
import com.example.fusmobilni.responses.events.filter.EventPaginationResponse;
import com.example.fusmobilni.responses.events.filter.EventsPaginationResponse;
import com.example.fusmobilni.viewModels.events.filters.EventSearchViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventSearchFragment extends Fragment {
    private EventSearchViewModel _viewModel;
    private EventFragmentSearchBinding _binding;
    private TextInputLayout _searchView;
    private RecyclerView listView;
    private EventsHorizontalAdapter eventsHorizontalAdapter;
    private Spinner paginationSpinner;
    private MaterialButton prevButton;
    private MaterialButton nextButton;


    public EventSearchFragment() {
        // Required empty public constructor
    }

    public static EventSearchFragment newInstance(String param1, String param2) {
        EventSearchFragment fragment = new EventSearchFragment();

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
        // Inflate the layout for this fragment
        this._binding = EventFragmentSearchBinding.inflate(inflater, container, false);
        View view = this._binding.getRoot();

        listView = this._binding.eventsList;
        this._searchView = this._binding.searchTextInputLayout;

        initializeButtons();

        eventsHorizontalAdapter = new EventsHorizontalAdapter();
        listView.setAdapter(eventsHorizontalAdapter);

        _viewModel = new ViewModelProvider(requireActivity()).get(EventSearchViewModel.class);

        fetchEvents();
        fetchEventTypes();
        fetchLocations();

        _viewModel.getEvents().observe(getViewLifecycleOwner(), observer -> {
            eventsHorizontalAdapter.setData(_viewModel.getEvents().getValue());
            listView.setAdapter(eventsHorizontalAdapter);
        });

        _binding.searchButton.setOnClickListener(v -> {
            _viewModel.setConstraint(_searchView.getEditText().getText().toString());
        });

        _binding.eventsFilterButton.setOnClickListener(v -> {
            openFilterFragment();
        });


        initializePaginationSpinner();

        return view;
    }

    private void openFilterFragment() {
        EventFilterFragment fragment = new EventFilterFragment();

        fragment.show(getParentFragmentManager(), fragment.getTag());
    }

    private void initializePaginationSpinner() {
        paginationSpinner = _binding.paginationSpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.paginationPageSizes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paginationSpinner.setAdapter(adapter);
        paginationSpinner.setSelection(0);
        paginationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void initializeButtons() {

        prevButton = this._binding.eventSearchPreviousButton;
        nextButton = this._binding.eventSearchNextButton;

        prevButton.setOnClickListener(v -> {
            _viewModel.prevPage();
        });
        nextButton.setOnClickListener(v -> {
            _viewModel.nextPage();
        });
    }


    private void fetchEventTypes() {
        Call<EventTypesResponse> call = ClientUtils.eventsService.findEventTypesForEvents();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<EventTypesResponse> call, Response<EventTypesResponse> response) {
                if (response.isSuccessful()) {
                    _viewModel.setEventTypes(response.body().eventTypes);

                }
            }

            @Override
            public void onFailure(Call<EventTypesResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchLocations() {
        Call<EventLocationsResponse> call = ClientUtils.eventsService.findEventLocations();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<EventLocationsResponse> call, Response<EventLocationsResponse> response) {
                if (response.isSuccessful()) {
                    _viewModel.setLocations(response.body().locations);
                }
            }

            @Override
            public void onFailure(Call<EventLocationsResponse> call, Throwable t) {

            }
        });
    }

    private void fetchEvents() {
        Call<EventsPaginationResponse> call = ClientUtils.eventsService.findFilteredAndPaginated(0, 5);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<EventsPaginationResponse> call, Response<EventsPaginationResponse> response) {
                if (response.isSuccessful()) {
                    List<EventPaginationResponse> responses = response.body().content;
                    long totalItems = response.body().totalItems;
                    int totalPages = response.body().totalPages;


                    _viewModel.setEvents(responses);
                    _viewModel.setTotalElements(totalItems);
                    _viewModel.setTotalPages(totalPages);

                }
            }

            @Override
            public void onFailure(Call<EventsPaginationResponse> call, Throwable t) {
                _viewModel.setEvents(new ArrayList<>());
                _viewModel.setTotalElements(0L);
                _viewModel.setTotalPages(0);
            }
        });
    }

}