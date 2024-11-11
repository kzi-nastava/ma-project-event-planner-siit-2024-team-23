package com.example.fusmobilni.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.model.Event;
import com.example.fusmobilni.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsHorizontalAdapter extends RecyclerView.Adapter<ProductsHorizontalAdapter.ProductHorizontalViewHolder> implements Filterable {
    private List<Product> _productsList;
    private List<Product> _productsFull;
    private List<Product> _pagedProducts;
    private int _currentPage = 0;
    private int _itemsPerPage = 5;
    private String _constraint = "";
    private String _selectedCategory = "";
    private double _minSelectedPrice = 0;
    private double _maxSelectedPrice = 100;
    private String _selectedLocation = "";

    public ProductsHorizontalAdapter() {
        this._productsList = new ArrayList<>();
        this._productsFull = new ArrayList<>();
        this._pagedProducts = new ArrayList<>();
    }

    public ProductsHorizontalAdapter(ArrayList<Product> events) {
        this._productsList = events;
        this._productsFull = new ArrayList<>(events);
        this._pagedProducts = new ArrayList<>(events);
    }

    public void setOriginalData(List<Product> list) {
        this._productsFull = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void setFilteringData(List<Product> list) {
        this._productsList = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void setData(List<Product> list) {
        this._pagedProducts = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void setFilters(@NonNull String constraint, String category, double minPrice,double maxPrice, String location) {
        _minSelectedPrice = minPrice;
        _maxSelectedPrice = maxPrice;
        _selectedLocation = location.toLowerCase().trim();
        _selectedCategory = category.toLowerCase().trim();
        _constraint = constraint.toLowerCase().trim();
        notifyDataSetChanged();
        applyFilters();
    }

    private void applyFilters() {
        getFilter().filter(_constraint);
    }

    public void loadPage(int page) {
        if (page < 0) {
            return;
        }


        _currentPage = page;
        int start = page * _itemsPerPage;
        int end = start + _itemsPerPage;
        if (end > _productsList.size()) {
            end = _productsList.size();
        }
        if (start >= end) {
            _currentPage -=1;
            return;
        }
        List<Product> pageCategories = _productsList.subList(start, end);

        this.setData(pageCategories);

    }

    public void prevPage() {
        if (_currentPage > 0) {
            loadPage(_currentPage - 1);
        }
    }

    public void nextPage() {
        if ((_currentPage + 1) * _itemsPerPage < _productsFull.size()) {
            loadPage(_currentPage + 1);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Product> filteredList = new ArrayList<>();

                _constraint = constraint.toString().toLowerCase().trim();
                for (Product product : _productsFull) {
                    boolean matchesConstraint = _constraint.isEmpty() || product.getName().toLowerCase().contains(_constraint);
                    boolean matchesLocation = _selectedLocation.isEmpty() || product.getLocation().toLowerCase().equals(_selectedLocation);
                    boolean matchesPrice = product.getPrice() >= _minSelectedPrice && product.getPrice() <= _maxSelectedPrice;
                    boolean matchesCategory = _selectedCategory.isEmpty() || product.getCategory().equals(_selectedCategory);
                    if (matchesConstraint && matchesLocation && matchesPrice && matchesCategory) {
                        filteredList.add(product);
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                _productsList.clear();
                if (results.values != null) {
                    _productsList.addAll((List<Product>) results.values);
                }
                loadPage(0);
            }
        };
    }

    public void resetFilters() {
        _constraint = "";
        _selectedLocation = "";
        _minSelectedPrice = -1;
    }

    public void setPageSize(int selectedItem, String currentText) {
        _itemsPerPage = selectedItem;
        getFilter().filter(currentText);
    }

    static class ProductHorizontalViewHolder extends RecyclerView.ViewHolder {
        TextView _name;
        TextView _description;
        TextView _price;

        TextView _location;

        ProductHorizontalViewHolder(View itemView) {
            super(itemView);
            _name = itemView.findViewById(R.id.textViewProductNameHorizontal);
            _description = itemView.findViewById(R.id.productDescriptionHorizontal);
            _price = itemView.findViewById(R.id.productsHorizontalPrice);
            _location = itemView.findViewById(R.id.textViewProductsLocationHorizontal);
        }
    }

    @NonNull
    @Override
    public ProductHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_horizontal, parent, false);
        return new ProductHorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHorizontalViewHolder holder, int position) {
        Product product = _pagedProducts.get(position);

        holder._name.setText(product.getName());
        holder._description.setText(product.getDescription());
        holder._price.setText(String.valueOf(product.getPrice()));
        holder._location.setText(product.getLocation());
    }


    @Override
    public int getItemCount() {
        return _pagedProducts.size();
    }


}
