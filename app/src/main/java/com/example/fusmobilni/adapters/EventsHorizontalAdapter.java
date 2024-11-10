package com.example.fusmobilni.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.Category;
import com.example.fusmobilni.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventsHorizontalAdapter extends RecyclerView.Adapter<EventsHorizontalAdapter.EventHorizontalViewHolder> implements Filterable {
    private List<Event> _eventList;
    private List<Event> _eventsFull;
    private List<Event> _pagedEvents;
    private int currentPage = 0;
    private int itemsPerPage = 5;

    public EventsHorizontalAdapter() {
        this._eventList = new ArrayList<>();
        this._eventsFull = new ArrayList<>();
        this._pagedEvents = new ArrayList<>();
    }

    public EventsHorizontalAdapter(ArrayList<Event> events) {
        this._eventList = events;
        this._eventsFull = new ArrayList<>(events);
        this._pagedEvents = new ArrayList<>(events);
    }

    public void setOriginalData(List<Event> list) {
        this._eventsFull = new ArrayList<>(list);
        notifyDataSetChanged();
    }
    public void setFilteringData(List<Event> list){
        this._eventList  = new ArrayList<>(list);
        notifyDataSetChanged();
    }
    public void setData(List<Event> list) {
        this._pagedEvents = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void loadPage(int page) {
        if (page < 0 || page * itemsPerPage >= _eventList.size()) {
            return;
        }

        currentPage = page;

        int start = page * itemsPerPage;
        int end = start + itemsPerPage;
        if(end > _eventList.size()){
            end = _eventList.size();
        }
        List<Event> pageCategories = _eventList.subList(start, end);

        this.setData(pageCategories);

    }

    public void prevPage() {
        if (currentPage > 0) {
            loadPage(currentPage - 1);
        }
    }

    public void nextPage() {
        if ((currentPage + 1) * itemsPerPage < _eventsFull.size()) {
            loadPage(currentPage + 1);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Event> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(_eventsFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Event event : _eventsFull) {
                        if (event.getTitle().toLowerCase().contains(filterPattern) ||
                                event.getLocation().toLowerCase().contains(filterPattern)) {
                            filteredList.add(event);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                _eventList.clear();
                if (results.values != null) {
                    _eventList.addAll((List<Event>) results.values);
                }
                loadPage(0);
            }
        };
    }

    public void setPageSize(int selectedItem,String currentText) {
        itemsPerPage = selectedItem;
        getFilter().filter( currentText);
    }

    static class EventHorizontalViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView day;
        TextView monthYear;
        TextView location;

        EventHorizontalViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewEventTitleHorizontal);
            day = itemView.findViewById(R.id.textViewDayHorizontal);
            monthYear = itemView.findViewById(R.id.textViewMonthAndYearHorizontal);
            location = itemView.findViewById(R.id.textViewLocationHorizontal);
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
        Event event = _pagedEvents.get(position);
        holder.title.setText(event.getTitle());
        holder.day.setText(event.get_day());
        holder.monthYear.setText(event.get_month() + " " + event.getYear());
        holder.location.setText(event.getLocation());
    }


    @Override
    public int getItemCount() {
        return _pagedEvents.size();
    }


}
