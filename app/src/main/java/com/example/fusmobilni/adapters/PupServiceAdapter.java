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
import com.example.fusmobilni.model.PrototypeService;
import com.example.fusmobilni.model.Service;

import java.util.ArrayList;
import java.util.List;

public class PupServiceAdapter extends RecyclerView.Adapter<PupServiceAdapter.ServiceViewHolder> {

    private List<PrototypeService> serviceList;


    private DeleteServiceListener clickListener;

    public PupServiceAdapter(List<PrototypeService> serviceList, DeleteServiceListener clickListener) {
        this.serviceList = serviceList;
        this.clickListener = clickListener;
    }

    public void setData(List<PrototypeService> list) {
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
        PrototypeService service = serviceList.get(position);
        holder.title.setText(service.getName());
        holder.description.setText(service.getDescription());
        holder.deleteButton.setOnClickListener(v -> clickListener.onDeleteService(position));
        holder.editButton.setOnClickListener(v -> clickListener.onUpdateService(position));
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public void updateServiceList(List<PrototypeService> newServiceList) {
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

