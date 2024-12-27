package com.example.fusmobilni.adapters.events;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.responses.events.components.AgendaActivityResponse;
import com.example.fusmobilni.responses.events.components.EventComponentResponse;
import com.example.fusmobilni.responses.events.components.EventComponentsResponse;

import java.util.List;

public class EventComponentAdapter extends RecyclerView.Adapter<EventComponentAdapter.ComponentViewHolder> {
    private List<EventComponentResponse> components;

    public EventComponentAdapter(List<EventComponentResponse> components){
        this.components = components;
    }


    @NonNull
    @Override
    public EventComponentAdapter.ComponentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event_component, parent, false);
        return new EventComponentAdapter.ComponentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventComponentAdapter.ComponentViewHolder holder, int position) {
        EventComponentResponse componentResponse = components.get(position);
        holder.title.setText(componentResponse.itemName);
        holder.itemName.setText(componentResponse.category.getName());
        holder.estimatedBudget.setText(String.format("%s $", componentResponse.estimatedBudget));
        holder.category.setText(componentResponse.category.getDescription());
    }

    @Override
    public int getItemCount() {
        return components != null ? components.size() : 0;
    }
    public static class ComponentViewHolder extends RecyclerView.ViewHolder {
        TextView title, category, itemName, estimatedBudget;

        public ComponentViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.componentTitle);
            category = itemView.findViewById(R.id.componentCategory);
            estimatedBudget = itemView.findViewById(R.id.estimatedBudget);
            itemName = itemView.findViewById(R.id.itemName);
        }
    }
}
