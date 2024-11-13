package com.example.fusmobilni.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceHorizontalAdapter  extends RecyclerView.Adapter<ServiceHorizontalAdapter.ServiceHorizontalViewHodler>{

    private List<Service> _services;

    public ServiceHorizontalAdapter() {
        this._services = new ArrayList<>();
    }

    public ServiceHorizontalAdapter(ArrayList<Service> events) {
        this._services = new ArrayList<>(events);
    }

    @NonNull
    @Override
    public ServiceHorizontalAdapter.ServiceHorizontalViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_card_horizontal, parent, false);
        return new ServiceHorizontalAdapter.ServiceHorizontalViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHorizontalAdapter.ServiceHorizontalViewHodler holder, int position) {
        Service service = _services.get(position);
        holder.name.setText(service.getName());
        holder.description.setText(service.getDescription());
        holder.location.setText(service.getLocation());
    }

    @Override
    public int getItemCount() {
        return _services.size();
    }


    public void setData(List<Service> list) {
        this._services = new ArrayList<>(list);
        notifyDataSetChanged();
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
}
