package com.example.fusmobilni.adapters;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.Service;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ServiceHorizontalAdapter extends RecyclerView.Adapter<ServiceHorizontalAdapter.ServiceHorizontalViewHodler> {

    private List<Service> _services;

    public ServiceHorizontalAdapter() {
        this._services = new ArrayList<>();
    }

    public ServiceHorizontalAdapter(ArrayList<Service> events) {
        this._services = new ArrayList<>(events);
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
        Service service = _services.get(position);
        holder.name.setText(service.getName());
        holder.description.setText(service.getDescription());
        holder.location.setText(service.getLocation());
        holder._card.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_service_card_to_service_details, createBundle(service));
        });
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
        public MaterialCardView _card;

        public ServiceHorizontalViewHodler(@NonNull View view) {
            super(view);
            this.name = view.findViewById(R.id.textViewServiceNameHorizontal);
            this.description = view.findViewById(R.id.serviceDescriptionHorizontal);
            this.location = view.findViewById(R.id.textViewServiceLocationHorizontal);
            _card = view.findViewById(R.id.serviceCardHorizontal);
        }
    }
}
