package com.example.fusmobilni.adapters.users.serviceProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.interfaces.DeleteServiceListener;
import com.example.fusmobilni.requests.services.GetServiceResponse;

import java.util.ArrayList;
import java.util.List;

public class ServiceProviderServiceAdapter extends RecyclerView.Adapter<ServiceProviderServiceAdapter.ServiceViewHolder> {

    private List<GetServiceResponse> serviceList;


    private DeleteServiceListener clickListener;

    public ServiceProviderServiceAdapter(List<GetServiceResponse> services, DeleteServiceListener clickListener) {
        this.serviceList = services;
        this.clickListener = clickListener;
    }

    public void setData(List<GetServiceResponse> list) {
        this.serviceList = new ArrayList<>(list);
        notifyDataSetChanged();
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
        GetServiceResponse service = serviceList.get(position);
        holder.title.setText(service.getName());
        holder.description.setText(service.getDescription());
        holder.deleteButton.setOnClickListener(v -> clickListener.onDeleteService(position));
        holder.editButton.setOnClickListener(v -> clickListener.onUpdateService(position));
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public void updateServiceList(List<GetServiceResponse> newServiceList) {
        this.serviceList = new ArrayList<>(newServiceList);
        notifyDataSetChanged();
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

