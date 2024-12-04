package com.example.fusmobilni.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.AgendaActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AgendaActivityEditableAdapter extends RecyclerView.Adapter<AgendaActivityEditableAdapter.AgendaViewHolder> {

private final List<AgendaActivity> agendaActivities;
private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

public AgendaActivityEditableAdapter(List<AgendaActivity> agendaActivities) {
    this.agendaActivities = agendaActivities;
}
    public void addAgendaActivity(AgendaActivity activity) {
        agendaActivities.add(activity); // Add the new category to the list
        notifyItemInserted(agendaActivities.size() - 1); // Notify the adapter to refresh the view
    }

@NonNull
@Override
public AgendaActivityEditableAdapter.AgendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.fragment_item_activity_editable, parent, false);
    return new AgendaActivityEditableAdapter.AgendaViewHolder(view);
}

@Override
public void onBindViewHolder(@NonNull AgendaActivityEditableAdapter.AgendaViewHolder holder, int position) {
    AgendaActivity activity = agendaActivities.get(position);

    // Bind data to views
    holder.title.setText(activity.getTitle());
    holder.description.setText(activity.getDescription());
    String timeRange = timeFormat.format(activity.getStartTime()) + " - " + timeFormat.format(activity.getEndTime());
    holder.timeRange.setText(timeRange);
    holder.deleteView.setOnClickListener(v->{
            agendaActivities.remove(position);
            notifyDataSetChanged();
        });

}

@Override
public int getItemCount() {
    return agendaActivities != null ? agendaActivities.size() : 0;
}

public static class AgendaViewHolder extends RecyclerView.ViewHolder {
    TextView title, description, timeRange, deleteView;

    public AgendaViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.agendaTitle);
        description = itemView.findViewById(R.id.agendaDescription);
        timeRange = itemView.findViewById(R.id.agendaTimeRange);
        deleteView = itemView.findViewById(R.id.deleteButton);
    }
}
}
