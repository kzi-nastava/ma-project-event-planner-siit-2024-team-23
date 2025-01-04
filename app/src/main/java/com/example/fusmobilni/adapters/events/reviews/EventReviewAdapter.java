package com.example.fusmobilni.adapters.events.reviews;

import static com.example.fusmobilni.adapters.AdapterUtils.convertToUrisFromBase64;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.responses.events.review.EventReviewResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventReviewAdapter extends RecyclerView.Adapter<EventReviewAdapter.EventReviewAdapterViewHolder> {
    List<EventReviewResponse> reviews = new ArrayList<>();

    public EventReviewAdapter() {
        reviews = new ArrayList<>();
    }

    public EventReviewAdapter(List<EventReviewResponse> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public EventReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_event_review, parent, false);
        return new EventReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventReviewAdapterViewHolder holder, int position) {
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
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class EventReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView userName;
        TextView reviewDate;
        TextView reviewContent;
        ImageView star1;
        ImageView star2;
        ImageView star3;
        ImageView star4;
        ImageView star5;

        public EventReviewAdapterViewHolder(@NonNull View itemView) {
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
        }
    }
}
