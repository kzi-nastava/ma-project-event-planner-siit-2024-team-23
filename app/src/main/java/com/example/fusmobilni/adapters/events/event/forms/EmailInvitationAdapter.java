package com.example.fusmobilni.adapters.events.event.forms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class EmailInvitationAdapter extends RecyclerView.Adapter<EmailInvitationAdapter.EmailInvitationViewHolder> {

    private List<String> _emails;

    public EmailInvitationAdapter(List<String> emails) {
        _emails = new ArrayList<>(emails);
    }

    public EmailInvitationAdapter() {
        _emails = new ArrayList<>();
    }

    public void setData(List<String> list) {
        this._emails = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void addEmail(String email) {
        String transformedEmail = email.toLowerCase().trim();
        if (!_emails.contains(transformedEmail)) {
            _emails.add(email);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public EmailInvitationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_invitaion_chip, parent, false);
        return new EmailInvitationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmailInvitationViewHolder holder, int position) {
        String email = _emails.get(position);
        holder.chip.setText(email);
        holder.chip.setOnCloseIconClickListener(v -> {
            _emails.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return _emails.size();
    }

    public static class EmailInvitationViewHolder extends RecyclerView.ViewHolder {
        private Chip chip;

        public EmailInvitationViewHolder(@NonNull View itemView) {
            super(itemView);
            chip = itemView.findViewById(R.id.emailInvitationChip);
        }
    }
}
