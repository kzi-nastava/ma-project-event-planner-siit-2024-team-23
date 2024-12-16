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

public class LoadingCardVerticalAdapter extends RecyclerView.Adapter<LoadingCardVerticalAdapter.LoadingCardVerticalViewHolder> {

    private List<Boolean> _members = new ArrayList<>();

    @NonNull
    @Override
    public LoadingCardVerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_loading_card_vertical, parent, false);
        return new LoadingCardVerticalViewHolder(view);

    }

    public LoadingCardVerticalAdapter() {
        _members = new ArrayList<>();
    }

    public LoadingCardVerticalAdapter(int size) {
        _members = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            _members.add(true);
        }
    }

    public void setSize(int size) {
        _members = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            _members.add(true);
        }
        notifyDataSetChanged();
    }

    public void ceaseShimmer(){
        setSize(0);
    }
    @Override

    public void onBindViewHolder(@NonNull LoadingCardVerticalViewHolder holder, int position) {
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

    public static class LoadingCardVerticalViewHolder extends RecyclerView.ViewHolder {
        private ShimmerFrameLayout shimmerFrameLayout;

        public LoadingCardVerticalViewHolder(@NonNull View itemView) {
            super(itemView);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmer_layout);
        }
    }
}
