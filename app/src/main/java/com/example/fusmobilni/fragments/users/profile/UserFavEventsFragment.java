package com.example.fusmobilni.fragments.users.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.users.ViewProfileEventAdapter;
import com.example.fusmobilni.databinding.FragmentUserEventsFragmentsBinding;
import com.example.fusmobilni.interfaces.EventClickListener;
import com.example.fusmobilni.model.event.Event;

import java.util.ArrayList;

public class UserFavEventsFragment extends Fragment implements EventClickListener {

    private FragmentUserEventsFragmentsBinding _binding;
    private ArrayList<Event> events;

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
        events = fillEvents();
        ViewProfileEventAdapter eventsHorizontalAdapter = new ViewProfileEventAdapter(events, this);
        listView.setAdapter(eventsHorizontalAdapter);
        eventsHorizontalAdapter.setData(events);
        return view;
    }
    private ArrayList<Event> fillEvents() {
        ArrayList<Event> e = new ArrayList<>();
        e.add(new Event("Food and Wine Tasting", "12-07-2025", "Napa Valley Vineyard", "Food", "Food"));
        e.add(new Event("Tech Innovators Conference", "15-08-2025", "Silicon Valley Expo Center", "Tech", "Tech"));
        e.add(new Event("Autumn Art and Sculpture Exhibition", "18-09-2025", "Paris Art Museum", "Art", "Art"));
        e.add(new Event("Global Startup Pitch Event", "22-12-2024", "Berlin Startup Hub", "Tech", "Tech"));
        e.add(new Event("International Film and Documentary Festival", "05-12-2025", "Toronto Film Centre", "Art", "Art"));
        e.add(new Event("New Year Gala", "01-01-2025", "Times Square", "Travel", "Travel"));
        e.add(new Event("Valentine's Day Dance", "10-02-2025", "City Hall Ballroom", "Music", "Music"));
        e.add(new Event("Winter Sports Championship", "20-02-2025", "Aspen Ski Resort", "Sports", "Sports"));
        e.add(new Event("Spring Fashion Week", "15-03-2025", "New York City", "Fashion", "Fashion"));
        e.add(new Event("Cherry Blossom Festival", "30-03-2025", "Washington D.C.", "Travel", "Travel"));
        return e;
    }

    @Override
    public void onEventClick(int position) {
        Event event = events.get(position);
        Toast.makeText(getContext(), event.getTitle(), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putParcelable("event", event);
        Navigation.findNavController(_binding.getRoot()).navigate(R.id.action_viewProfileFragment_to_eventDetailsFragment, bundle);
    }
}