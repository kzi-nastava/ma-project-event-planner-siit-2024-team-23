package com.example.fusmobilni.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.ProductsHorizontalAdapter;
import com.example.fusmobilni.databinding.FragmentProductSearchBinding;
import com.example.fusmobilni.interfaces.OnFilterEventsApplyListener;
import com.example.fusmobilni.interfaces.OnFilterProductsApplyListener;
import com.example.fusmobilni.model.Product;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Optional;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductSearchFragment extends Fragment implements OnFilterEventsApplyListener {

    private FragmentProductSearchBinding _binding;
    private TextInputLayout _searchView;
    private ArrayList<Product> _products;
    private RecyclerView _listView;
    private ProductsHorizontalAdapter _productsAdapter;
    private Spinner _paginationSpinner;
    private MaterialButton _prevButton;
    private MaterialButton _nextButton;

    public ProductSearchFragment() {
        // Required empty public constructor
    }

    public static ProductSearchFragment newInstance(String param1, String param2) {
        ProductSearchFragment fragment = new ProductSearchFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _binding = FragmentProductSearchBinding.inflate(inflater, container, false);
        View view = _binding.getRoot();


        _listView = _binding.productList;
        _searchView = _binding.searchTextInputLayoutProducts;

        initializeButtons();

        _productsAdapter = new ProductsHorizontalAdapter();
        _listView.setAdapter(_productsAdapter);

        _searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _productsAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        _binding.productsFilterButton.setOnClickListener(v -> {
            _productsAdapter.resetFilters();
            ProductFilterFragment filterFragment = new ProductFilterFragment();
            filterFragment.set_filterListener(new OnFilterProductsApplyListener() {

                @Override
                public void onFilterApply(String category, double minPrice,double maxPrice, String location) {
                    _productsAdapter.setFilters(_searchView.getEditText().getText().toString(),category, minPrice,maxPrice, location);
                }
            });
            Bundle args = new Bundle();
            ArrayList<Double> minMax = getMinMaxPrice();
            args.putDouble("min_slider", minMax.get(0));
            args.putDouble("max_slider",minMax.get(1));
            filterFragment.setArguments(args);
            filterFragment.show(getParentFragmentManager(), filterFragment.getTag());
        });

        _products = fillProducts();
        _productsAdapter.setOriginalData(_products);
        _productsAdapter.setFilteringData(_products);
        _productsAdapter.setData(_products);
        _productsAdapter.loadPage(0);

        initializePaginationSpinner();

        return view;
    }

    private void initializeButtons() {

        _prevButton = this._binding.productSearchPreviousButton;
        _nextButton = this._binding.productSearchNextButton;

        _prevButton.setOnClickListener(v -> _productsAdapter.prevPage());
        _nextButton.setOnClickListener(v -> _productsAdapter.nextPage());
    }

    private void initializePaginationSpinner() {
        _paginationSpinner = _binding.paginationSpinnerProducts;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.paginationPageSizes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _paginationSpinner.setAdapter(adapter);
        _paginationSpinner.setSelection(0);
        _paginationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedItem = Integer.parseInt(String.valueOf(parent.getItemAtPosition(position)));
                _productsAdapter.setPageSize(selectedItem, _searchView.getEditText().getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private ArrayList<Product> fillProducts() {
        ArrayList<Product> p = new ArrayList<>();
        p.add(new Product("Gourmet Pizza", "A delicious blend of flavors", 15.99, "New York", "Food"));
        p.add(new Product("Lemonade", "Refreshing and invigorating beverage", 3.49, "California", "Health"));
        p.add(new Product("Mixed Nuts", "Sweet and savory snacks", 5.99, "Texas", "Sports"));
        p.add(new Product("Croissants", "Freshly baked pastries", 2.99, "France", "Art"));
        p.add(new Product("Dark Chocolate Truffles", "Artisanal chocolates", 12.49, "Belgium", "Fashion"));
        p.add(new Product("Sushi Platter", "Assorted fresh sushi and sashimi", 25.00, "Japan", "Food"));
        p.add(new Product("Espresso", "Strong and bold coffee shot", 2.50, "Italy", "Travel"));
        p.add(new Product("Hamburger", "Classic beef burger with toppings", 8.99, "USA", "Sports"));
        p.add(new Product("Macarons", "Colorful French almond cookies", 10.00, "France", "Art"));
        p.add(new Product("Tacos", "Mexican-style tacos with various fillings", 7.49, "Mexico", "Travel"));
        p.add(new Product("Smoothie Bowl", "Fruit and granola bowl", 6.99, "California", "Health"));
        p.add(new Product("Falafel Wrap", "Spiced chickpea wraps", 5.50, "Middle East", "Education"));
        p.add(new Product("Pasta Carbonara", "Creamy pasta with bacon", 13.99, "Italy", "Food"));
        p.add(new Product("Matcha Latte", "Green tea latte", 4.99, "Japan", "Tech"));
        p.add(new Product("Bagel with Cream Cheese", "Toasted bagel with spread", 3.00, "New York", "Food"));
        p.add(new Product("Avocado Toast", "Healthy smashed avocado on toast", 5.00, "California", "Fashion"));
        p.add(new Product("Greek Yogurt", "Thick yogurt with honey", 4.49, "Greece", "Health"));
        p.add(new Product("Ramen", "Japanese noodle soup", 11.99, "Japan", "Education"));
        p.add(new Product("BBQ Ribs", "Slow-cooked BBQ pork ribs", 16.99, "Texas", "Food"));
        p.add(new Product("Vegan Burger", "Plant-based burger", 9.99, "Los Angeles", "Health"));
        p.add(new Product("Cheese Platter", "Assorted cheeses", 20.00, "France", "Food"));
        p.add(new Product("Orange Juice", "Freshly squeezed orange juice", 3.25, "Florida", "Sports"));
        p.add(new Product("Caesar Salad", "Salad with romaine, croutons, dressing", 7.99, "Italy", "Education"));
        p.add(new Product("Pad Thai", "Thai stir-fried noodles", 12.50, "Thailand", "Travel"));
        p.add(new Product("Cupcakes", "Assorted mini cupcakes", 6.00, "London", "Fashion"));
        p.add(new Product("Hot Dog", "Classic American hot dog", 4.50, "Chicago", "Music"));
        p.add(new Product("Ice Cream Sundae", "Ice cream with toppings", 5.50, "New York", "Travel"));
        p.add(new Product("Burrito", "Mexican wrap with beans, rice, meat", 8.99, "Mexico", "Food"));
        p.add(new Product("Miso Soup", "Japanese soup with tofu", 2.99, "Japan", "Health"));
        p.add(new Product("Chocolate Brownie", "Rich chocolate dessert", 3.99, "USA", "Fashion"));
        p.add(new Product("Kimchi", "Spicy fermented cabbage", 3.99, "South Korea", "Tech"));
        p.add(new Product("Pancakes", "Stack of fluffy pancakes", 7.00, "Canada", "Sports"));
        p.add(new Product("Churros", "Fried dough with sugar", 4.99, "Spain", "Travel"));
        p.add(new Product("Bruschetta", "Italian bread with toppings", 5.50, "Italy", "Education"));
        p.add(new Product("Pho", "Vietnamese noodle soup", 10.99, "Vietnam", "Art"));
        p.add(new Product("Fried Chicken", "Crispy fried chicken", 8.99, "Kentucky", "Music"));
        p.add(new Product("Peking Duck", "Roast duck with pancakes", 25.99, "China", "Food"));
        p.add(new Product("Beef Wellington", "Fillet steak in puff pastry", 30.00, "England", "Fashion"));
        p.add(new Product("Paella", "Spanish rice with seafood", 18.50, "Spain", "Travel"));
        p.add(new Product("Samosas", "Spiced potato-filled pastries", 3.50, "India", "Health"));
        p.add(new Product("Dim Sum", "Chinese dumplings", 15.00, "China", "Tech"));
        p.add(new Product("Apple Pie", "Classic American dessert", 5.00, "USA", "Music"));

        return p;
    }

    private ArrayList<Double> getMinMaxPrice(){
        ArrayList <Double> resultList = new ArrayList<>();
        Optional<Double> minPrice = _products.stream().map(Product::getPrice).min(Double::compare);
        Optional<Double> maxPrice = _products.stream().map(Product::getPrice).max(Double::compare);
        resultList.add(minPrice.get());
        resultList.add(maxPrice.get());
        return  resultList;
    }
    @Override
    public void onFilterApply(String category, String location, String date) {

    }
}