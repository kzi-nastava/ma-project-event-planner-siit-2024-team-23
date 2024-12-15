package com.example.fusmobilni.fragments.events.event.createEventSteps;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.items.BudgetPlaningItemAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentCreateEventStepTwoBinding;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.model.event.eventTypes.EventType;
import com.example.fusmobilni.model.items.category.OfferingsCategory;
import com.example.fusmobilni.requests.categories.GetCategoriesResponse;
import com.example.fusmobilni.requests.categories.GetCategoryResponse;
import com.example.fusmobilni.responses.events.components.EventComponentResponse;
import com.example.fusmobilni.responses.events.components.EventComponentsResponse;
import com.example.fusmobilni.viewModels.events.event.EventViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEventStepTwo extends Fragment  implements FragmentValidation {
    private FragmentCreateEventStepTwoBinding _binding;
    private EventViewModel _eventViewModel;
    private BudgetPlaningItemAdapter _budgetPlaningAdapter;
    private final List<EventComponentResponse> _suggestedCategoryOfferings = new ArrayList<>();
    private final ArrayList<OfferingsCategory> _allcategories = new ArrayList<>();

    public CreateEventStepTwo() {
        // Required empty public constructor
    }
    public static CreateEventStepTwo newInstance() {
        return new CreateEventStepTwo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentCreateEventStepTwoBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();
        MaterialButton addCategoryButton = _binding.addButton;
        addCategoryButton.setOnClickListener(v -> showCategorySelectionDialog());
        return view;
    }

    private void showCategorySelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Category");

        String[] categoryNames = _allcategories.stream()
                .map(OfferingsCategory::getName)
                .toArray(String[]::new);

        builder.setItems(categoryNames, (dialog, which) -> {
            OfferingsCategory selectedCategory = _allcategories.get(which);
            _budgetPlaningAdapter.addCategory(getEmptyComponent(selectedCategory));
        });

        builder.show();
    }

    private EventComponentResponse getEmptyComponent(OfferingsCategory category) {
        return new EventComponentResponse(-1L, 0, "", 0, category);
    }

    @Override
    public void onResume(){
        super.onResume();
        _eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        RecyclerView recyclerView = _binding.eventsRecycleView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        _suggestedCategoryOfferings.clear();
        _allcategories.clear();
        populateData();
        populateCategories();
        Call<EventComponentsResponse> request = ClientUtils.eventsService.findComponentsByEventId(_eventViewModel.eventId);
        request.enqueue(new Callback<EventComponentsResponse>() {
            @Override
            public void onResponse(Call<EventComponentsResponse> call, Response<EventComponentsResponse> response) {
                if(response.isSuccessful()){
                    _suggestedCategoryOfferings.clear();
                    _suggestedCategoryOfferings.addAll(response.body().components);
                    _budgetPlaningAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<EventComponentsResponse> call, Throwable t) {

            }
        });
        this._budgetPlaningAdapter = new BudgetPlaningItemAdapter(requireContext(), this._suggestedCategoryOfferings, (category, price) -> {
            Bundle bundle = new Bundle();
            bundle.putLong("eventId", _eventViewModel.eventId);
            bundle.putDouble("price", price);
            bundle.putLong("category", category.category.getId());
            Navigation.findNavController(_binding.getRoot()).navigate(R.id.action_stepTwoFragment_to_viewProductsFragment, bundle);
        });
        recyclerView.setAdapter(_budgetPlaningAdapter);
    }


    private void populateCategories() {
        Call<GetCategoriesResponse> request = ClientUtils.categoryService.findAll();
        request.enqueue(new Callback<GetCategoriesResponse>() {
            @Override
            public void onResponse(Call<GetCategoriesResponse> call, Response<GetCategoriesResponse> response) {
                Log.d("Tag", String.valueOf(response.code()));
                if (response.isSuccessful()) {
                    _allcategories.clear();
                    for(GetCategoryResponse cat: response.body().categories) {
                        _allcategories.add(new OfferingsCategory(cat.id, cat.name, cat.description));
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCategoriesResponse> call, Throwable t) {

            }
        });
    }

    private void populateData() {
        EventType eventType = _eventViewModel.getEventType().getValue();
        if (eventType != null && eventType.getSuggestedCategories() != null) {
            eventType.getSuggestedCategories().categories.stream().map(
                    c -> _suggestedCategoryOfferings.add(getEmptyComponent(c))).
                    collect(Collectors.toList());
        }
    }

    @Override
    public boolean validate() {
        return true;
    }
}