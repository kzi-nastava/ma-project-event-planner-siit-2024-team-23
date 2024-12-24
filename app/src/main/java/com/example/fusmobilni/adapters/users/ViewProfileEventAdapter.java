package com.example.fusmobilni.adapters.users;

import static com.example.fusmobilni.adapters.AdapterUtils.convertToUrisFromBase64;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.interfaces.EventClickListener;
import com.example.fusmobilni.model.event.Event;
import com.example.fusmobilni.responses.events.home.EventHomeResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewProfileEventAdapter extends RecyclerView.Adapter<ViewProfileEventAdapter.EventViewHolder>{
        private EventClickListener eventClickListener;
        private List<EventHomeResponse> eventList;



        public ViewProfileEventAdapter(List<EventHomeResponse> eventList, EventClickListener clickListener) {
            this.eventList = eventList;
            this.eventClickListener = clickListener;
        }
        public void setData(List<EventHomeResponse> list) {
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
            EventHomeResponse event = eventList.get(position);
            holder.title.setText(event.getTitle());
            holder.day.setText(event.getDate());
            holder.monthYear.setText(event.getDate());
            holder.location.setText(event.getLocation().city + ", " + event.getLocation().street);
            holder.eventType.setText(event.getType().name);
            holder.eventCard.setOnClickListener(v -> eventClickListener.onEventClick(position));
//            try {
//                holder.imageView.setImageURI(convertToUrisFromBase64(holder.imageView.getContext(), event.getImage()));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        }

        @Override
        public int getItemCount() {
            return eventList.size();
        }

        public void updateServiceList(List<EventHomeResponse> newServiceList) {
            this.eventList = new ArrayList<>(newServiceList);
            notifyDataSetChanged();
        }

public static class EventViewHolder extends RecyclerView.ViewHolder {
    LinearLayout eventCard;
    TextView title;
    TextView day;
    TextView monthYear;
    TextView location;

    TextView eventType;
    ImageView imageView;

    public EventViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.textViewEventNameHorizontal);
        day = itemView.findViewById(R.id.textViewDayHorizontal);
        monthYear = itemView.findViewById(R.id.textViewMonthAndYearHorizontal);
        location = itemView.findViewById(R.id.textViewEventLocationHorizontal);
        eventCard = itemView.findViewById(R.id.eventCard);
        eventType = itemView.findViewById(R.id.textViewEventType);
        imageView = itemView.findViewById(R.id.imageBannerHorizontal);
    }
}
}
