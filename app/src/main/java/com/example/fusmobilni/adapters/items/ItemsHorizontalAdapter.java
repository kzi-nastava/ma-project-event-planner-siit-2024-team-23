package com.example.fusmobilni.adapters.items;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.items.product.Product;
import com.example.fusmobilni.responses.events.GetItemResponse;
import com.example.fusmobilni.responses.events.GetItemsResponse;
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemsHorizontalAdapter extends RecyclerView.Adapter<ItemsHorizontalAdapter.ItemsHorizontalViewHolder> {

    private List<GetItemResponse> _products;
    private Long eventId;
    private double estimatedBudget;

    public ItemsHorizontalAdapter(Long eventId, double estimatedBudget) {

        this._products = new ArrayList<>();
        this.eventId = eventId;
        this.estimatedBudget = estimatedBudget;

    }


    public void setData(List<GetItemResponse> list) {
        this._products = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    static class ItemsHorizontalViewHolder extends RecyclerView.ViewHolder {
        TextView _name;
        TextView _description;
        TextView _price;
        MaterialCardView _card;

        ImageView _image;

        ItemsHorizontalViewHolder(View itemView) {
            super(itemView);
            _name = itemView.findViewById(R.id.textViewProductNameHorizontal);
            _description = itemView.findViewById(R.id.productDescriptionHorizontal);
            _price = itemView.findViewById(R.id.productsHorizontalPrice);
            _card = itemView.findViewById(R.id.productCardHorizontal);
            _image = itemView.findViewById(R.id.productImageBannerHorizontal);
        }
    }

    private Bundle createBundle(Long productId) {
        Bundle bundle = new Bundle();
        bundle.putLong("eventId", eventId);
        bundle.putDouble("estimatedBudget", estimatedBudget);
        bundle.putLong("productId", productId);
        return bundle;
    }

    @NonNull
    @Override
    public ItemsHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_horizontal, parent, false);
        return new ItemsHorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsHorizontalViewHolder holder, int position) {
        GetItemResponse product = _products.get(position);

        holder._name.setText(product.name);
        holder._description.setText(product.description);
        holder._price.setText(String.valueOf(product.price));
        try {
            holder._image.setImageURI(this.convertToUrisFromBase64(holder._name.getContext(), product.image));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        holder._card.setOnClickListener(v -> {
            if(product.isService){
                Navigation.findNavController(v).navigate(R.id.action_service_card_to_service_details, createBundle(product.id));
            }else{
                //Log.d("Tag", "Estimated" + String.valueOf(estimatedBudget));
                Navigation.findNavController(v).navigate(R.id.action_product_card_to_product_details, createBundle(product.id));
            }
        });
    }


    @Override
    public int getItemCount() {
        return _products.size();
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
}
