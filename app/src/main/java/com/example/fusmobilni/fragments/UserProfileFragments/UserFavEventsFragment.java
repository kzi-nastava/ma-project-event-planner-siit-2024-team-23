package com.example.fusmobilni.fragments.UserProfileFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.adapters.EventsHorizontalAdapter;
import com.example.fusmobilni.databinding.FragmentUserEventsFragmentsBinding;
import com.example.fusmobilni.model.Event;

import java.util.ArrayList;

public class UserFavEventsFragment extends Fragment {

    private FragmentUserEventsFragmentsBinding _binding;

    public UserFavEventsFragment() {
        // Required empty public constructor
    }

    public static UserFavEventsFragment newInstance() {
        return new UserFavEventsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentUserEventsFragmentsBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();
        RecyclerView listView = _binding.eventsList;
        EventsHorizontalAdapter eventsHorizontalAdapter = new EventsHorizontalAdapter();
        listView.setAdapter(eventsHorizontalAdapter);
        ArrayList<Event> events = fillEvents();
        eventsHorizontalAdapter.setData(events);
        return view;
    }
    private ArrayList<Event> fillEvents() {
        ArrayList<Event> e = new ArrayList<>();
        e.add(new Event("Food and Wine Tasting", "12-07-2024", "Napa Valley Vineyard", "Food"));
        e.add(new Event("Tech Innovators Conference", "15-08-2024", "Silicon Valley Expo Center", "Tech"));
        e.add(new Event("Autumn Art and Sculpture Exhibition", "18-09-2024", "Paris Art Museum", "Art"));
        e.add(new Event("Global Startup Pitch Event", "22-11-2024", "Berlin Startup Hub", "Tech"));
        e.add(new Event("International Film and Documentary Festival", "05-11-2024", "Toronto Film Centre", "Art"));

        e.add(new Event("New Year Gala", "01-01-2024", "Times Square", "Travel"));
        e.add(new Event("Valentine's Day Dance", "10-02-2024", "City Hall Ballroom", "Music"));
        e.add(new Event("Winter Sports Championship", "20-02-2024", "Aspen Ski Resort", "Sports"));
        e.add(new Event("Spring Fashion Week", "15-03-2024", "New York City", "Fashion"));
        e.add(new Event("Cherry Blossom Festival", "30-03-2024", "Washington D.C.", "Travel"));
        return e;
    }
}