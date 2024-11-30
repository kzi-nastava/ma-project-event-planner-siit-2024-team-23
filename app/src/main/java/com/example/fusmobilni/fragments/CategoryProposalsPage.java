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
import com.example.fusmobilni.databinding.FragmentCategoryProposalsPageBinding;
import com.example.fusmobilni.databinding.FragmentOfferingsPageBinding;
import com.example.fusmobilni.interfaces.CategoryProposalListener;
import com.example.fusmobilni.model.CategoryProposal;
import com.example.fusmobilni.model.OfferingsCategory;
import com.example.fusmobilni.viewModels.CategoryProposalViewModel;
import com.example.fusmobilni.viewModels.CategoryViewModel;

import java.util.ArrayList;


public class CategoryProposalsPage extends Fragment implements CategoryProposalListener {

    private ArrayList<CategoryProposal> proposals = new ArrayList<>();
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
        dummyData();
        this.adapter = new CategoryProposalAdapter(this.proposals, this);
        recycler.setAdapter(adapter);
        binding.containedButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.categoryProposals_toCategoriesPage);
        });

        return view;
    }

    private void dummyData() {
        proposals.add(new CategoryProposal(1, "kanta", "Sport je jako zanimljiv i zabavan", "Kosarka", "kosarka je sport u kojem se igra"));
        proposals.add(new CategoryProposal(2, "keleraba", "Sport je jako zanimljiv i zabavan", "Ketering", "Ketering je jako dobra stvar"));
        proposals.add(new CategoryProposal(3, "Slavlje", "Sport je jako zanimljiv i zabavan", "Svadba", "Svadba je jako cool"));
        proposals.add(new CategoryProposal(4, "Hronologija", "Sport je jako zanimljiv i zabavan", "istorijaPisama", "Istorija je jako kul"));
        proposals.add(new CategoryProposal(5, "dunston", "Sport je jako zanimljiv i zabavan", "Sarma", "Sarma je jako dobra"));
    }

    @Override
    public void onModifyCategory(int position) {
        CategoryProposal proposal = this.proposals.get(position);
        this.viewModel.populate(proposal);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.categoryProposals_toModificationForm);
    }
}