package com.example.fusmobilni.core.calendar;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.event.Event;
import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.core.DayPosition;
import com.kizitonwose.calendar.view.MonthDayBinder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DayBinder implements MonthDayBinder<DayViewContainer> {
    private ArrayList<Event> _userEvents;
    private Map<LocalDate, Event> eventMap;
    private Context _context;
    public DayBinder(ArrayList<Event> events, Context context){
        this._userEvents = events;
        this._context = context;
        // TODO after fetching the real data remove this and just use the preprocessing in the setter
        preprocessEvents();
    }
    @NonNull
    @Override
    public DayViewContainer create(@NonNull View view) {
        return new DayViewContainer(view);
    }
    @Override
    public void bind(@NonNull DayViewContainer container, @NonNull CalendarDay day) {
        container.textView.setText(String.valueOf(day.getDate().getDayOfMonth()));

        Event event = eventMap.get(day.getDate());
        if(event != null){
            container.textView.setBackgroundResource(R.drawable.circle_today);
            container.textView.setTextColor(Color.WHITE);
            // TODO here redirect to the concrete event page!
            container.textView.setOnClickListener(v -> {
                Toast.makeText(container.textView.getContext(),
                        event.getTitle(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putParcelable("event", event);
                Navigation.findNavController(container.textView).navigate(R.id.action_userCalendarFragment_to_eventDetailsFragment, bundle);
            });
        }else{
            container.textView.setBackground(null);
            if (day.getPosition() == DayPosition.MonthDate) {
                // Current month's dates
                container.textView.setTextColor(Color.BLACK);
            } else {
                // outDates and inDates
                container.textView.setTextColor(ContextCompat.getColor(container.textView.getContext(), R.color.primary_blue_300));
                container.textView.setBackground(null);
            }
        }
    }

    private void preprocessEvents() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        eventMap = new HashMap<>();

        for (Event event : _userEvents) {
            try {
                LocalDate date = LocalDate.parse(event.getDate(), formatter);
                eventMap.put(date, event);
            } catch (DateTimeParseException e) {
                Log.e("EventPreprocess", "Invalid date format: " + event.getDate(), e);
                Toast.makeText(_context,
                        "Invalid date format in event: " + event.getDate(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setUserEvents(ArrayList<Event> userEvents) {
        this._userEvents = userEvents;
        preprocessEvents();
    }


}
