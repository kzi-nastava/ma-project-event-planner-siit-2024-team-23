package com.example.fusmobilni.fragments.EventTypeFragments;

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

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.EventTypeAdapter;
import com.example.fusmobilni.databinding.FragmentEventTypeBinding;
import com.example.fusmobilni.interfaces.EventTypeListener;
import com.example.fusmobilni.model.EventType;
import com.example.fusmobilni.model.OfferingsCategory;
import com.example.fusmobilni.viewModels.EventTypeViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class EventTypeFragment extends Fragment implements EventTypeListener {
    private FragmentEventTypeBinding _binding;
    private EventTypeViewModel _eventTypeViewModel;
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
        RecyclerView recyclerView = _binding.eventsRecycleView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        populateData();
        this._eventTypeAdapter = new EventTypeAdapter(this._eventTypes, this);
        recyclerView.setAdapter(_eventTypeAdapter);
        _binding.floatingActionButton.setOnClickListener(v-> {
            _eventTypeViewModel.cleanUp();
            Navigation.findNavController(view).navigate(R.id.eventTypeCreationForm);
        });
        return view;
    }
    private void populateData() {
        _offeringCategory.add(new OfferingsCategory(1, "Sport", "Sport je jako zanimljiv i zabavan"));
        _offeringCategory.add(new OfferingsCategory(2, "Food", "Sport je jako zanimljiv i zabavan"));
        _offeringCategory.add(new OfferingsCategory(3, "Slavlje", "Sport je jako zanimljiv i zabavan"));
        _offeringCategory.add(new OfferingsCategory(4, "Hronologija", "Sport je jako zanimljiv i zabavan"));
        _offeringCategory.add(new OfferingsCategory(5, "Jelo", "Sport je jako zanimljiv i zabavan"));
        _eventTypes.add(new EventType(
                1,
                "Sports Event",
                "An event centered around sports activities.",
                Arrays.asList(_offeringCategory.get(0), _offeringCategory.get(1))
        ));
        _eventTypes.add(new EventType(
                2,
                "Food Festival",
                "A festival showcasing various cuisines and food culture.",
                Arrays.asList(_offeringCategory.get(1), _offeringCategory.get(4))
        ));
        _eventTypes.add(new EventType(
                3,
                "Birthday Party",
                "A celebration for someone's birthday.",
                Arrays.asList(_offeringCategory.get(2), _offeringCategory.get(4))
        ));
        _eventTypes.add(new EventType(
                4,
                "Historical Conference",
                "A conference focusing on historical topics and events.",
                Collections.singletonList(_offeringCategory.get(3))
        ));
        _eventTypes.add(new EventType(
                5,
                "Community Gathering",
                "An event for bringing the community together.",
                Arrays.asList(_offeringCategory.get(0), _offeringCategory.get(2), _offeringCategory.get(4))
        ));
    }

    @Override
    public void onDeleteEventType(int position) {
        EventType eventType = this._eventTypes.get(position);
        boolean isActive = !eventType.getActive();
        eventType.setActive(isActive);
        _eventTypeViewModel.setIsUpdating(isActive);
        _eventTypeAdapter.updateItem(position, eventType);
    }

    @Override
    public void onUpdateEventType(int position) {
        EventType eventType = this._eventTypes.get(position);
        this._eventTypeViewModel.populate(eventType);
        Navigation.findNavController(_binding.getRoot()).navigate(R.id.eventType_to_eventTypeUpdate);
    }
}

