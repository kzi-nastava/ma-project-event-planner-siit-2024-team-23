package com.example.fusmobilni.adapters.items;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.interfaces.OnCategoryClickListener;
import com.example.fusmobilni.model.items.category.OfferingsCategory;
import com.example.fusmobilni.responses.events.components.EventComponentResponse;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BudgetPlaningItemAdapter extends RecyclerView.Adapter<BudgetPlaningItemAdapter.ViewHolder> {
    private final List<EventComponentResponse> categories;
    private final Context context;

    private final Long eventId;
    private final OnCategoryClickListener categoryClickListener;
    public BudgetPlaningItemAdapter(Context context, List<EventComponentResponse> categories, Long eventId, OnCategoryClickListener categoryClickListener) {
        this.context = context;
        this.categories = categories;
        this.eventId = eventId;
        this.categoryClickListener = categoryClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event_type_category, parent, false);
        return new ViewHolder(view);
    }
    public void addCategory(EventComponentResponse category) {
        categories.add(category);
        notifyItemInserted(categories.size() - 1);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventComponentResponse category = categories.get(position);

        holder.categoryName.setText(category.category.getName());
        if (category.id != -1){
            holder.plannedBudget.setText(String.valueOf(category.estimatedBudget));
            holder.priceInput.setText(String.valueOf(category.actualPrice));
        }
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
            if (categories.get(position).id != -1) {
                Call<Void> deleteResponse = ClientUtils.eventsService.removeComponentFromEvent(eventId, categories.get(position).id);
                deleteResponse.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
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
