package com.example.fusmobilni.fragments.users.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.users.ViewProfileEventAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentUserEventsFragmentsBinding;
import com.example.fusmobilni.interfaces.EventClickListener;
import com.example.fusmobilni.model.event.Event;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.users.UserFavoriteEventResponse;
import com.example.fusmobilni.responses.users.UserFavoriteEventsResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFavEventsFragment extends Fragment implements EventClickListener {

    private FragmentUserEventsFragmentsBinding _binding;
    private List<Event> events;
    private RecyclerView listView;

    public UserFavEventsFragment() {
        // Required empty public constructor
    }

    public static UserFavEventsFragment newInstance() {
        return new UserFavEventsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentUserEventsFragmentsBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();
        listView = _binding.eventsList;
        fillEvents();

        return view;
    }
    private void fillEvents() {
        CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();
        LoginResponse user = prefs.getUser();
        Call<UserFavoriteEventsResponse> request = ClientUtils.userService.findFavoriteEvents(user.getId());
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<UserFavoriteEventsResponse> call, @NonNull Response<UserFavoriteEventsResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    events = response.body().getEvents().stream().map(event ->
                            new Event(event.description, event.date, event.title,
                                    event.location.city + ", " + event.location.street, event.getType().name)).
                            collect(Collectors.toList());
                    ViewProfileEventAdapter eventsHorizontalAdapter = new ViewProfileEventAdapter(events, UserFavEventsFragment.this);
                    listView.setAdapter(eventsHorizontalAdapter);
                    eventsHorizontalAdapter.setData(events);
                    if(events.isEmpty()){
                        _binding.eventsList.setVisibility(View.GONE);
                        _binding.emptyMessage.setVisibility(View.VISIBLE);
                    }else{
                        _binding.eventsList.setVisibility(View.VISIBLE);
                        _binding.emptyMessage.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<UserFavoriteEventsResponse> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onEventClick(int position) {
        Event event = events.get(position);
        Toast.makeText(getContext(), event.getTitle(), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putParcelable("event", event);
        Navigation.findNavController(_binding.getRoot()).navigate(R.id.action_viewProfileFragment_to_eventDetailsFragment, bundle);
    }
}