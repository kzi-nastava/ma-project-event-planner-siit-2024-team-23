package com.example.fusmobilni.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventSelectionSpinnerAdapter extends ArrayAdapter<Event> {
    private final Context _context;
    private final List<Event> _events;

    public EventSelectionSpinnerAdapter(Context context, List<Event> events) {
        super(context, R.layout.event_dropdown_item, new ArrayList<>(events));
        _context = context;
        _events = new ArrayList<>(events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(_context).inflate(R.layout.event_dropdown_item, parent, false);

        TextView name = view.findViewById(R.id.textViewEventNameHorizontal);
        TextView day = view.findViewById(R.id.textViewDayHorizontal);
        TextView monthYear = view.findViewById(R.id.textViewMonthAndYearHorizontal);
        TextView location = view.findViewById(R.id.textViewEventLocationHorizontal);
        Event event = _events.get(position);
        name.setText(event.getTitle());
        day.setText(event.getDay());
        monthYear.setText(String.format("%s %s", event.getMonth(), event.getYear()));
        location.setText(event.getLocation());


        return view;
    }

}
