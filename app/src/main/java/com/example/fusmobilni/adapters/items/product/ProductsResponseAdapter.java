package com.example.fusmobilni.adapters.items.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.items.product.Product;
import com.example.fusmobilni.responses.items.products.filter.ProductPaginationResponse;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ProductsResponseAdapter extends RecyclerView.Adapter<ProductsResponseAdapter.ProductHorizontalViewHolder> {
    private List<ProductPaginationResponse> _products;

    public ProductsResponseAdapter() {

        this._products = new ArrayList<>();

    }

    public void setData(List<ProductPaginationResponse> list) {
        this._products = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    static class ProductHorizontalViewHolder extends RecyclerView.ViewHolder {
        TextView _name;
        TextView _description;
        TextView _price;
        MaterialCardView _card;
        TextView _location;
        TextView _category;


        ProductHorizontalViewHolder(View itemView) {
            super(itemView);
            _name = itemView.findViewById(R.id.textViewProductNameHorizontal);
            _description = itemView.findViewById(R.id.productDescriptionHorizontal);
            _price = itemView.findViewById(R.id.productsHorizontalPrice);
            _location = itemView.findViewById(R.id.textViewProductLocationHorizontal);
            _card = itemView.findViewById(R.id.productCardHorizontal);
            _category = itemView.findViewById(R.id.textViewCategory);
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
    public ProductsResponseAdapter.ProductHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_horizontal, parent, false);
        return new ProductsResponseAdapter.ProductHorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsResponseAdapter.ProductHorizontalViewHolder holder, int position) {
        ProductPaginationResponse product = _products.get(position);

        holder._name.setText(product.getName());
        holder._description.setText(product.getDescription());
        holder._price.setText(String.valueOf(product.getPrice()));
        holder._location.setText(product.getLocation().getCity() + ", " + product.getLocation().getStreet() + " " + product.getLocation().getStreetNumber());
        holder._category.setText(product.getCategory().getName());


    }


    @Override
    public int getItemCount() {
        return _products.size();
    }

}