package com.example.fusmobilni.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.interfaces.DeleteServiceListener;
import com.example.fusmobilni.interfaces.EventClickListener;
import com.example.fusmobilni.model.Event;
import com.example.fusmobilni.model.Event;

import java.util.ArrayList;
import java.util.List;

public class ViewProfileEventAdapter extends RecyclerView.Adapter<ViewProfileEventAdapter.EventViewHolder>{
        private EventClickListener eventClickListener;
        private List<Event> eventList;



        public ViewProfileEventAdapter(List<Event> eventList, EventClickListener clickListener) {
            this.eventList = eventList;
            this.eventClickListener = clickListener;
        }
        public void setData(List<Event> list) {
            this.eventList = new ArrayList<>(list);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewProfileEventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.event_card_horizontal, parent, false);
            return new ViewProfileEventAdapter.EventViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewProfileEventAdapter.EventViewHolder holder, int position) {
            Event event = eventList.get(position);
            holder.title.setText(event.getTitle());
            holder.day.setText(event.getDay());
            holder.monthYear.setText(event.getMonth() + " " + event.getYear());
            holder.location.setText(event.getLocation());
            holder.eventCard.setOnClickListener(v -> eventClickListener.onEventClick(position));
        }

        @Override
        public int getItemCount() {
            return eventList.size();
        }

        public void updateServiceList(List<Event> newServiceList) {
            this.eventList = new ArrayList<>(newServiceList);
            notifyDataSetChanged();
        }

public static class EventViewHolder extends RecyclerView.ViewHolder {
    LinearLayout eventCard;
    TextView title;
    TextView day;
    TextView monthYear;
    TextView location;

    public EventViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.textViewEventNameHorizontal);
        day = itemView.findViewById(R.id.textViewDayHorizontal);
        monthYear = itemView.findViewById(R.id.textViewMonthAndYearHorizontal);
        location = itemView.findViewById(R.id.textViewEventLocationHorizontal);
        eventCard = itemView.findViewById(R.id.eventCard);

    }
}
}
