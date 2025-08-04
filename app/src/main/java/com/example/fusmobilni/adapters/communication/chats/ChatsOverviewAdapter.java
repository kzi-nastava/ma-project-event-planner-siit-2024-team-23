package com.example.fusmobilni.adapters.communication.chats;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;

import com.example.fusmobilni.interfaces.ChatListener;
import com.example.fusmobilni.model.communication.chat.ChatOverview;
import java.util.ArrayList;
import java.util.List;

public class ChatsOverviewAdapter extends RecyclerView.Adapter<ChatsOverviewAdapter.ChatsViewHolder> {

    private List<ChatOverview> chats = new ArrayList<>();
    private final ChatListener listener;

    public ChatsOverviewAdapter(List<ChatOverview> chats, ChatListener listener) {
        this.chats = chats;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ChatOverview> chats) {
        this.chats = chats;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_overview_card, parent, false);
        return new ChatsOverviewAdapter.ChatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsViewHolder holder, int position) {
        ChatOverview chat = this.chats.get(position);
        holder.personName.setText(chat.name);
        holder.latestMessage.setText(chat.latestMessage);
        holder.profileImage.setImageURI(chat.avatar);
        holder.itemView.setOnClickListener(v -> {
            this.listener.onChatClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return this.chats.size();
    }

    public static class ChatsViewHolder extends RecyclerView.ViewHolder {

        public TextView personName;
        public TextView latestMessage;
        public ImageView profileImage;

        public ChatsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.personName = itemView.findViewById(R.id.personName);
            this.latestMessage = itemView.findViewById(R.id.latestMessage);
            this.profileImage = itemView.findViewById(R.id.profileImage);
        }
    }

}
