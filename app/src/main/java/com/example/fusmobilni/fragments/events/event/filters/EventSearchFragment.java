package com.example.fusmobilni.fragments.events.event.filters;

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
import com.example.fusmobilni.adapters.events.event.EventsHorizontalAdapter;
import com.example.fusmobilni.databinding.EventFragmentSearchBinding;
import com.example.fusmobilni.model.event.Event;
import com.example.fusmobilni.viewModels.events.filters.EventSearchViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventSearchFragment extends Fragment {

    private EventSearchViewModel _viewModel;
    private EventFragmentSearchBinding _binding;
    private TextInputLayout _searchView;
    private ArrayList<Event> events;
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

        this._searchView.getEditText().addTextChangedListener(new TextWatcher() {
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
        _binding.eventsFilterButton.setOnClickListener(v -> {
            openFilterFragment();
        });
        events = fillEvents();
        _viewModel.setData(events);
        eventsHorizontalAdapter.setData(_viewModel.getPagedEvents().getValue());

        _viewModel.getConstraint().observe(getViewLifecycleOwner(), observer -> {
            eventsHorizontalAdapter.setData(_viewModel.getPagedEvents().getValue());
        });
        _viewModel.getPagedEvents().observe(getViewLifecycleOwner(), observer -> {
            eventsHorizontalAdapter.setData(_viewModel.getPagedEvents().getValue());
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

        prevButton.setOnClickListener(v -> _viewModel.prevPage());
        nextButton.setOnClickListener(v -> _viewModel.nextPage());
    }

    private ArrayList<Event> fillEvents() {
        ArrayList<Event> e = new ArrayList<>();
        e.add(new Event("Food and Wine Tasting", "12-07-2024", "Napa Valley Vineyard", "Food", "Food"));
        e.add(new Event("Tech Innovators Conference", "15-08-2024", "Silicon Valley Expo Center", "Tech", "Tech"));
        e.add(new Event("Autumn Art and Sculpture Exhibition", "18-09-2024", "Paris Art Museum", "Art", "Tech"));
        e.add(new Event("Global Startup Pitch Event", "22-11-2024", "Berlin Startup Hub", "Tech","Tech"));
        e.add(new Event("International Film and Documentary Festival", "05-11-2024", "Toronto Film Centre", "Art","Art"));

        e.add(new Event("New Year Gala", "01-01-2024", "Times Square", "Travel", "Travel"));
        e.add(new Event("Valentine's Day Dance", "10-02-2024", "City Hall Ballroom", "Music", "Music"));
        e.add(new Event("Winter Sports Championship", "20-02-2024", "Aspen Ski Resort", "Sports", "Sports"));
        e.add(new Event("Spring Fashion Week", "15-03-2024", "New York City", "Fashion", "Fashion"));
        e.add(new Event("Cherry Blossom Festival", "30-03-2024", "Washington D.C.", "Travel", "Travel"));

        e.add(new Event("Earth Day Celebration", "22-04-2024", "Central Park", "Health", "Health"));
        e.add(new Event("Music Festival", "10-05-2024", "Coachella Valley", "Music", "Music"));
        e.add(new Event("Memorial Day Parade", "24-05-2024", "Chicago", "Travel", "Travel"));
        e.add(new Event("Summer Food Festival", "07-06-2024", "Los Angeles", "Food", "Food"));
        e.add(new Event("Pride Parade", "15-06-2024", "San Francisco", "Travel", "Travel"));

        e.add(new Event("Independence Day Fireworks", "04-07-2024", "Washington D.C.", "Travel", "Travel"));
        e.add(new Event("Bastille Day Celebration", "14-07-2024", "Paris", "Travel", "Travel"));
        e.add(new Event("International Comic Con", "30-07-2024", "San Diego", "Art", "Art"));
        e.add(new Event("Outdoor Yoga Festival", "10-08-2024", "Bali", "Health", "Health"));
        e.add(new Event("Gastronomy Festival", "20-08-2024", "Barcelona", "Food", "Food"));

        e.add(new Event("Labor Day Weekend", "01-09-2024", "New York", "Travel", "Travel"));
        e.add(new Event("Tech Startups Expo", "10-09-2024", "Austin", "Tech", "Tech"));
        e.add(new Event("International Film Festival", "25-09-2024", "Venice", "Art", "Art"));
        e.add(new Event("Oktoberfest", "08-10-2024", "Munich", "Food", "Food"));
        e.add(new Event("Halloween Spooktacular", "31-10-2024", "New Orleans", "Travel", "Travel"));

        e.add(new Event("Thanksgiving Parade", "10-11-2024", "New York", "Travel", "Travel"));
        e.add(new Event("Black Friday Shopping Event", "22-11-2024", "Mall of America", "Fashion", "Fashion"));
        e.add(new Event("Christmas Market", "05-12-2024", "Prague", "Food", "Food"));
        e.add(new Event("Winter Wonderland Festival", "20-12-2024", "London", "Travel", "travel"));
        e.add(new Event("New Year's Eve Countdown", "31-12-2024", "Sydney", "Travel", "Travel"));

        e.add(new Event("Chocolate Festival", "14-02-2024", "Zurich", "Food", "Food"));
        e.add(new Event("St. Patrick's Day Parade", "05-03-2024", "Dublin", "Travel","Travel"));
        e.add(new Event("International Women’s Day Conference", "17-03-2024", "Los Angeles", "Education", "Education"));
        e.add(new Event("May Day Celebration", "29-04-2024", "Berlin", "Travel", "Travel"));
        e.add(new Event("Flower Festival", "12-05-2024", "Amsterdam", "Travel", "Travel"));

        e.add(new Event("Midsummer Festival", "19-06-2024", "Stockholm", "Travel", "Travel"));
        e.add(new Event("World Music Festival", "24-07-2024", "Austin", "Music", "Music"));
        e.add(new Event("National Book Festival", "13-08-2024", "Washington D.C.", "Education", "Education"));
        e.add(new Event("Sustainable Living Expo", "21-09-2024", "San Francisco", "Health", "Health"));
        e.add(new Event("Haunted House Experience", "18-10-2024", "Los Angeles", "Travel", "Travel"));

        e.add(new Event("Dia de los Muertos Festival", "02-11-2024", "Mexico City", "Travel", "Travel"));
        e.add(new Event("Winter Solstice Celebration", "06-12-2024", "Reykjavik", "Travel", "Travel"));
        e.add(new Event("Christmas Concert", "25-12-2024", "London", "Music", "Music"));
        e.add(new Event("Boxing Day Sales", "28-12-2024", "Toronto", "Fashion", "Fashion"));
        e.add(new Event("Epiphany Celebration", "03-01-2024", "Madrid", "Education", "Education"));

        e.add(new Event("Winter Carnival", "10-02-2024", "Quebec", "Travel", "Travel"));
        e.add(new Event("Wi Art and Design Fair", "15-03-2024", "Tokyo", "Art", "Art"));

        return e;
    }

}