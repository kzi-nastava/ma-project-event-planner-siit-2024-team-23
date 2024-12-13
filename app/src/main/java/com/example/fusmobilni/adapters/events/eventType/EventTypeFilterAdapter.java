package com.example.fusmobilni.adapters.events.eventType;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.event.EventType;
import com.example.fusmobilni.responses.events.EventTypeResponse;

import java.util.ArrayList;
import java.util.List;

public class EventTypeFilterAdapter extends RecyclerView.Adapter<EventTypeFilterAdapter.EventTypeViewHolder> {
    private List<EventTypeResponse> _eventTypes;

    private int _selectedEventType = -1;

    public EventTypeFilterAdapter(List<EventTypeResponse> responses) {
        _eventTypes = responses;
    }

    public EventTypeFilterAdapter() {
        _eventTypes = new ArrayList<>();
    }
    public EventTypeResponse getSelectedEventTypeByIndex(){
        if(_selectedEventType == -1){
            return null;
        }
        return _eventTypes.get(_selectedEventType);
    }
    public EventTypeResponse getSelectedEventType() {
        if (_selectedEventType == -1) {
            return null;
        }

        return _eventTypes.get(getEventTypeById(_selectedEventType));
    }

    public void setSelectedEventType(int index) {
        _selectedEventType = index;
    }

    public void setSelectedEventType(EventTypeResponse type) {
        int index = getEventTypeById(type.getId());
        _selectedEventType = index;
        notifyDataSetChanged();
    }

    private int getEventTypeById(Long id) {
        for (int i = 0; i < _eventTypes.size(); ++i) {
            if (_eventTypes.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private int getEventTypeById(int id) {
        for (int i = 0; i < _eventTypes.size(); ++i) {
            if (_eventTypes.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public boolean isSelected(EventTypeResponse type) {
        if (_selectedEventType == -1) {
            return false;
        }
        return _eventTypes.get(_selectedEventType).getId() == type.getId();
    }

    @NonNull
    @Override
    public EventTypeFilterAdapter.EventTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_type_filter_card, parent, false);
        return new EventTypeFilterAdapter.EventTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventTypeFilterAdapter.EventTypeViewHolder holder, int position) {
        EventTypeResponse eventType = _eventTypes.get(position);
        holder.typeIcon.setImageResource(isSelected(eventType) ? R.drawable.category : R.drawable.category_inactive);
        holder.typeName.setText(eventType.getName());

        holder.backGround.setBackgroundResource(isSelected(eventType) ? R.drawable.circle_background_primary : R.drawable.circle_background_secondary);
        holder.backGround.setElevation(isSelected(eventType) ? 0f : 10f);

        holder.itemView.setOnClickListener(v -> {
            if (isSelected(eventType)) {
                _selectedEventType = -1;
            } else {
                _selectedEventType = getEventTypeById(Integer.parseInt(String.valueOf(eventType.getId())));
            }
            notifyDataSetChanged();
        });
    }

    public void resetTypes() {
        _selectedEventType = -1;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return _eventTypes.size();
    }

    public static class EventTypeViewHolder extends RecyclerView.ViewHolder {
        ImageView typeIcon;
        TextView typeName;

        ConstraintLayout backGround;

        public EventTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            typeIcon = itemView.findViewById(R.id.type_image);
            typeName = itemView.findViewById(R.id.type_text);
            backGround = itemView.findViewById(R.id.category_image_background);
        }
    }
}
