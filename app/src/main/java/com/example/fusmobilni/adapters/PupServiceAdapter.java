package com.example.fusmobilni.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fusmobilni.R;
import com.example.fusmobilni.model.DummyService;
import com.example.fusmobilni.model.Service;

import java.util.List;

public class PupServiceAdapter extends RecyclerView.Adapter<PupServiceAdapter.ServiceViewHolder> {

    private List<DummyService> serviceList;

    public PupServiceAdapter(List<DummyService> serviceList) {
        this.serviceList = serviceList;
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
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
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

