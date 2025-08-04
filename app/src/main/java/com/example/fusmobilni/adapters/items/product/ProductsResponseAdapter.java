package com.example.fusmobilni.adapters.items.product;

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
import com.example.fusmobilni.model.items.product.Product;
import com.example.fusmobilni.responses.items.products.filter.ProductPaginationResponse;
import com.example.fusmobilni.responses.items.services.home.ServiceHomeResponse;
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        ImageView _image;

        ProductHorizontalViewHolder(View itemView) {
            super(itemView);
            _name = itemView.findViewById(R.id.textViewProductNameHorizontal);
            _description = itemView.findViewById(R.id.productDescriptionHorizontal);
            _price = itemView.findViewById(R.id.productsHorizontalPrice);
            _location = itemView.findViewById(R.id.textViewServiceLocationHorizontal);
            _card = itemView.findViewById(R.id.productCardHorizontal);
            _category = itemView.findViewById(R.id.textViewCategory);
            _image = itemView.findViewById(R.id.productImageBannerHorizontal);
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
        holder._card.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_service_card_to_service_details_regular, createBundle(product));
        });
        try {
            holder._image.setImageURI(convertToUrisFromBase64(holder._image.getContext(),product.getImage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    private Bundle createBundle(ProductPaginationResponse service) {

        Bundle bundle = new Bundle();
        bundle.putLong("productId", service.getId());
        return bundle;
    }
    @Override
    public int getItemCount() {
        return _products.size();
    }

}
