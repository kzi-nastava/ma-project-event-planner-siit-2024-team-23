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
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.items.product.Product;
import com.example.fusmobilni.responses.items.products.home.ProductHomeResponse;
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {
    private List<ProductHomeResponse> _productList;

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_card, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        ProductHomeResponse product = _productList.get(position);

        holder.name.setText(product.getName());
        holder.description.setText(product.getDescription());
        holder._location.setText(product.getLocation().getCity() + ", " + product.getLocation().getStreet() + " " + product.getLocation().getStreetNumber());
        holder._price.setText(String.valueOf(product.getPrice()));
        holder.category.setText(product.getCategory().getName());
        try {
            holder._image.setImageURI(convertToUrisFromBase64(holder._image.getContext(),product.getImage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
        public TextView category;
        public MaterialCardView _card;
        public TextView _price;
        public TextView _location;
        public ImageView _image;
        public ProductsViewHolder(@NonNull View view) {
            super(view);
            _card = view.findViewById(R.id.productCardVertical);
            this.name = view.findViewById(R.id.textViewProductTitle);
            this.description = view.findViewById(R.id.textViewProductDescription);
            _price = view.findViewById(R.id.productsPrice);
            _location = view.findViewById(R.id.textViewLocationService);
            category = view.findViewById(R.id.textViewCategory);
            _image = view.findViewById(R.id.imageBanner);
        }
    }

    public ProductsAdapter(List<ProductHomeResponse> pro) {
        _productList = pro;
    }

}
