package com.example.fusmobilni.fragments.events.event;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.loading.LoadingCardHorizontalAdapter;
import com.example.fusmobilni.adapters.users.eventOrganizer.EventOrganizerEventAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentEventOrganizerEventDetailsBinding;
import com.example.fusmobilni.interfaces.EOEventClickListener;
import com.example.fusmobilni.model.event.Event;
import com.example.fusmobilni.model.users.User;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.events.home.EventHomeResponse;
import com.example.fusmobilni.responses.events.home.EventPreviewResponse;
import com.example.fusmobilni.responses.events.home.EventsHomeResponse;
import com.example.fusmobilni.responses.events.home.EventsPreviewResponse;
import com.example.fusmobilni.viewModels.events.event.EventViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventOrganizerEventFragment extends Fragment implements EOEventClickListener {
    private FragmentEventOrganizerEventDetailsBinding _binding;
    private EventViewModel _eventViewModel;
    private final ArrayList<EventPreviewResponse> _events = new ArrayList<>();
    private EventOrganizerEventAdapter _eventAdapter;
    private RecyclerView recyclerView;

    public EventOrganizerEventFragment() {
        // Required empty public constructor
    }
    public static EventOrganizerEventFragment newInstance() {
        return new EventOrganizerEventFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentEventOrganizerEventDetailsBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();
        _eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        recyclerView = _binding.eventsRecycleView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initializeLoadingCards();
        populateData();
        _binding.floatingActionButton.setOnClickListener(v-> {
            _eventViewModel.cleanUp();
            Navigation.findNavController(view).navigate(R.id.action_eventOrganizerEventDetailsFragment_to_createEventFragment);
        });
        return view;
    }

    private void populateData() {
        CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();
        LoginResponse user = prefs.getUser();
        if (user == null){
            return;
        }
        Call<EventsPreviewResponse> request = ClientUtils.userService.findEventsForEventOrganizer(user.getId());
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<EventsPreviewResponse> call, @NonNull Response<EventsPreviewResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    _events.clear();
                   _events.addAll(response.body().getPreviews());
                    _eventAdapter = new EventOrganizerEventAdapter(_events, EventOrganizerEventFragment.this);
                    recyclerView.setAdapter(_eventAdapter);
                    turnOffShimmer(_binding.eventsListLoading, _binding.eventsRecycleView);
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventsPreviewResponse> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Cannot fetch events from the server!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onEventClick(int position) {
        EventPreviewResponse event = _events.get(position);
        Toast.makeText(getContext(), event.getTitle(), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putLong("eventId", event.getId());
        Navigation.findNavController(_binding.getRoot()).navigate(R.id.action_eventOrganizerEventDetailsFragment_to_eventDetailsFragment, bundle);
    }

    @Override
    public void onStatsClick(int position) {
        EventPreviewResponse event = _events.get(position);
        Toast.makeText(getContext(), event.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditClick(int position) {
        EventPreviewResponse event = _events.get(position);
        Toast.makeText(getContext(), event.getTitle(), Toast.LENGTH_SHORT).show();
        //TODO
//        this._eventViewModel.populate(event);
//        Navigation.findNavController(_binding.getRoot()).navigate(R.id.action_eventOrganizerEventDetailsFragment_to_createEventFragment);
    }
    public static void turnOffShimmer(RecyclerView loadingCardsView, RecyclerView actualCards) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            loadingCardsView.setAdapter(new LoadingCardHorizontalAdapter(0));
            loadingCardsView.setVisibility(View.GONE);
            actualCards.setVisibility(View.VISIBLE);
        }, 0);
    }
    private void initializeLoadingCards() {
        _binding.eventsListLoading.setAdapter(new LoadingCardHorizontalAdapter(7));
    }
}