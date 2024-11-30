package com.example.fusmobilni.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.interfaces.EventTypeListener;
import com.example.fusmobilni.model.EventType;

import java.util.List;

public class EventTypeAdapter extends RecyclerView.Adapter<EventTypeAdapter.EventTypeViewHolder> {

    private List<EventType> eventTypes;
    private EventTypeListener eventTypeListener;

    public EventTypeAdapter(List<EventType> eventTypes, EventTypeListener eventTypeListener){
        this.eventTypes = eventTypes;
        this.eventTypeListener = eventTypeListener;
    }

    @NonNull
    @Override
    public EventTypeAdapter.EventTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event_type_card, parent, false);
        return new EventTypeAdapter.EventTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventTypeAdapter.EventTypeViewHolder holder, int position) {
        EventType eventType = eventTypes.get(position);
        holder.title.setText(eventType.getName());
        holder.description.setText(eventType.getDescription());
        holder.deleteButton.setOnClickListener(v -> eventTypeListener.onDeleteEventType(position));
        holder.editButton.setOnClickListener(v -> eventTypeListener.onUpdateEventType(position));
    }

    @Override
    public int getItemCount() {
        return eventTypes != null ? eventTypes.size() : 0;
    }
    public static class EventTypeViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        Button editButton, deleteButton;

        public EventTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cardTitle);
            description = itemView.findViewById(R.id.cardDescription);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

        }
    }
}
