package com.example.fusmobilni.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.interfaces.CategoryListener;
import com.example.fusmobilni.interfaces.CategoryProposalListener;
import com.example.fusmobilni.model.CategoryProposal;
import com.example.fusmobilni.model.OfferingsCategory;

import java.util.List;

public class CategoryProposalAdapter extends  RecyclerView.Adapter<CategoryProposalAdapter.CategoryViewHolder>{

    private List<CategoryProposal> proposals;


    private CategoryProposalListener clickListener;

    public CategoryProposalAdapter(List<CategoryProposal> proposals, CategoryProposalListener clickListener) {
        this.proposals = proposals;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public CategoryProposalAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_proposal_card, parent, false);
        return new CategoryProposalAdapter.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryProposalAdapter.CategoryViewHolder holder, int position) {
        CategoryProposal category = proposals.get(position);
        holder.title.setText(category.getName());
        holder.description.setText(category.getDescription());
        holder.approveButton.setOnClickListener(v -> clickListener.onApproveCategory(position));
        holder.modifyButton.setOnClickListener(v -> clickListener.onModifyCategory(position));
    }

    @Override
    public int getItemCount() {
        return proposals.size();
    }


    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView title, description;
        Button modifyButton, approveButton;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cardTitle);
            description = itemView.findViewById(R.id.cardDescription);
            modifyButton = itemView.findViewById(R.id.modifyButton);
            approveButton = itemView.findViewById(R.id.approveButton);

        }
    }

}
