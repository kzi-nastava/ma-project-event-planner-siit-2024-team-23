package com.example.fusmobilni.adapters.users.serviceProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Base64;
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
import com.example.fusmobilni.requests.services.cardView.GetServiceCardResponse;
import com.example.fusmobilni.responses.items.products.home.ProductHomeResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServiceProviderProductAdapter extends RecyclerView.Adapter<ServiceProviderProductAdapter.ServiceViewHolder> {

    private List<ProductHomeResponse> serviceList;


    private DeleteServiceListener clickListener;

    public ServiceProviderProductAdapter(List<ProductHomeResponse> services, DeleteServiceListener clickListener) {
        this.serviceList = services;
        this.clickListener = clickListener;
    }

    public void setData(List<ProductHomeResponse> list) {
        this.serviceList = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_card_horizontal_sp, parent, false);
        return new ServiceViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        ProductHomeResponse service = serviceList.get(position);
        holder.title.setText(service.name);
        holder.description.setText(service.description);
        holder.deleteButton.setOnClickListener(v -> clickListener.onDeleteService(service.id));
        holder.editButton.setOnClickListener(v -> clickListener.onUpdateService(service.id));
        holder.location.setText(service.location.toString());
        holder.price.setText(service.price.toString());
        holder.category.setText(service.category.name + ", " + service.category.description);
        try {
            holder.image.setImageURI(convertToUrisFromBase64(holder.title.getContext(), service.image));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static Uri convertToUrisFromBase64(Context context, String base64String) throws IOException {
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);

        File tempFile = File.createTempFile("image_", ".jpg", context.getCacheDir());
        tempFile.deleteOnExit();

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(decodedBytes);
        }

        return Uri.fromFile(tempFile);
    }


    public static class ServiceViewHolder extends RecyclerView.ViewHolder {

        TextView title, description;
        ImageView image;
        Button editButton, deleteButton;
        TextView location, category, price;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewProductNameHorizontal);
            description = itemView.findViewById(R.id.productDescriptionHorizontal);
            image = itemView.findViewById(R.id.productImageBannerHorizontal);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            category = itemView.findViewById(R.id.textViewCategory);
            price = itemView.findViewById(R.id.productsHorizontalPrice);
            location = itemView.findViewById(R.id.textViewServiceLocationHorizontal);

        }
    }
}

