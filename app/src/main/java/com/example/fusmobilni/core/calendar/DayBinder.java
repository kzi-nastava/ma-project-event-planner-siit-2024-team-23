package com.example.fusmobilni.core.calendar;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.fusmobilni.R;
import com.example.fusmobilni.interfaces.ICalendarCallback;
import com.example.fusmobilni.responses.events.home.EventHomeResponse;
import com.example.fusmobilni.responses.events.home.EventsHomeResponse;
import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.core.DayPosition;
import com.kizitonwose.calendar.view.MonthDayBinder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayBinder implements MonthDayBinder<DayViewContainer> {
    private List<EventHomeResponse> _userEvents;
    private Map<LocalDate, List<EventHomeResponse>> eventMap;
    private ICalendarCallback callback;
    private Context _context;
    public DayBinder(List<EventHomeResponse> events, Context context, ICalendarCallback callback){
        this._userEvents = events;
        this._context = context;
        this.callback = callback;
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

        List<EventHomeResponse> events = eventMap.get(day.getDate());
        if(events != null){
            container.textView.setBackgroundResource(R.drawable.circle_today);
            container.textView.setTextColor(Color.WHITE);
            container.textView.setOnClickListener(v -> {
                callback.onCalendarDateClick(new EventsHomeResponse(events));
                //TODO
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("event", event);
//                Navigation.findNavController(container.textView).navigate(R.id.action_userCalendarFragment_to_eventDetailsFragment, bundle);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        eventMap = new HashMap<>();

        for (EventHomeResponse event : _userEvents) {
            try {
                LocalDate date = LocalDate.parse(event.getDate(), formatter);
                eventMap.computeIfAbsent(date, k -> new ArrayList<>()).add(event);
            } catch (DateTimeParseException e) {
                Log.e("EventPreprocess", "Invalid date format: " + event.getDate(), e);
                Toast.makeText(_context,
                        "Invalid date format in event: " + event.getDate(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
