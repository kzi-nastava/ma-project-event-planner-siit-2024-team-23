package com.example.fusmobilni.fragments.events.eventType;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.events.eventType.EventTypeAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentEventTypeBinding;
import com.example.fusmobilni.interfaces.EventTypeListener;
import com.example.fusmobilni.model.event.eventTypes.EventType;
import com.example.fusmobilni.model.event.eventTypes.EventTypeStatus;
import com.example.fusmobilni.model.items.category.OfferingsCategory;
import com.example.fusmobilni.requests.categories.GetCategoriesResponse;
import com.example.fusmobilni.requests.categories.GetCategoryResponse;
import com.example.fusmobilni.requests.eventTypes.EventTypeUpdateRequest;
import com.example.fusmobilni.responses.events.GetEventTypesWithCategoriesResponse;
import com.example.fusmobilni.viewModels.events.eventTypes.EventTypeViewModel;

import java.util.ArrayList;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventTypeFragment extends Fragment implements EventTypeListener {
    private FragmentEventTypeBinding _binding;
    private EventTypeViewModel _eventTypeViewModel;
    private RecyclerView _recyclerView;
    private final ArrayList<EventType> _eventTypes = new ArrayList<>();
    private final ArrayList<OfferingsCategory> _offeringCategory = new ArrayList<>();
    private EventTypeAdapter _eventTypeAdapter;

    public EventTypeFragment() {
        // Required empty public constructor
    }
    public static EventTypeFragment newInstance() {
        return new EventTypeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentEventTypeBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();
        _eventTypeViewModel = new ViewModelProvider(requireActivity()).get(EventTypeViewModel.class);
        _recyclerView = _binding.eventsRecycleView;
        _recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        populateData();

        _binding.floatingActionButton.setOnClickListener(v-> {
            _eventTypeViewModel.cleanUp();
            _eventTypeViewModel.setIsUpdating(false);
            Navigation.findNavController(view).navigate(R.id.eventTypeCreationForm);
        });
        return view;
    }
    private void populateData() {
        Call<GetCategoriesResponse> request = ClientUtils.categoryService.findAll();
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<GetCategoriesResponse> call, @NonNull Response<GetCategoriesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _offeringCategory.clear();
                    for (GetCategoryResponse cat : response.body().categories) {
                        _offeringCategory.add(new OfferingsCategory(cat.id, cat.name, cat.description));
                    }
                    _eventTypeViewModel.setAllCategories(_offeringCategory);
                    populateEvents();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetCategoriesResponse> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Error fetching categories from server!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void populateEvents(){
        Call<GetEventTypesWithCategoriesResponse> request = ClientUtils.eventTypeService.findAllWIthSuggestedCategories();
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<GetEventTypesWithCategoriesResponse> call, @NonNull Response<GetEventTypesWithCategoriesResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    _eventTypes.clear();
                    _eventTypes.addAll(response.body().eventTypes);
                }
                _eventTypeAdapter = new EventTypeAdapter(_eventTypes, EventTypeFragment.this);
                _recyclerView.setAdapter(_eventTypeAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<GetEventTypesWithCategoriesResponse> call, @NonNull Throwable t) {
            }
        });
    }



    @Override
    public void onDeleteEventType(int position) {
        EventType eventType = this._eventTypes.get(position);
        EventTypeStatus isActive = eventType.getActive().equals(EventTypeStatus.ACTIVATED) ? EventTypeStatus.DEACTIVATED: EventTypeStatus.ACTIVATED;
        eventType.setActive(isActive);
        ArrayList<Long> categories = eventType.getSuggestedCategories().categories.stream()
                .map(OfferingsCategory::getId).collect(Collectors.toCollection(ArrayList::new));

        Call<EventType> request = ClientUtils.eventTypeService.updateEventType(eventType.getId(),
                new EventTypeUpdateRequest(eventType.getName(), eventType.getDescription(), categories, eventType.isActive()));
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<EventType> call, @NonNull Response<EventType> response) {
                if(response.isSuccessful() && response.body() != null){
                    _eventTypeViewModel.setIsUpdating(response.body().getActive().equals(EventTypeStatus.ACTIVATED));
                    _eventTypeAdapter.updateItem(position, response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventType> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onUpdateEventType(int position) {
        EventType eventType = this._eventTypes.get(position);
        this._eventTypeViewModel.populate(eventType);
        Navigation.findNavController(_binding.getRoot()).navigate(R.id.eventType_to_eventTypeUpdate);
    }
}

