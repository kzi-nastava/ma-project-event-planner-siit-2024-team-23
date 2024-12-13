package com.example.fusmobilni.adapters.events.event;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.event.Event;
import com.example.fusmobilni.responses.events.filter.EventPaginationResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventsHorizontalAdapter extends RecyclerView.Adapter<EventsHorizontalAdapter.EventHorizontalViewHolder> {
    private static HashMap MonthMap = new HashMap<String, String>();

    static {
        MonthMap.put("01", "Jan");
        MonthMap.put("02", "Feb");
        MonthMap.put("03", "Mar");
        MonthMap.put("04", "Apr");
        MonthMap.put("05", "May");
        MonthMap.put("06", "Jun");
        MonthMap.put("07", "Jul");
        MonthMap.put("08", "Aug");
        MonthMap.put("09", "Sept");
        MonthMap.put("10", "Oct");
        MonthMap.put("11", "Nov");
        MonthMap.put("12", "Dec");
    }

    private List<EventPaginationResponse> _events;

    public EventsHorizontalAdapter() {

        this._events = new ArrayList<>();
    }

    public void setData(List<EventPaginationResponse> list) {
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
        EventPaginationResponse event = _events.get(position);

        String[] dateParts = event.getDate().split("-");
        holder.title.setText(event.getTitle());
        holder.day.setText(dateParts[2]);
        holder.monthYear.setText(MonthMap.get(dateParts[1]) + " " + dateParts[0]);
        holder.location.setText(event.getLocation().getCity() + ", " + event.getLocation().getStreet() + " " + event.getLocation().getStreetNumber());
    }


    @Override
    public int getItemCount() {
        return _events.size();
    }


}
