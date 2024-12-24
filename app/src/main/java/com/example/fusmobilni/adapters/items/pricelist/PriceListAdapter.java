package com.example.fusmobilni.adapters.items.pricelist;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.interfaces.PriceListListener;
import com.example.fusmobilni.responses.items.pricelist.PriceListGetResponse;

import java.util.ArrayList;
import java.util.List;

public class PriceListAdapter extends RecyclerView.Adapter<PriceListAdapter.PriceListViewHolder> {

    private List<PriceListGetResponse> items = new ArrayList<>();
    private final PriceListListener listener;

    public PriceListAdapter(List<PriceListGetResponse> items, PriceListListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public void updateItem(int position, PriceListGetResponse updatedItem) {
        items.set(position, updatedItem);
        notifyItemChanged(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<PriceListGetResponse> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PriceListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.price_list_item, parent, false);
        return new PriceListAdapter.PriceListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PriceListViewHolder holder, int position) {
        PriceListGetResponse item = items.get(position);
        holder.name.setText(item.name);
        holder.price.setText(String.valueOf(item.price));
        holder.discount.setText(String.valueOf(item.discount));
        holder.priceWithDiscount.setText(String.valueOf(item.priceWithDiscount));
        holder.itemView.setOnClickListener(v -> {
            this.listener.onUpdate(position);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class PriceListViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView discount;
        public TextView price;
        public TextView priceWithDiscount;
        public CardView card;

        public PriceListViewHolder(@NonNull View view) {
            super(view);
            this.name = view.findViewById(R.id.textName);
            this.price = view.findViewById(R.id.textPrice);
            this.discount = view.findViewById(R.id.textDiscount);
            this.priceWithDiscount = view.findViewById(R.id.textPriceWithDiscount);
        }
    }
}
