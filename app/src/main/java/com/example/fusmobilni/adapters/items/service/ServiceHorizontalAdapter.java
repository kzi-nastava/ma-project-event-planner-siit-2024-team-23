package com.example.fusmobilni.adapters.items.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.items.service.Service;
import com.example.fusmobilni.responses.items.services.filter.ServicePaginationResponse;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ServiceHorizontalAdapter extends RecyclerView.Adapter<ServiceHorizontalAdapter.ServiceHorizontalViewHodler> {

    private List<ServicePaginationResponse> _services;

    public ServiceHorizontalAdapter() {
        this._services = new ArrayList<>();
    }

    public ServiceHorizontalAdapter(ArrayList<ServicePaginationResponse> events) {
        this._services = events;
    }

    private Bundle createBundle(Service service) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("service", service);
        return bundle;
    }

    @NonNull
    @Override
    public ServiceHorizontalAdapter.ServiceHorizontalViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_card_horizontal, parent, false);
        return new ServiceHorizontalAdapter.ServiceHorizontalViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHorizontalAdapter.ServiceHorizontalViewHodler holder, int position) {
        ServicePaginationResponse service = _services.get(position);
        holder.name.setText(service.getName());
        holder.description.setText(service.getDescription());
        holder.location.setText(service.getLocation().getCity() + ", " + service.getLocation().getStreet() + " " + service.getLocation().getStreetNumber());
        holder.category.setText(service.getCategory().getName());
        holder.price.setText(String.valueOf(service.getPrice()));

    }

    @Override
    public int getItemCount() {
        return _services.size();
    }


    public void setData(List<ServicePaginationResponse> list) {
        this._services = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public static class ServiceHorizontalViewHodler extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView description;
        public TextView location;
        public TextView category;
        public TextView price;
        public MaterialCardView _card;

        public ServiceHorizontalViewHodler(@NonNull View view) {
            super(view);
            this.name = view.findViewById(R.id.textViewServiceNameHorizontal);
            this.description = view.findViewById(R.id.serviceDescriptionHorizontal);
            this.location = view.findViewById(R.id.textViewServiceLocationHorizontal);
            this.category = view.findViewById(R.id.textViewCategory);
            this.price = view.findViewById(R.id.price);
            _card = view.findViewById(R.id.serviceCardHorizontal);
        }
    }
}