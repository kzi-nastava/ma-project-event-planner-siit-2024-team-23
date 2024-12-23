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
import com.example.fusmobilni.responses.items.ItemReviewResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemReviewApprovalAdapter extends RecyclerView.Adapter<ItemReviewApprovalAdapter.ItemReviewApprovalAdapterViewHolder> {
    List<ItemReviewResponse> reviews = new ArrayList<>();

    public ItemReviewApprovalAdapter() {
        reviews = new ArrayList<>();
    }

    public ItemReviewApprovalAdapter(List<ItemReviewResponse> responses) {
        reviews = new ArrayList<>(responses);
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

        });
        holder.declineButton.setOnClickListener(v -> {
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
        }
    }
}
