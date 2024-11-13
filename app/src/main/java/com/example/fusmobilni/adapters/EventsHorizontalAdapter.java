package com.example.fusmobilni.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventsHorizontalAdapter extends RecyclerView.Adapter<EventsHorizontalAdapter.EventHorizontalViewHolder>  {

    private List<Event> _events;

    public EventsHorizontalAdapter() {

        this._events = new ArrayList<>();
    }
    public void setData(List<Event> list){
        this._events = new ArrayList<>(list);
        notifyDataSetChanged();
    }
    static class EventHorizontalViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView day;
        TextView monthYear;
        TextView location;

        EventHorizontalViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewEventNameHorizontal);
            day = itemView.findViewById(R.id.textViewDayHorizontal);
            monthYear = itemView.findViewById(R.id.textViewMonthAndYearHorizontal);
            location = itemView.findViewById(R.id.textViewEventLocationHorizontal);
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
        Event event = _events.get(position);
        holder.title.setText(event.getTitle());
        holder.day.setText(event.getDay());
        holder.monthYear.setText(event.getMonth() + " " + event.getYear());
        holder.location.setText(event.getLocation());
    }


    @Override
    public int getItemCount() {
        return _events.size();
    }


}
