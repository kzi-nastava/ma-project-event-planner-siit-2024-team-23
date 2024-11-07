package com.example.fusmobilni.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.Event;
import com.example.fusmobilni.model.Service;

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
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }


    public static class ServicesViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView description;

        public ServicesViewHolder(@NonNull View view) {
            super(view);
            this.name = view.findViewById(R.id.textViewServiceTitle);
            this.description = view.findViewById(R.id.textViewServiceDescription);

        }
    }

    public ServicesAdapter(List<Service> services) {
        this.serviceList = services;
    }

}
