package com.example.fusmobilni.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.CategoryAdapter;
import com.example.fusmobilni.adapters.CategoryProposalAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentCategoryProposalsPageBinding;
import com.example.fusmobilni.databinding.FragmentOfferingsPageBinding;
import com.example.fusmobilni.interfaces.CategoryProposalListener;
import com.example.fusmobilni.model.CategoryProposal;
import com.example.fusmobilni.model.OfferingsCategory;
import com.example.fusmobilni.requests.proposals.GetProposalResponse;
import com.example.fusmobilni.requests.proposals.GetProposalsResponse;
import com.example.fusmobilni.viewModels.CategoryProposalViewModel;
import com.example.fusmobilni.viewModels.CategoryViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryProposalsPage extends Fragment implements CategoryProposalListener {

    private ArrayList<GetProposalResponse> proposals = new ArrayList<>();
    private CategoryProposalAdapter adapter;
    private CategoryProposalViewModel viewModel;
    private FragmentCategoryProposalsPageBinding binding;

    public CategoryProposalsPage() {
    }

    public static CategoryProposalsPage newInstance(String param1, String param2) {
        return new CategoryProposalsPage();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryProposalsPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(CategoryProposalViewModel.class);
        RecyclerView recycler = binding.categoryRecyclerView;
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.adapter = new CategoryProposalAdapter(this.proposals, this);
        recycler.setAdapter(adapter);
        Call<GetProposalsResponse> request = ClientUtils.proposalService.findAll();
        request.enqueue(new Callback<GetProposalsResponse>() {
            @Override
            public void onResponse(Call<GetProposalsResponse> call, Response<GetProposalsResponse> response) {
                if (response.isSuccessful()) {
                    proposals.clear();
                    proposals.addAll(response.body().proposals);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GetProposalsResponse> call, Throwable t) {

            }
        });
        binding.containedButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.categoryProposals_toCategoriesPage);
        });

        return view;
    }

    @Override
    public void onModifyCategory(int position) {
        GetProposalResponse proposal = this.proposals.get(position);
        this.viewModel.populate(proposal);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.categoryProposals_toModificationForm);
    }
}