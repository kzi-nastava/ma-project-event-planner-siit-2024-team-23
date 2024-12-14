package com.example.fusmobilni.adapters.items.category;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.items.category.Category;
import com.example.fusmobilni.responses.items.CategoryResponse;

import java.util.ArrayList;
import java.util.List;

public class CategoryResponsesFilterAdapter extends RecyclerView.Adapter<CategoryResponsesFilterAdapter.CategoryFilterViewHolder> {
    private List<CategoryResponse> _categories = new ArrayList<>();
    private Long selectedCategory = -1L;


    public CategoryResponsesFilterAdapter(List<CategoryResponse> categories) {
        this._categories = categories;

    }

    public CategoryResponsesFilterAdapter(){
        _categories = new ArrayList<>();
    }
    public CategoryResponse getSelectedCategory() {
        if (selectedCategory == -1) {
            return null;
        }
        return _categories.get(getCategoryById(this.selectedCategory));
    }

    private int getCategoryById(Long id) {
        for (int i = 0; i < _categories.size(); ++i) {
            if (_categories.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public void setSelectedCategory(Long selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    @NonNull
    @Override
    public CategoryResponsesFilterAdapter.CategoryFilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_type_filter_card, parent, false);
        return new CategoryResponsesFilterAdapter.CategoryFilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryResponsesFilterAdapter.CategoryFilterViewHolder holder, int position) {
        CategoryResponse category = _categories.get(position);
        holder.categoryIcon.setImageResource(category.getId() == selectedCategory ? R.drawable.category : R.drawable.category_inactive);
        holder.categoryName.setText(category.getName());

        holder.backGround.setBackgroundResource(category.getId() == selectedCategory ? R.drawable.circle_background_primary : R.drawable.circle_background_secondary);
        holder.backGround.setElevation(category.getId() == selectedCategory ? 0f : 10f);
        holder.itemView.setOnClickListener(v -> {

            Log.d("Selected:" + String.valueOf(selectedCategory) + " Actual:" + category.getId(), "Selected:" + String.valueOf(selectedCategory) + " Actual:" + category.getId());

            if (selectedCategory == category.getId()) {
                selectedCategory = -1L;
            } else {
                selectedCategory = category.getId();
            }
            notifyDataSetChanged();
        });

    }

    public void resetCategories() {
        selectedCategory = -1L;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(_categories == null){
            _categories = new ArrayList<>();
        }
        return _categories.size();
    }

    public static class CategoryFilterViewHolder extends RecyclerView.ViewHolder {

        ImageView categoryIcon;
        TextView categoryName;

        ConstraintLayout backGround;

        public CategoryFilterViewHolder(@NonNull View itemView) {
            super(itemView);
            this.categoryIcon = itemView.findViewById(R.id.type_image);
            this.categoryName = itemView.findViewById(R.id.type_text);
            this.backGround = itemView.findViewById(R.id.category_image_background);

        }
    }
}
