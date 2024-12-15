package com.example.fusmobilni.adapters.items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.items.product.Product;
import com.example.fusmobilni.responses.events.GetItemResponse;
import com.example.fusmobilni.responses.events.GetItemsResponse;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemsHorizontalAdapter extends RecyclerView.Adapter<ItemsHorizontalAdapter.ItemsHorizontalViewHolder> {

    private List<GetItemResponse> _products;

    public ItemsHorizontalAdapter() {

        this._products = new ArrayList<>();

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
        TextView _location;

        ItemsHorizontalViewHolder(View itemView) {
            super(itemView);
            _name = itemView.findViewById(R.id.textViewProductNameHorizontal);
            _description = itemView.findViewById(R.id.productDescriptionHorizontal);
            _price = itemView.findViewById(R.id.productsHorizontalPrice);
            _location = itemView.findViewById(R.id.textViewProductLocationHorizontal);
            _card = itemView.findViewById(R.id.productCardHorizontal);
        }
    }

    private Bundle createBundle(Long productId) {
        Bundle bundle = new Bundle();
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
        holder._card.setOnClickListener(v -> {
            if(product.isService){
                Navigation.findNavController(v).navigate(R.id.action_service_card_to_service_details, createBundle(product.id));
            }else{
                Navigation.findNavController(v).navigate(R.id.action_product_card_to_product_details, createBundle(product.id));
            }
        });
    }


    @Override
    public int getItemCount() {
        return _products.size();
    }


}
