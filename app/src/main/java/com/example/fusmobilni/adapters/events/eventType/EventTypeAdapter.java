package com.example.fusmobilni.adapters.events.eventType;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.interfaces.EventTypeListener;
import com.example.fusmobilni.model.event.eventTypes.EventType;
import com.example.fusmobilni.model.items.category.OfferingsCategory;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class EventTypeAdapter extends RecyclerView.Adapter<EventTypeAdapter.EventTypeViewHolder> {

    private final List<EventType> eventTypes;
    private final EventTypeListener eventTypeListener;

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
        holder.isActiveIndicator.setText(eventType.getActive() ? "Active" : "Deactivated");
        holder.isActiveIndicator.setTextColor(ContextCompat.getColor(
                holder.itemView.getContext(),
                eventType.getActive() ? R.color.accent_green : R.color.accent_red
        ));
        holder.deleteButton.setText(eventType.getActive() ? "Disable" : "Enable");
        holder.deleteButton.setTextColor(ContextCompat.getColor(
                holder.itemView.getContext(),
                eventType.getActive() ? R.color.accent_red : R.color.accent_green
        ));
        holder.deleteButton.setOnClickListener(v -> eventTypeListener.onDeleteEventType(position));
        holder.editButton.setOnClickListener(v -> eventTypeListener.onUpdateEventType(position));
        holder.chipGroup.removeAllViews();
        for(OfferingsCategory category: eventType.getSuggestedCategories().categories){
            Chip chip = new Chip(holder.title.getContext());
            chip.setText(category.getName());
            chip.setChipBackgroundColorResource(R.color.white);
            chip.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.primary_blue_700));
            chip.setChipCornerRadius(48f);
            chip.setChipStrokeColorResource(R.color.primary_blue_900);
            chip.setChipStrokeWidth(3f);
            holder.chipGroup.addView(chip);
        }
    }

    @Override
    public int getItemCount() {
        return eventTypes != null ? eventTypes.size() : 0;
    }
    public void updateItem(int position, EventType updatedEventType) {
        eventTypes.set(position, updatedEventType);
        notifyItemChanged(position);
    }
    public static class EventTypeViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, isActiveIndicator;
        Button editButton, deleteButton;
        ChipGroup chipGroup;

        public EventTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            isActiveIndicator = itemView.findViewById(R.id.activeIndicator);
            title = itemView.findViewById(R.id.cardTitle);
            description = itemView.findViewById(R.id.cardDescription);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            chipGroup = itemView.findViewById(R.id.chipGroup);

        }
    }
}
