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

import java.util.List;

public class CategoryFilterAdapter extends RecyclerView.Adapter<CategoryFilterAdapter.CategoryFilterViewHolder> {

    private List<Category> _categories;
    private  int selectedCategory = -1;
    public CategoryFilterAdapter(List<Category> categories) {
        this._categories = categories;

    }

    public Category getSelectedCategory() {
       if(selectedCategory == -1){
           return null;
       }
       return _categories.get(getCategoryById(this.selectedCategory));
    }
    private int getCategoryById(int id){
        for(int i = 0; i < _categories.size();++i){
            if(_categories.get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }
    public void setSelectedCategory(int selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    @NonNull
    @Override
    public CategoryFilterAdapter.CategoryFilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_category_card, parent, false);
        return new CategoryFilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryFilterAdapter.CategoryFilterViewHolder holder, int position) {
        Category category = _categories.get(position);
        holder.categoryIcon.setImageResource(category.getId() == selectedCategory ? category.getActiveIconResId() : category.getInactiveIconResId());
        holder.categoryName.setText(category.getName());

        holder.backGround.setBackgroundResource(category.getId() == selectedCategory ? R.drawable.circle_background_primary : R.drawable.circle_background_secondary);
        holder.backGround.setElevation(category.getId() == selectedCategory ? 0f : 10f);
        holder.itemView.setOnClickListener(v -> {

            Log.d("Selected:" + String.valueOf(selectedCategory) + " Actual:" + category.getId(),"Selected:" + String.valueOf(selectedCategory) + " Actual:" + category.getId());

            if(selectedCategory==category.getId()){
                selectedCategory = -1;
            }else{
                selectedCategory = category.getId();
            }
            notifyDataSetChanged();
        });

    }
    public void resetCategories(){
        selectedCategory = -1;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return _categories.size();
    }

    public static class CategoryFilterViewHolder extends RecyclerView.ViewHolder {

        ImageView categoryIcon;
        TextView categoryName;

        ConstraintLayout backGround;
        public CategoryFilterViewHolder(@NonNull View itemView) {
            super(itemView);
            this.categoryIcon = itemView.findViewById(R.id.category_image);
            this.categoryName = itemView.findViewById(R.id.category_text);
            this.backGround = itemView.findViewById(R.id.category_image_background);

        }
    }
}


