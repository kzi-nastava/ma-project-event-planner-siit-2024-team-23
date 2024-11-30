package com.example.fusmobilni.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.interfaces.CategoryListener;
import com.example.fusmobilni.model.OfferingsCategory;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<OfferingsCategory> categories;


    private CategoryListener clickListener;

    public CategoryAdapter(List<OfferingsCategory> categories, CategoryListener clickListener) {
        this.categories = categories;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_card, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        OfferingsCategory category = categories.get(position);
        holder.title.setText(category.getName());
        holder.description.setText(category.getDescription());
        holder.deleteButton.setOnClickListener(v -> clickListener.onDeleteCategory(position));
        holder.editButton.setOnClickListener(v -> clickListener.onUpdateCategory(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView title, description;
        Button editButton, deleteButton;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cardTitle);
            description = itemView.findViewById(R.id.cardDescription);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

        }
    }
}