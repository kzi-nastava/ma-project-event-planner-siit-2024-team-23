package com.example.fusmobilni.adapters.events;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.event.AgendaActivity;
import com.example.fusmobilni.responses.events.components.AgendaActivityResponse;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AgendaActivityAdapter extends RecyclerView.Adapter<AgendaActivityAdapter.AgendaViewHolder> {

    private final List<AgendaActivityResponse> agendaActivities;
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public AgendaActivityAdapter(List<AgendaActivityResponse> agendaActivities) {
        this.agendaActivities = agendaActivities;
    }

    @NonNull
    @Override
    public AgendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_event_activity, parent, false);
        return new AgendaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgendaViewHolder holder, int position) {
        AgendaActivityResponse activity = agendaActivities.get(position);

        // Bind data to views
        holder.title.setText(activity.title);
        holder.description.setText(activity.description);
        String timeRange = activity.startTime + " - " + activity.endTime;
        holder.timeRange.setText(timeRange);

    }

    @Override
    public int getItemCount() {
        return agendaActivities != null ? agendaActivities.size() : 0;
    }

    public static class AgendaViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, timeRange;

        public AgendaViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.agendaTitle);
            description = itemView.findViewById(R.id.agendaDescription);
            timeRange = itemView.findViewById(R.id.agendaTimeRange);
        }
    }
}