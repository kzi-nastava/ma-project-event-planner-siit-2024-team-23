package com.example.fusmobilni.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fusmobilni.R;
import com.example.fusmobilni.interfaces.DeleteServiceListener;
import com.example.fusmobilni.model.DummyService;
import com.example.fusmobilni.model.Event;
import com.example.fusmobilni.model.Service;

import java.util.ArrayList;
import java.util.List;

public class PupServiceAdapter extends RecyclerView.Adapter<PupServiceAdapter.ServiceViewHolder> implements Filterable {

    private List<DummyService> serviceList;

    private List<DummyService> filterableServices;

    private DeleteServiceListener clickListener;

    public PupServiceAdapter(List<DummyService> serviceList, DeleteServiceListener clickListener) {
        this.serviceList = serviceList;
        this.filterableServices = new ArrayList<>(serviceList);
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pup_service_card, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        DummyService service = serviceList.get(position);
        holder.title.setText(service.getTitle());
        holder.description.setText(service.getDescription());
        holder.deleteButton.setOnClickListener(v -> clickListener.onDeleteService(position));
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public void updateServiceList(List<DummyService> newServiceList) {
        this.serviceList = new ArrayList<>(newServiceList);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<DummyService> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(filterableServices);
                } else {
                    String filterPatter = constraint.toString().toLowerCase().trim();
                    for (DummyService service : filterableServices) {
                        if (service.getTitle().toLowerCase().contains(filterPatter)) {
                            filteredList.add(service);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                serviceList.clear();
                serviceList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {

        TextView title, description;
        ImageView image;
        Button editButton, deleteButton;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cardTitle);
            description = itemView.findViewById(R.id.cardDescription);
            image = itemView.findViewById(R.id.cardImage);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

        }
    }
}

