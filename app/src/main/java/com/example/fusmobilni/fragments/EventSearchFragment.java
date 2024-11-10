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
public class EventSearchFragment extends Fragment {

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
            EventFilterFragment fragment = new EventFilterFragment();
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
        e.add(new Event("12", "July", "Food and Wine Tasting Festival", "2024", "Napa Valley Vineyard"));
        e.add(new Event("15", "August", "Tech Innovators Conference", "2024", "Silicon Valley Expo Center"));
        e.add(new Event("18", "September", "Autumn Art and Sculpture Exhibition", "2024", "Paris Art Museum"));
        e.add(new Event("22", "October", "Global Startup Pitch Event", "2024", "Berlin Startup Hub"));
        e.add(new Event("5", "November", "International Film and Documentary Festival", "2024", "Toronto Film Centre"));

        // 50 More Events
        e.add(new Event("1", "January", "New Year Gala", "2024", "Times Square"));
        e.add(new Event("10", "February", "Valentine's Day Dance", "2024", "City Hall Ballroom"));
        e.add(new Event("20", "February", "Winter Sports Championship", "2024", "Aspen Ski Resort"));
        e.add(new Event("15", "March", "Spring Fashion Week", "2024", "New York City"));
        e.add(new Event("30", "March", "Cherry Blossom Festival", "2024", "Washington D.C."));

        e.add(new Event("22", "April", "Earth Day Celebration", "2024", "Central Park"));
        e.add(new Event("10", "May", "Music Festival", "2024", "Coachella Valley"));
        e.add(new Event("24", "May", "Memorial Day Parade", "2024", "Chicago"));
        e.add(new Event("7", "June", "Summer Food Festival", "2024", "Los Angeles"));
        e.add(new Event("15", "June", "Pride Parade", "2024", "San Francisco"));

        e.add(new Event("4", "July", "Independence Day Fireworks", "2024", "Washington D.C."));
        e.add(new Event("14", "July", "Bastille Day Celebration", "2024", "Paris"));
        e.add(new Event("30", "July", "International Comic Con", "2024", "San Diego"));
        e.add(new Event("10", "August", "Outdoor Yoga Festival", "2024", "Bali"));
        e.add(new Event("20", "August", "Gastronomy Festival", "2024", "Barcelona"));

        e.add(new Event("1", "September", "Labor Day Weekend", "2024", "New York"));
        e.add(new Event("10", "September", "Tech Startups Expo", "2024", "Austin"));
        e.add(new Event("25", "September", "International Film Festival", "2024", "Venice"));
        e.add(new Event("8", "October", "Oktoberfest", "2024", "Munich"));
        e.add(new Event("31", "October", "Halloween Spooktacular", "2024", "New Orleans"));

        e.add(new Event("10", "November", "Thanksgiving Parade", "2024", "New York"));
        e.add(new Event("22", "November", "Black Friday Shopping Event", "2024", "Mall of America"));
        e.add(new Event("5", "December", "Christmas Market", "2024", "Prague"));
        e.add(new Event("20", "December", "Winter Wonderland Festival", "2024", "London"));
        e.add(new Event("31", "December", "New Year's Eve Countdown", "2024", "Sydney"));

        e.add(new Event("14", "February", "Chocolate Festival", "2024", "Zurich"));
        e.add(new Event("5", "March", "St. Patrick's Day Parade", "2024", "Dublin"));
        e.add(new Event("17", "March", "International Women’s Day Conference", "2024", "Los Angeles"));
        e.add(new Event("29", "April", "May Day Celebration", "2024", "Berlin"));
        e.add(new Event("12", "May", "Flower Festival", "2024", "Amsterdam"));

        e.add(new Event("19", "June", "Midsummer Festival", "2024", "Stockholm"));
        e.add(new Event("24", "July", "World Music Festival", "2024", "Austin"));
        e.add(new Event("13", "August", "National Book Festival", "2024", "Washington D.C."));
        e.add(new Event("21", "September", "Sustainable Living Expo", "2024", "San Francisco"));
        e.add(new Event("18", "October", "Haunted House Experience", "2024", "Los Angeles"));

        e.add(new Event("2", "November", "Dia de los Muertos Festival", "2024", "Mexico City"));
        e.add(new Event("6", "December", "Winter Solstice Celebration", "2024", "Reykjavik"));
        e.add(new Event("25", "December", "Christmas Concert", "2024", "London"));
        e.add(new Event("28", "December", "Boxing Day Sales", "2024", "Toronto"));
        e.add(new Event("3", "January", "Epiphany Celebration", "2024", "Madrid"));

        e.add(new Event("10", "February", "Winter Carnival", "2024", "Quebec"));
        e.add(new Event("15", "March", "Wi Art and Design Fair", "2024", "Tokyo"));
        return e;
    }

}