package com.example.fusmobilni.adapters.items.reviews;

import static com.example.fusmobilni.adapters.AdapterUtils.convertToUrisFromBase64;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.fragments.items.reviews.OnItemReviewActionListener;
import com.example.fusmobilni.responses.items.ItemReviewResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemReviewApprovalAdapter extends RecyclerView.Adapter<ItemReviewApprovalAdapter.ItemReviewApprovalAdapterViewHolder> {
    List<ItemReviewResponse> reviews = new ArrayList<>();
    private final OnItemReviewActionListener listener;

    public ItemReviewApprovalAdapter(OnItemReviewActionListener listener) {
        this.listener = listener;
        reviews = new ArrayList<>();
    }

    public ItemReviewApprovalAdapter(List<ItemReviewResponse> responses, OnItemReviewActionListener listener) {
        reviews = new ArrayList<>(responses);
        this.listener = listener;
    }

    public void removeReview(ItemReviewResponse review) {
        reviews.removeIf(item -> item.getId().equals(review.getId()));
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemReviewApprovalAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_review_approve, parent, false);
        return new ItemReviewApprovalAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemReviewApprovalAdapterViewHolder holder, int position) {
        ItemReviewResponse grade = reviews.get(position);
        try {
            holder.eoImage.setImageURI(convertToUrisFromBase64(holder.eoImage.getContext(), grade.getEventOrganizer().getImage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        holder.reviewDate.setText(grade.getDate());
        holder.eoName.setText(grade.getEventOrganizer().getFirstName() + " " + grade.getEventOrganizer().getLastName());
        holder.reviewContent.setText(grade.getContent());
        ImageView[] attendees = {holder.star1, holder.star2, holder.star3, holder.star4, holder.star5};
        for (int i = 0; i < attendees.length; ++i) {
            if ((i + 1) <= grade.getGrade()) {
                attendees[i].setImageResource(R.drawable.ic_star_fill);
            } else {
                attendees[i].setImageResource(R.drawable.ic_star);
            }
        }
        holder.approveButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onApprove(grade);
            }
        });
        holder.declineButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDecline(grade);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ItemReviewApprovalAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView eoImage;
        TextView eoName;
        TextView reviewDate;
        TextView reviewContent;
        ImageView star1;
        ImageView star2;
        ImageView star3;
        ImageView star4;
        ImageView star5;
        Button approveButton;
        Button declineButton;

        public ItemReviewApprovalAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            eoImage = itemView.findViewById(R.id.eoImageView);
            eoName = itemView.findViewById(R.id.eoNameTextView);
            reviewContent = itemView.findViewById(R.id.textViewReviewContent);
            reviewDate = itemView.findViewById(R.id.textViewReviewDate);
            star1 = itemView.findViewById(R.id.star1);
            star2 = itemView.findViewById(R.id.star2);
            star3 = itemView.findViewById(R.id.star3);
            star4 = itemView.findViewById(R.id.star4);
            star5 = itemView.findViewById(R.id.star5);
            approveButton = itemView.findViewById(R.id.approveReviewButton);
            declineButton = itemView.findViewById(R.id.declineReviewButton);
        }
    }
}
