package com.example.fusmobilni.adapters.items.service;

import static com.example.fusmobilni.adapters.AdapterUtils.convertToUrisFromBase64;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.items.service.Service;
import com.example.fusmobilni.responses.items.services.home.ServiceHomeResponse;
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder> {
    private List<ServiceHomeResponse> serviceList;

    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_card, parent, false);
        return new ServicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesViewHolder holder, int position) {
        ServiceHomeResponse service = serviceList.get(position);
        holder.name.setText(service.getName());
        holder.description.setText(service.getDescription());
        holder.location.setText(service.getLocation().city + ", " + service.getLocation().street + " " + service.getLocation().streetNumber);
        holder.category.setText(service.getCategory().getName());
        holder.price.setText(String.valueOf(service.getPrice()));
        holder._card.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_service_card_to_service_details_regular, createBundle(service));
        });
        try {
            holder.image.setImageURI(convertToUrisFromBase64(holder.image.getContext(), service.getImage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Bundle createBundle(ServiceHomeResponse service) {

        Bundle bundle = new Bundle();
        bundle.putLong("serviceId", service.getId());
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
        public TextView price;
        public TextView category;
        public ImageView image;
        MaterialCardView _card;

        public ServicesViewHolder(@NonNull View view) {
            super(view);
            this.name = view.findViewById(R.id.textViewServiceTitle);
            this.description = view.findViewById(R.id.textViewServiceDescription);
            this.location = view.findViewById(R.id.textViewLocationService);
            this._card = view.findViewById(R.id.serviceVerticalCard);
            this.price = view.findViewById(R.id.price);
            this.category = view.findViewById(R.id.textViewCategory);
            this.image = view.findViewById(R.id.imageBanner);

        }
    }

    public ServicesAdapter(List<ServiceHomeResponse> services) {
        this.serviceList = services;
    }

}
