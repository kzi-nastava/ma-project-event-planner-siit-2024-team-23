package com.example.fusmobilni.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.Product;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductsHorizontalAdapter extends RecyclerView.Adapter<ProductsHorizontalAdapter.ProductHorizontalViewHolder> {

    private List<Product> _products;

    public ProductsHorizontalAdapter() {

        this._products = new ArrayList<>();

    }

    public void setData(List<Product> list) {
        this._products = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    static class ProductHorizontalViewHolder extends RecyclerView.ViewHolder {
        TextView _name;
        TextView _description;
        TextView _price;
        MaterialCardView _card;
        TextView _location;

        ProductHorizontalViewHolder(View itemView) {
            super(itemView);
            _name = itemView.findViewById(R.id.textViewProductNameHorizontal);
            _description = itemView.findViewById(R.id.productDescriptionHorizontal);
            _price = itemView.findViewById(R.id.productsHorizontalPrice);
            _location = itemView.findViewById(R.id.textViewProductLocationHorizontal);
            _card = itemView.findViewById(R.id.productCardHorizontal);
        }
    }

    private Bundle createBundle(Product product, boolean displayPurchase) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("product", product);
        bundle.putBoolean("displayPurchase", displayPurchase);
        return bundle;
    }

    @NonNull
    @Override
    public ProductHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_horizontal, parent, false);
        return new ProductHorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHorizontalViewHolder holder, int position) {
        Product product = _products.get(position);

        holder._name.setText(product.getName());
        holder._description.setText(product.getDescription());
        holder._price.setText(String.valueOf(product.getPrice()));
        holder._location.setText(product.getLocation());
        holder._card.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_product_card_to_product_details, createBundle(product, new Random(System.currentTimeMillis()).nextBoolean()));
        });
    }


    @Override
    public int getItemCount() {
        return _products.size();
    }


}
