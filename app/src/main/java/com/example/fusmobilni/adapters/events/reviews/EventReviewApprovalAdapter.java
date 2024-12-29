package com.example.fusmobilni.adapters.events.reviews;

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
import com.example.fusmobilni.fragments.events.event.reviews.OnEventReviewActionListener;
import com.example.fusmobilni.fragments.items.reviews.OnItemReviewActionListener;
import com.example.fusmobilni.responses.events.review.EventReviewResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventReviewApprovalAdapter extends RecyclerView.Adapter<EventReviewApprovalAdapter.EventReviewApprovalAdapterViewHolder> {
    List<EventReviewResponse> reviews = new ArrayList<>();

    private final OnEventReviewActionListener listener;
    public EventReviewApprovalAdapter(OnEventReviewActionListener listener) {
        this.listener = listener;
        reviews = new ArrayList<>();
    }
    public EventReviewApprovalAdapter(List<EventReviewResponse> responses, OnEventReviewActionListener listener) {
        reviews = new ArrayList<>(responses);
        this.listener = listener;
    }


    @NonNull
    @Override
    public EventReviewApprovalAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_event_review_approve, parent, false);
        return new EventReviewApprovalAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventReviewApprovalAdapterViewHolder holder, int position) {
        EventReviewResponse grade = reviews.get(position);
        try {
            holder.userImage.setImageURI(convertToUrisFromBase64(holder.userImage.getContext(), grade.getUser().getAvatar()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        holder.reviewDate.setText(grade.getDate());
        holder.userName.setText(grade.getUser().getFirstName() + " " + grade.getUser().getLastName());
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

    public static class EventReviewApprovalAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView userName;
        TextView reviewDate;
        TextView reviewContent;
        ImageView star1;
        ImageView star2;
        ImageView star3;
        ImageView star4;
        ImageView star5;
        Button approveButton;
        Button declineButton;

        public EventReviewApprovalAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.userImageView);
            userName = itemView.findViewById(R.id.userNameTextView);
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
