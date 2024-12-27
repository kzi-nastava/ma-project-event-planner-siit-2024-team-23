package com.example.fusmobilni.adapters.users.eventOrganizer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.interfaces.EOEventClickListener;
import com.example.fusmobilni.model.event.Event;
import com.example.fusmobilni.responses.events.home.EventHomeResponse;
import com.example.fusmobilni.responses.events.home.EventPreviewResponse;
import com.example.fusmobilni.responses.events.home.EventsHomeResponse;

import java.util.ArrayList;
import java.util.List;

public class EventOrganizerEventAdapter extends RecyclerView.Adapter<EventOrganizerEventAdapter.EventViewHolder>{
        private final EOEventClickListener eventClickListener;
        private List<EventPreviewResponse> eventList;



        public EventOrganizerEventAdapter(List<EventPreviewResponse> eventList, EOEventClickListener clickListener) {
            this.eventList = eventList;
            this.eventClickListener = clickListener;
        }
        public void setData(List<EventPreviewResponse> list) {
            this.eventList = new ArrayList<>(list);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public EventOrganizerEventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_e_o_event_card_horizontal, parent, false);
            return new EventOrganizerEventAdapter.EventViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EventOrganizerEventAdapter.EventViewHolder holder, int position) {
            EventPreviewResponse event = eventList.get(position);
            holder.title.setText(event.getTitle());
            holder.day.setText(R.string.see);
            holder.monthYear.setText(R.string.details);
            holder.location.setText(event.getLocation());
            holder.eventCard.setOnClickListener(v -> eventClickListener.onEventClick(position));
            holder.editButton.setOnClickListener(v-> eventClickListener.onEditClick(position));
            holder.statsButton.setOnClickListener(v-> eventClickListener.onStatsClick(position));
        }

        @Override
        public int getItemCount() {
            return eventList.size();
        }

        public void updateServiceList(List<EventPreviewResponse> newServiceList) {
            this.eventList = new ArrayList<>(newServiceList);
            notifyDataSetChanged();
        }

public static class EventViewHolder extends RecyclerView.ViewHolder {
    LinearLayout eventCard;
    TextView title;
    TextView day;
    TextView monthYear;
    TextView location;
    Button editButton, statsButton;
    public EventViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.textViewEventNameHorizontal);
        day = itemView.findViewById(R.id.textViewDayHorizontal);
        monthYear = itemView.findViewById(R.id.textViewMonthAndYearHorizontal);
        location = itemView.findViewById(R.id.textViewEventLocationHorizontal);
        eventCard = itemView.findViewById(R.id.eventCard);
        editButton = itemView.findViewById(R.id.editButton);
        statsButton = itemView.findViewById(R.id.statsButton);
    }
}
}
