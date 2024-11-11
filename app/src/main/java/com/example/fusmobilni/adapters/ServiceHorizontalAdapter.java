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
import com.example.fusmobilni.model.Product;
import com.example.fusmobilni.model.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceHorizontalAdapter  extends RecyclerView.Adapter<ServiceHorizontalAdapter.ServiceHorizontalViewHodler> implements Filterable {
    private List<Service> _serviceList;
    private List<Service> _servicesFull;
    private List<Service> _pagedServices;
    private int _currentPage = 0;
    private int _itemsPerPage = 5;
    private String _constraint = "";
    private String _selectedCategory = "";
    private String _selectedLocation = "";
    public ServiceHorizontalAdapter() {
        this._serviceList = new ArrayList<>();
        this._servicesFull = new ArrayList<>();
        this._pagedServices = new ArrayList<>();
    }

    public ServiceHorizontalAdapter(ArrayList<Service> events) {
        this._serviceList = events;
        this._servicesFull = new ArrayList<>(events);
        this._pagedServices = new ArrayList<>(events);
    }

    @NonNull
    @Override
    public ServiceHorizontalAdapter.ServiceHorizontalViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_card_horizontal, parent, false);
        return new ServiceHorizontalAdapter.ServiceHorizontalViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHorizontalAdapter.ServiceHorizontalViewHodler holder, int position) {
        Service service = _pagedServices.get(position);
        holder.name.setText(service.getName());
        holder.description.setText(service.getDescription());
        holder.location.setText(service.getLocation());
    }

    @Override
    public int getItemCount() {
        return _pagedServices.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Service> filteredList = new ArrayList<>();

                _constraint = constraint.toString().toLowerCase().trim();
                for (Service product : _servicesFull) {
                    boolean matchesConstraint = _constraint.isEmpty() || product.getName().toLowerCase().trim().contains(_constraint);
                    boolean matchesLocation = _selectedLocation.isEmpty() || product.getLocation().toLowerCase().trim().equals(_selectedLocation);

                    boolean matchesCategory = _selectedCategory.isEmpty() || product.getCategory().toLowerCase().trim().equals(_selectedCategory);
                    if (matchesConstraint && matchesLocation  && matchesCategory) {
                        filteredList.add(product);
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                _serviceList.clear();
                if (results.values != null) {
                    _serviceList.addAll((List<Service>) results.values);
                }
                loadPage(0);
            }
        };
    }

    public void setOriginalData(List<Service> list) {
        this._servicesFull = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void setFilteringData(List<Service> list) {
        this._serviceList = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void setData(List<Service> list) {
        this._pagedServices = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    private void applyFilters() {
        getFilter().filter(_constraint);
    }

    public void loadPage(int page) {
        if (page < 0) {
            return;
        }


        _currentPage = page;
        int start = page * _itemsPerPage;
        int end = start + _itemsPerPage;
        if (end > _serviceList.size()) {
            end = _serviceList.size();
        }
        if (start > end) {
            _currentPage -= 1;
            return;
        }
        List<Service> pageCategories = _serviceList.subList(start, end);

        this.setData(pageCategories);

    }

    public void prevPage() {
        if (_currentPage > 0) {
            loadPage(_currentPage - 1);
        }
    }

    public void nextPage() {
        if ((_currentPage + 1) * _itemsPerPage < _servicesFull.size()) {
            loadPage(_currentPage + 1);
        }
    }

    public static class ServiceHorizontalViewHodler extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView description;
        public TextView location;

        public ServiceHorizontalViewHodler(@NonNull View view) {
            super(view);
            this.name = view.findViewById(R.id.textViewServiceNameHorizontal);
            this.description = view.findViewById(R.id.serviceDescriptionHorizontal);
            this.location = view.findViewById(R.id.textViewServiceLocationHorizontal);
        }
    }
    public void setFilters(@NonNull String constraint, String category, String location) {

        _selectedLocation = location.toLowerCase().trim();
        _selectedCategory = category.toLowerCase().trim();
        _constraint = constraint.toLowerCase().trim();
        notifyDataSetChanged();
        applyFilters();
    }
    public void resetFilters() {
        _constraint = "";
        _selectedLocation = "";
        _selectedCategory = "";
    }

    public void setPageSize(int selectedItem, String currentText) {
        _itemsPerPage = selectedItem;
        getFilter().filter(currentText);
    }

    public ServiceHorizontalAdapter(List<Service> services) {
        this._serviceList = services;
    }

}
