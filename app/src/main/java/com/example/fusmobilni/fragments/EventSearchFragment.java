package com.example.fusmobilni.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.example.fusmobilni.adapters.EventsHorizontalAdapter;
import com.example.fusmobilni.databinding.EventFragmentSearchBinding;
import com.example.fusmobilni.interfaces.OnFilterEventsApplyListener;
import com.example.fusmobilni.model.Event;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.search.SearchBar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventSearchFragment extends Fragment implements OnFilterEventsApplyListener {

    private EventFragmentSearchBinding _binding;
    private TextInputLayout _searchView;
    private ArrayList<Event> events;
    private RecyclerView listView;
    private EventsHorizontalAdapter eventsHorizontalAdapter;
    private Spinner paginationSpinner;
    private MaterialButton prevButton;
    private MaterialButton nextButton;
    private SearchBar _searchBar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventSearchFragment newInstance(String param1, String param2) {
        EventSearchFragment fragment = new EventSearchFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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


        this._searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                eventsHorizontalAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        _binding.eventsFilterButton.setOnClickListener(v -> {
            eventsHorizontalAdapter.resetFilters();
            EventFilterFragment fragment = new EventFilterFragment();
            fragment.setFilterListener(new OnFilterEventsApplyListener() {
                @Override
                public void onFilterApply(String category, String location, String date) {
                    eventsHorizontalAdapter.setFilters(_searchView.getEditText().getText().toString(),category,location,date);
                }
            });
            fragment.show(getParentFragmentManager(), fragment.getTag());
        });
        events = fillEvents();
        eventsHorizontalAdapter.setOriginalData(events);
        eventsHorizontalAdapter.setFilteringData(events);
        eventsHorizontalAdapter.setData(events);
        eventsHorizontalAdapter.loadPage(0);

        initializePaginationSpinner();

        return view;
    }
    private void initializePaginationSpinner(){
        paginationSpinner = _binding.paginationSpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.paginationPageSizes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paginationSpinner.setAdapter(adapter);
        paginationSpinner.setSelection(0);
        paginationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedItem = Integer.parseInt(String.valueOf(parent.getItemAtPosition(position)));
                eventsHorizontalAdapter.setPageSize(selectedItem, _searchView.getEditText().getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void initializeButtons(){

        prevButton = this._binding.eventSearchPreviousButton;
        nextButton = this._binding.eventSearchNextButton;

        prevButton.setOnClickListener(v -> eventsHorizontalAdapter.prevPage());
        nextButton.setOnClickListener(v -> eventsHorizontalAdapter.nextPage());
    }
    private ArrayList<Event> fillEvents() {
        ArrayList<Event> e = new ArrayList<>();
        e.add(new Event("Food and Wine Tasting", "12-7-2024", "Napa Valley Vineyard", "Food"));
        e.add(new Event("Tech Innovators Conference", "15-8-2024", "Silicon Valley Expo Center", "Tech"));
        e.add(new Event("Autumn Art and Sculpture Exhibition", "18-9-2024", "Paris Art Museum", "Art"));
        e.add(new Event("Global Startup Pitch Event", "22-11-2024", "Berlin Startup Hub", "Tech"));
        e.add(new Event("International Film and Documentary Festival", "5-11-2024", "Toronto Film Centre", "Art"));

        e.add(new Event("New Year Gala", "1-1-2024", "Times Square", "Travel"));
        e.add(new Event("Valentine's Day Dance", "10-2-2024", "City Hall Ballroom", "Music"));
        e.add(new Event("Winter Sports Championship", "20-2-2024", "Aspen Ski Resort", "Sports"));
        e.add(new Event("Spring Fashion Week", "15-3-2024", "New York City", "Fashion"));
        e.add(new Event("Cherry Blossom Festival", "30-3-2024", "Washington D.C.", "Travel"));

        e.add(new Event("Earth Day Celebration", "22-4-2024", "Central Park", "Health"));
        e.add(new Event("Music Festival", "10-5-2024", "Coachella Valley", "Music"));
        e.add(new Event("Memorial Day Parade", "24-5-2024", "Chicago", "Travel"));
        e.add(new Event("Summer Food Festival", "7-6-2024", "Los Angeles", "Food"));
        e.add(new Event("Pride Parade", "15-6-2024", "San Francisco", "Travel"));

        e.add(new Event("Independence Day Fireworks", "4-7-2024", "Washington D.C.", "Travel"));
        e.add(new Event("Bastille Day Celebration", "14-7-2024", "Paris", "Travel"));
        e.add(new Event("International Comic Con", "30-7-2024", "San Diego", "Art"));
        e.add(new Event("Outdoor Yoga Festival", "10-8-2024", "Bali", "Health"));
        e.add(new Event("Gastronomy Festival", "20-8-2024", "Barcelona", "Food"));

        e.add(new Event("Labor Day Weekend", "1-9-2024", "New York", "Travel"));
        e.add(new Event("Tech Startups Expo", "10-9-2024", "Austin", "Tech"));
        e.add(new Event("International Film Festival", "25-9-2024", "Venice", "Art"));
        e.add(new Event("Oktoberfest", "8-10-2024", "Munich", "Food"));
        e.add(new Event("Halloween Spooktacular", "31-10-2024", "New Orleans", "Travel"));

        e.add(new Event("Thanksgiving Parade", "10-11-2024", "New York", "Travel"));
        e.add(new Event("Black Friday Shopping Event", "22-11-2024", "Mall of America", "Fashion"));
        e.add(new Event("Christmas Market", "5-12-2024", "Prague", "Food"));
        e.add(new Event("Winter Wonderland Festival", "20-12-2024", "London", "Travel"));
        e.add(new Event("New Year's Eve Countdown", "31-12-2024", "Sydney", "Travel"));

        e.add(new Event("Chocolate Festival", "14-2-2024", "Zurich", "Food"));
        e.add(new Event("St. Patrick's Day Parade", "5-3-2024", "Dublin", "Travel"));
        e.add(new Event("International Women’s Day Conference", "17-3-2024", "Los Angeles", "Education"));
        e.add(new Event("May Day Celebration", "29-4-2024", "Berlin", "Travel"));
        e.add(new Event("Flower Festival", "12-5-2024", "Amsterdam", "Travel"));

        e.add(new Event("Midsummer Festival", "19-6-2024", "Stockholm", "Travel"));
        e.add(new Event("World Music Festival", "24-7-2024", "Austin", "Music"));
        e.add(new Event("National Book Festival", "13-8-2024", "Washington D.C.", "Education"));
        e.add(new Event("Sustainable Living Expo", "21-9-2024", "San Francisco", "Health"));
        e.add(new Event("Haunted House Experience", "18-10-2024", "Los Angeles", "Travel"));

        e.add(new Event("Dia de los Muertos Festival", "2-11-2024", "Mexico City", "Travel"));
        e.add(new Event("Winter Solstice Celebration", "6-12-2024", "Reykjavik", "Travel"));
        e.add(new Event("Christmas Concert", "25-12-2024", "London", "Music"));
        e.add(new Event("Boxing Day Sales", "28-12-2024", "Toronto", "Fashion"));
        e.add(new Event("Epiphany Celebration", "3-1-2024", "Madrid", "Education"));

        e.add(new Event("Winter Carnival", "10-2-2024", "Quebec", "Travel"));
        e.add(new Event("Wi Art and Design Fair", "15-3-2024", "Tokyo", "Art"));
        return e;
    }

    @Override
    public void onFilterApply(String category, String location, String date) {

    }
}