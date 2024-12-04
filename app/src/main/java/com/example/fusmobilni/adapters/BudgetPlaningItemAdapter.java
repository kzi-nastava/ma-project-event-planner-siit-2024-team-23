package com.example.fusmobilni.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.interfaces.OnCategoryClickListener;
import com.example.fusmobilni.model.OfferingsCategory;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.util.List;

public class BudgetPlaningItemAdapter extends RecyclerView.Adapter<BudgetPlaningItemAdapter.ViewHolder> {
    private List<OfferingsCategory> categories;
    private Context context;

    private OnCategoryClickListener categoryClickListener;
    public BudgetPlaningItemAdapter(Context context, List<OfferingsCategory> categories, OnCategoryClickListener categoryClickListener) {
        this.context = context;
        this.categories = categories;
        this.categoryClickListener = categoryClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event_type_category, parent, false);
        return new ViewHolder(view);
    }
    public void addCategory(OfferingsCategory category) {
        categories.add(category); // Add the new category to the list
        notifyItemInserted(categories.size() - 1); // Notify the adapter to refresh the view
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OfferingsCategory category = categories.get(position);

        holder.categoryName.setText(category.getName());
//        holder.plannedBudget.setText(String.valueOf(category.getPlannedBudget()));
//        holder.priceInput.setText(String.valueOf(category.getSelectedProductPrice()));

        holder.seeProductsButton.setOnClickListener(v -> {
            if(categoryClickListener != null){
                try {
                    double budget = Double.parseDouble(holder.plannedBudget.getText().toString());
                    categoryClickListener.onItemClick(category, budget);
                }catch (NumberFormatException e){
                    holder.plannedBudget.setError("You need to select budget first!");
                    e.printStackTrace();
                }

            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            // Handle card deletion (remove category from the list)
            categories.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        EditText plannedBudget;
        Button seeProductsButton;
        EditText priceInput;
        MaterialButton deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            plannedBudget = itemView.findViewById(R.id.plannedBudget);
            seeProductsButton = itemView.findViewById(R.id.seeProductsButton);
            priceInput = itemView.findViewById(R.id.priceInput);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
