package com.example.fusmobilni.adapters.communication.chats;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.responses.communication.chat.messages.GetMessageResponse;

import java.util.List;
import java.util.Objects;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageHolder> {

    private List<GetMessageResponse> messages;
    private final Long senderId;

    public ChatAdapter(List<GetMessageResponse> messages, Long senderId) {
        this.messages = messages;
        this.senderId = senderId;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<GetMessageResponse> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    public void addMessage(GetMessageResponse message) {
        this.messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message, parent, false);
        return new ChatAdapter.MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        GetMessageResponse message = this.messages.get(position);
        holder.content.setText(message.content);

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.messageCard.getLayoutParams();

        if (Objects.equals(message.senderId, senderId)) {

            params.startToStart = ConstraintLayout.LayoutParams.UNSET;
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            holder.messageCard.setCardBackgroundColor(ContextCompat.getColor(holder.messageCard.getContext(), R.color.primary_blue_900));
            holder.content.setTextColor(ContextCompat.getColor(holder.messageCard.getContext(), android.R.color.white)); // Optional: Ensure text is white
        } else {

            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            params.endToEnd = ConstraintLayout.LayoutParams.UNSET;
            holder.messageCard.setCardBackgroundColor(ContextCompat.getColor(holder.messageCard.getContext(), android.R.color.white));
            holder.content.setTextColor(ContextCompat.getColor(holder.messageCard.getContext(), R.color.primary_blue_900)); // Optional: Ensure text contrasts
        }

        holder.messageCard.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return this.messages.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {

        public TextView content;
        public CardView messageCard;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            this.content = itemView.findViewById(R.id.messageText);
            this.messageCard = itemView.findViewById(R.id.messageCard);
        }
    }
}
