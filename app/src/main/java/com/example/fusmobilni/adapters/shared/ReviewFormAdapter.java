package com.example.fusmobilni.adapters.shared;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewFormAdapter extends RecyclerView.Adapter<ReviewFormAdapter.ReviewFormAdapterViewHolder> {
    List<Boolean> stars = new ArrayList<>();
    int amount;

    public ReviewFormAdapter() {
        stars = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            stars.add(false);
        }
        amount = 0;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        for (int i = 0; i < stars.size(); ++i) {
            if (i + 1 <= this.amount) {
                stars.set(i, true);
            } else {
                stars.set(i, false);
            }
        }
        notifyDataSetChanged();
    }

    public int getAmount() {
        return this.amount;
    }

    @NonNull
    @Override
    public ReviewFormAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_form_stars, parent, false);
        return new ReviewFormAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewFormAdapterViewHolder holder, int position) {
        Boolean selected = stars.get(position);
        holder.star2.setImageResource((selected) ? R.drawable.ic_star_fill : R.drawable.ic_star);
        holder.star2.setOnClickListener(v -> {
            setAmount(position + 1);
        });
    }

    @Override
    public int getItemCount() {
        return stars.size();
    }

    public static class ReviewFormAdapterViewHolder extends RecyclerView.ViewHolder {

        ImageView star2;

        public ReviewFormAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            star2 = itemView.findViewById(R.id.star2);
        }
    }
}
