package com.example.fusmobilni.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.Event;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {
    private List<Event> _eventList;

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {
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

    public static class EventsViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView day;
        public TextView monthYear;
        public TextView location;

        public EventsViewHolder(@NonNull View itemView) {

            super(itemView);
            title = itemView.findViewById(R.id.textViewServiceTitle);
            day = itemView.findViewById(R.id.textViewDay);
            monthYear = itemView.findViewById(R.id.textViewMonthAndYear);
            location = itemView.findViewById(R.id.textViewLocation);

        }
    }

    public EventsAdapter(List<Event> events) {
        _eventList = events;
    }

}
