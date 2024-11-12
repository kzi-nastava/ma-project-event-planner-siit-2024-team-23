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
import com.example.fusmobilni.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventsHorizontalAdapter extends RecyclerView.Adapter<EventsHorizontalAdapter.EventHorizontalViewHolder> implements Filterable {
    private List<Event> _eventList;
    private List<Event> _eventsFull;
    private List<Event> _pagedEvents;
    private String _constraint = "";
    private int _currentPage = 0;
    private int _itemsPerPage = 5;

    private String _selectedCategory = "";
    private String _selectedLocation = "";
    private String _selectedDate = "";

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

    public void setFilteringData(List<Event> list) {
        this._eventList = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void setData(List<Event> list) {
        this._pagedEvents = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void setFilters(String constraint, String category, String location, String date) {
        _selectedCategory = category.toLowerCase().trim();
        _selectedDate = date.toLowerCase().trim();
        _selectedLocation = location.toLowerCase().trim();
        _constraint = constraint.toLowerCase().trim();
        notifyDataSetChanged();
        applyFilters();
    }

    private void applyFilters() {
        getFilter().filter(_constraint);
    }

    public void loadPage(int page) {
        if (page < 0 || page*_itemsPerPage> _eventList.size()) {
            return;
        }

        _currentPage = page;

        int start = page * _itemsPerPage;
        int end = start + _itemsPerPage;
        if (end > _eventList.size()) {
            end = _eventList.size();
        }

        List<Event> pageCategories = _eventList.subList(start, end);

        this.setData(pageCategories);

    }

    public void prevPage() {
        if (_currentPage > 0) {
            loadPage(_currentPage - 1);
        }
    }

    public void nextPage() {
        if ((_currentPage + 1) * _itemsPerPage < _eventsFull.size()) {
            loadPage(_currentPage + 1);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Event> filteredList = new ArrayList<>();

                _constraint = constraint.toString().toLowerCase().trim();
                for (Event event : _eventsFull) {
                    boolean matchesConstraint = _constraint.isEmpty() || event.getTitle().toLowerCase().contains(_constraint);
                    boolean matchesLocation = _selectedLocation.isEmpty() || event.getLocation().toLowerCase().equals(_selectedLocation);
                    boolean matchesCategory = _selectedCategory.isEmpty() || event.getCategory().toLowerCase().equals(_selectedCategory);
                    boolean matchesDate = _selectedDate.isEmpty() || event.getDate().toLowerCase().equals(_selectedDate);
                    if (matchesConstraint && matchesLocation && matchesCategory && matchesDate) {
                        filteredList.add(event);
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

    public void resetFilters() {
        _constraint = "";
        _selectedLocation = "";
        _selectedDate = "";
        _selectedCategory = "";
    }

    public void setPageSize(int selectedItem, String currentText) {
        _itemsPerPage = selectedItem;
        getFilter().filter(currentText);
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
        Event event = _pagedEvents.get(position);
        holder.title.setText(event.getTitle());
        holder.day.setText(event.getDay());
        holder.monthYear.setText(event.getMonth() + " " + event.getYear());
        holder.location.setText(event.getLocation());
    }


    @Override
    public int getItemCount() {
        return _pagedEvents.size();
    }


}
