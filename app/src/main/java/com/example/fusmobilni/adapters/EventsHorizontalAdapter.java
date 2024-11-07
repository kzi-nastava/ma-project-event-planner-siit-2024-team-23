package com.example.fusmobilni.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventsHorizontalAdapter extends RecyclerView.Adapter<EventsHorizontalAdapter.EventHorizontalViewHolder> implements Filterable {
    private List<Event> _eventList;
    private List<Event> _eventsFull;

    public EventsHorizontalAdapter(ArrayList<Event> events) {
        this._eventList = events;
        this._eventsFull = new ArrayList<>(events);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Event> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(_eventsFull);
                } else {
                    String filterPatter = constraint.toString().toLowerCase().trim();
                    for (Event event : _eventsFull) {
                        if (event.getTitle().toLowerCase().contains(filterPatter)) {
                            filteredList.add(event);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                _eventList.clear();
                _eventList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }

    static class EventHorizontalViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView day;
        TextView monthYear;
        TextView location;

        EventHorizontalViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewEventTitleHorizontal);
            day = itemView.findViewById(R.id.textViewDayHorizontal);
            monthYear = itemView.findViewById(R.id.textViewMonthAndYearHorizontal);
            location = itemView.findViewById(R.id.textViewLocationHorizontal);
        }
    }

    @NonNull
    @Override
    public EventHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card_horizontal, parent, false);
        return new EventHorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHorizontalViewHolder holder, int position) {
        Event event = _eventList.get(position);
        holder.title.setText(event.getTitle());
        holder.day.setText(event.get_day());
        holder.monthYear.setText(event.get_month() + " " + event.getYear());
        holder.location.setText(event.getLocation());
    }


    @Override
    public int getItemCount() {
        return _eventList.size();
    }


}
