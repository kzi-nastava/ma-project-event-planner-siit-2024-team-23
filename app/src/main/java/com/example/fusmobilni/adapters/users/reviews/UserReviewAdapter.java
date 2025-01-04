package com.example.fusmobilni.adapters.users.reviews;

import static com.example.fusmobilni.adapters.AdapterUtils.convertToUrisFromBase64;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.responses.users.reviews.UserReviewResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserReviewAdapter extends RecyclerView.Adapter<UserReviewAdapter.UserReviewAdapterViewHolder> {
    List<UserReviewResponse> reviews = new ArrayList<>();

    public UserReviewAdapter() {
        reviews = new ArrayList<>();
    }

    public UserReviewAdapter(List<UserReviewResponse> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public UserReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_user_review, parent, false);
        return new UserReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserReviewAdapter.UserReviewAdapterViewHolder holder, int position) {
        UserReviewResponse grade = reviews.get(position);
        try {
            holder.userImage.setImageURI(convertToUrisFromBase64(holder.userImage.getContext(), grade.getRater().getUserImage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        holder.reviewDate.setText(grade.getDate());
        holder.userName.setText(grade.getRater().getFirstName() + " " + grade.getRater().getLastName());
        holder.reviewContent.setText(grade.getContent());
        ImageView[] attendees = {holder.star1, holder.star2, holder.star3, holder.star4, holder.star5};
        for (int i = 0; i < attendees.length; ++i) {
            if ((i + 1) <= grade.getRating()) {
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

    public static class UserReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView userName;
        TextView reviewDate;
        TextView reviewContent;
        ImageView star1;
        ImageView star2;
        ImageView star3;
        ImageView star4;
        ImageView star5;

        public UserReviewAdapterViewHolder(@NonNull View itemView) {
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
