package com.example.fusmobilni.adapters.items.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.items.service.Service;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder> {
    private List<Service> serviceList;

    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_card, parent, false);
        return new ServicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesViewHolder holder, int position) {
        Service service = serviceList.get(position);
        holder.name.setText(service.getName());
        holder.description.setText(service.getDescription());
        holder.location.setText(service.getLocation());
        holder._card.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_service_card_to_service_details,createBundle(service));
        });
    }

    private Bundle createBundle(Service service) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("service", service);
        return bundle;
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }


    public static class ServicesViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView description;
        public TextView location;
        MaterialCardView _card;
        public ServicesViewHolder(@NonNull View view) {
            super(view);
            this.name = view.findViewById(R.id.textViewServiceTitle);
            this.description = view.findViewById(R.id.textViewServiceDescription);
            this.location = view.findViewById(R.id.textViewLocationService);
            this._card = view.findViewById(R.id.serviceVerticalCard);
        }
    }

    public ServicesAdapter(List<Service> services) {
        this.serviceList = services;
    }

}
