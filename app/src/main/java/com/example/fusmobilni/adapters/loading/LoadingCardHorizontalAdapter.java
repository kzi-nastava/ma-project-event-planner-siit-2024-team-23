package com.example.fusmobilni.adapters.loading;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class LoadingCardHorizontalAdapter extends RecyclerView.Adapter<LoadingCardHorizontalAdapter.LoadingCardHorizontalViewHolder> {

    private List<Boolean> _members = new ArrayList<>();
    public LoadingCardHorizontalAdapter() {
        _members = new ArrayList<>();
    }

    public LoadingCardHorizontalAdapter(int size) {
        _members = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            _members.add(true);
        }
        notifyDataSetChanged();
    }
    public void setSize(int size) {
        _members = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            _members.add(true);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public LoadingCardHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_loading_card_horizontal, parent, false);
        return new LoadingCardHorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoadingCardHorizontalViewHolder holder, int position) {
        Boolean bool = _members.get(position);
        if (bool) {
            holder.shimmerFrameLayout.startShimmer();
        } else {
            holder.shimmerFrameLayout.stopShimmer();
        }
    }

    @Override
    public int getItemCount() {
        return _members.size();
    }

    public static class LoadingCardHorizontalViewHolder extends RecyclerView.ViewHolder {
        private ShimmerFrameLayout shimmerFrameLayout;

        public LoadingCardHorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmerLayout);
        }
    }
}
