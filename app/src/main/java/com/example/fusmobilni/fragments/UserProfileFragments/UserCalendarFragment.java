package com.example.fusmobilni.fragments.UserProfileFragments;

import static com.kizitonwose.calendar.core.ExtensionsKt.firstDayOfWeekFromLocale;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fusmobilni.adapters.EventsHorizontalAdapter;
import com.example.fusmobilni.adapters.ViewProfileEventAdapter;
import com.example.fusmobilni.core.calendar.DayBinder;
import com.example.fusmobilni.core.calendar.MonthHeaderBinder;
import com.example.fusmobilni.databinding.FragmentUserCalendarBinding;
import com.example.fusmobilni.interfaces.EventClickListener;
import com.example.fusmobilni.model.Event;
import com.kizitonwose.calendar.view.CalendarView;
import java.time.YearMonth;
import java.util.ArrayList;


public class UserCalendarFragment extends Fragment implements EventClickListener {
    private FragmentUserCalendarBinding _binding;
    private CalendarView _calendar;
    private ArrayList<Event> _events;

    public UserCalendarFragment() {
        // Required empty public constructor
    }
    public static UserCalendarFragment newInstance() {
        return new UserCalendarFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentUserCalendarBinding.inflate(getLayoutInflater());
        ProgressBar loadingSpinner = _binding.loadingSpinner;

        loadingSpinner.setVisibility(View.VISIBLE);

        // Create a background thread to perform the heavy work
        new Thread(() -> {
            // Perform data filling or other heavy tasks here
            _events = fillEvents();

            requireActivity().runOnUiThread(() -> {
                if(_events != null &&  !_events.isEmpty()){

                loadingSpinner.setVisibility(View.GONE);

                RecyclerView eventList = _binding.eventsList;
                ViewProfileEventAdapter eventsHorizontalAdapter = new ViewProfileEventAdapter(_events, this);
                eventList.setAdapter(eventsHorizontalAdapter);

                _calendar = _binding.calendarView;
                _calendar.setup(
                        YearMonth.now(),
                        YearMonth.now().plusMonths(12),
                        firstDayOfWeekFromLocale()
                );
                _calendar.scrollToMonth(YearMonth.now());
                _calendar.setMonthHeaderBinder(new MonthHeaderBinder());
                _calendar.setDayBinder(new DayBinder(_events, getContext()));
                }

            });
        }).start();

        return _binding.getRoot();
    }
    // TODO do the pagination!!!!
    private ArrayList<Event> fillEvents() {
        ArrayList<Event> e = new ArrayList<>();
        e.add(new Event("Food and Wine Tasting", "12-07-2025", "Napa Valley Vineyard", "Food"));
        e.add(new Event("Tech Innovators Conference", "15-08-2025", "Silicon Valley Expo Center", "Tech"));
        e.add(new Event("Autumn Art and Sculpture Exhibition", "18-09-2025", "Paris Art Museum", "Art"));
        e.add(new Event("Global Startup Pitch Event", "22-12-2024", "Berlin Startup Hub", "Tech"));
        e.add(new Event("International Film and Documentary Festival", "05-12-2025", "Toronto Film Centre", "Art"));
        e.add(new Event("New Year Gala", "01-01-2025", "Times Square", "Travel"));
        e.add(new Event("Valentine's Day Dance", "10-02-2025", "City Hall Ballroom", "Music"));
        e.add(new Event("Winter Sports Championship", "20-02-2025", "Aspen Ski Resort", "Sports"));
        e.add(new Event("Spring Fashion Week", "15-03-2025", "New York City", "Fashion"));
        e.add(new Event("Cherry Blossom Festival", "30-03-2025", "Washington D.C.", "Travel"));
        return e;
    }

    @Override
    public void onEventClick(int position) {
        Event event = _events.get(position);
        Toast.makeText(getContext(), event.getTitle(), Toast.LENGTH_SHORT).show();
    }
}



