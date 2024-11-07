package com.example.fusmobilni.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.interfaces.ItemClickListener;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Uri> imageUris;
    private ItemClickListener clickListener;

    public RecyclerAdapter(ArrayList<Uri> imageUris, ItemClickListener clickListener) {
        this.imageUris = imageUris;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_single_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return this.imageUris.size();
    }

    public void removeItem(int position) {
        imageUris.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, imageUris.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ImageButton button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            button = itemView.findViewById(R.id.floatingCancel);
        }

        public void bind(final int position) {
            this.imageView.setImageURI(imageUris.get(position));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemRemove(position);
                }
            });
        }
    }
}
