package com.example.fusmobilni.fragments.items;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.items.category.CategoryAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentOfferingsPageBinding;
import com.example.fusmobilni.interfaces.CategoryListener;
import com.example.fusmobilni.requests.categories.GetCategoriesResponse;
import com.example.fusmobilni.requests.categories.GetCategoryResponse;
import com.example.fusmobilni.viewModels.items.category.CategoryViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OfferingsPage extends Fragment implements CategoryListener {

    private ArrayList<GetCategoryResponse> categories = new ArrayList<>();
    private CategoryAdapter adapter;
    private CategoryViewModel viewModel;
    private View deleteModal;
    private FragmentOfferingsPageBinding binding;


    public OfferingsPage() {
        // Required empty public constructor
    }


    public static OfferingsPage newInstance(String param1, String param2) {
        return new OfferingsPage();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOfferingsPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);

        RecyclerView recycler = binding.categoryRecyclerView;
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.adapter = new CategoryAdapter(this.categories, this);
        recycler.setAdapter(adapter);

        Call<GetCategoriesResponse> request = ClientUtils.categoryService.findAll();
        request.enqueue(new Callback<GetCategoriesResponse>() {
            @Override
            public void onResponse(Call<GetCategoriesResponse> call, Response<GetCategoriesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categories.clear();
                    categories.addAll(response.body().categories);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<GetCategoriesResponse> call, Throwable t) {
            }
        });

        binding.floatingActionButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.categories_toCreationForm);
        });
        binding.containedButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.categoriesPage_toCategoryProposals);
        });

        return view;
    }

    @Override
    public void onDeleteCategory(int position) {
        deleteModal = binding.getRoot().findViewById(R.id.nigger);
        binding.modalBackground.setVisibility(View.VISIBLE);
        deleteModal.setVisibility(View.VISIBLE);
        Button cancelButton = deleteModal.findViewById(R.id.cancelButton);
        Button confirmButton = deleteModal.findViewById(R.id.confirmButton);

        cancelButton.setOnClickListener(v -> {
            binding.modalBackground.setVisibility(View.INVISIBLE);
            deleteModal.setVisibility(View.INVISIBLE);
        });

        confirmButton.setOnClickListener(v -> {
            Long id = this.categories.get(position).id;
            Call<Void> request = ClientUtils.categoryService.delete(id);
            request.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        categories.remove(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position, categories.size());
                    } else {

                    }
                    binding.modalBackground.setVisibility(View.INVISIBLE);
                    deleteModal.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        });
    }

    @Override
    public void onUpdateCategory(int position) {
        GetCategoryResponse category = this.categories.get(position);
        this.viewModel.populate(category);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.categories_toCreationForm);
    }
}