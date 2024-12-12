package com.example.fusmobilni.fragments.events.event;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.users.eventOrganizer.EventOrganizerEventAdapter;
import com.example.fusmobilni.databinding.FragmentEventOrganizerEventDetailsBinding;
import com.example.fusmobilni.interfaces.EOEventClickListener;
import com.example.fusmobilni.model.event.Event;
import com.example.fusmobilni.viewModels.events.event.EventViewModel;

import java.util.ArrayList;

public class EventOrganizerEventFragment extends Fragment implements EOEventClickListener {
    private FragmentEventOrganizerEventDetailsBinding _binding;
    private EventViewModel _eventViewModel;
    private final ArrayList<Event> _events = new ArrayList<>();
    private EventOrganizerEventAdapter _eventAdapter;

    public EventOrganizerEventFragment() {
        // Required empty public constructor
    }
    public static EventOrganizerEventFragment newInstance() {
        return new EventOrganizerEventFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentEventOrganizerEventDetailsBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();
        _eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        RecyclerView recyclerView = _binding.eventsRecycleView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        populateData();
        this._eventAdapter = new EventOrganizerEventAdapter(this._events, this);
        recyclerView.setAdapter(_eventAdapter);
        _binding.floatingActionButton.setOnClickListener(v-> {
            _eventViewModel.cleanUp();
            Navigation.findNavController(view).navigate(R.id.action_eventOrganizerEventDetailsFragment_to_createEventFragment);
        });
        return view;
    }

    private void populateData() {
        _events.add(new Event("Food and Wine Tasting", "12-07-2025", "Napa Valley Vineyard", "Food", "Food"));
        _events.add(new Event("Tech Innovators Conference", "15-08-2025", "Silicon Valley Expo Center", "Tech", "Tech"));
        _events.add(new Event("Autumn Art and Sculpture Exhibition", "18-09-2025", "Paris Art Museum", "Art", "Art"));
        _events.add(new Event("Global Startup Pitch Event", "22-12-2024", "Berlin Startup Hub", "Tech", "Tech"));
        _events.add(new Event("International Film and Documentary Festival", "05-12-2025", "Toronto Film Centre", "Art", "Art"));
        _events.add(new Event("New Year Gala", "01-01-2025", "Times Square", "Travel", "Travel"));
        _events.add(new Event("Valentine's Day Dance", "10-02-2025", "City Hall Ballroom", "Music", "Music"));
        _events.add(new Event("Winter Sports Championship", "20-02-2025", "Aspen Ski Resort", "Sports", "Sports"));
        _events.add(new Event("Spring Fashion Week", "15-03-2025", "New York City", "Fashion", "Fashion"));
        _events.add(new Event("Cherry Blossom Festival", "30-03-2025", "Washington D.C.", "Travel", "Travel"));
    }

    @Override
    public void onEventClick(int position) {
        Event event = _events.get(position);
        Toast.makeText(getContext(), event.getTitle(), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putParcelable("event", event);
        Navigation.findNavController(_binding.getRoot()).navigate(R.id.action_eventOrganizerEventDetailsFragment_to_eventDetailsFragment, bundle);
    }

    @Override
    public void onStatsClick(int position) {
        Event event = _events.get(position);
        Toast.makeText(getContext(), event.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditClick(int position) {
        Event event = _events.get(position);
        Toast.makeText(getContext(), event.getTitle(), Toast.LENGTH_SHORT).show();
        this._eventViewModel.populate(event);
        Navigation.findNavController(_binding.getRoot()).navigate(R.id.action_eventOrganizerEventDetailsFragment_to_createEventFragment);

    }
}