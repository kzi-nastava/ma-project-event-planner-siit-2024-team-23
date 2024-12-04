package com.example.fusmobilni.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.Product;
import com.google.android.material.card.MaterialCardView;

import java.util.List;
import java.util.Random;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {
    private List<Product> _productList;

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_card, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        Product product = _productList.get(position);

        holder.name.setText(product.getName());
        holder.description.setText(product.getDescription());
        holder._location.setText(product.getLocation());
        holder._price.setText(String.valueOf(product.getPrice()));
        holder._card.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_product_card_to_product_details, createBundle(product, new Random(System.currentTimeMillis()).nextBoolean()));
        });
    }

    private Bundle createBundle(Product product, boolean displayPurchase) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("product", product);
        bundle.putBoolean("displayPurchase", displayPurchase);
        return bundle;
    }


    @Override
    public int getItemCount() {
        return _productList.size();
    }

    public static class ProductsViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView description;
        MaterialCardView _card;
        TextView _price;

        TextView _location;

        public ProductsViewHolder(@NonNull View view) {
            super(view);
            _card = view.findViewById(R.id.productCardVertical);
            this.name = view.findViewById(R.id.textViewProductTitle);
            this.description = view.findViewById(R.id.textViewProductDescription);
            _price = itemView.findViewById(R.id.productsPrice);
            _location = itemView.findViewById(R.id.textViewLocationService);
        }
    }

    public ProductsAdapter(List<Product> pro) {
        _productList = pro;
    }

}
