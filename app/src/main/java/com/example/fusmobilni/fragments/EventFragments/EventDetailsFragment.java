package com.example.fusmobilni.fragments.EventFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.AgendaActivityAdapter;
import com.example.fusmobilni.databinding.FragmentEventDetailsBinding;
import com.example.fusmobilni.model.AgendaActivity;
import com.example.fusmobilni.model.Event;

import java.sql.Time;
import java.util.ArrayList;

public class EventDetailsFragment extends Fragment {
    private FragmentEventDetailsBinding _binding;
    private boolean favorite = false;
    private AgendaActivityAdapter _adapter;
    private ArrayList<AgendaActivity> agenda = new ArrayList<>();

    public EventDetailsFragment() {
        // Required empty public constructor
    }

    public static EventDetailsFragment newInstance(String param1, String param2) {
        EventDetailsFragment fragment = new EventDetailsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        _binding = FragmentEventDetailsBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        assert getArguments() != null;
        Event event = getArguments().getParcelable("event");
        _binding.eventDetailsText.setText(event.getTitle());
        _binding.textViewEventLocationHorizontal.setText(event.getLocation());
        _binding.textViewOrganizerEventDetails.setText("Ibrahimovic");
        _binding.imageView5.setImageResource(R.drawable.person);
        _binding.textViewEventDescriptionDetails.setText(event.getDescription());
        initializeFavoriteButton();
        agenda = fillAgenda();
        _adapter = new AgendaActivityAdapter(agenda);
        _binding.eventActivitiesRecycleView.setAdapter(_adapter);
        return root;
    }

    private void initializeFavoriteButton() {
        _binding.favoriteButton.setOnClickListener(v -> {
            favorite = !favorite;
            _binding.favoriteButton.animate()
                    .alpha(0f)
                    .setDuration(100)
                    .withEndAction(() -> {
                        _binding.favoriteButton.setIconResource(favorite ? R.drawable.ic_heart_full : R.drawable.ic_heart);

                        _binding.favoriteButton.animate()
                                .alpha(1f)
                                .setDuration(100)
                                .start();
                    })
                    .start();
        });
    }

    private ArrayList<AgendaActivity> fillAgenda() {
        ArrayList<AgendaActivity> a = new ArrayList<>();
        a.add(new AgendaActivity(
                1,
                Time.valueOf("09:00:00"),
                Time.valueOf("10:00:00"),
                "Welcome and Opening Remarks",
                "Kick off the event with opening speeches and a warm welcome."
        ));
        a.add(new AgendaActivity(
                2,
                Time.valueOf("10:00:00"),
                Time.valueOf("11:00:00"),
                "Keynote Speech",
                "A renowned speaker shares insights on the event's theme."
        ));
        a.add(new AgendaActivity(
                3,
                Time.valueOf("11:15:00"),
                Time.valueOf("12:15:00"),
                "Panel Discussion",
                "Industry leaders discuss trends and challenges."
        ));
        a.add(new AgendaActivity(
                4,
                Time.valueOf("12:15:00"),
                Time.valueOf("13:30:00"),
                "Lunch Break",
                "Enjoy a buffet lunch and network with other attendees."
        ));
        a.add(new AgendaActivity(
                5,
                Time.valueOf("13:30:00"),
                Time.valueOf("14:30:00"),
                "Workshop: Innovation in Action",
                "An interactive workshop on implementing innovative ideas."
        ));
        a.add(new AgendaActivity(
                6,
                Time.valueOf("14:45:00"),
                Time.valueOf("15:45:00"),
                "Breakout Sessions",
                "Choose from multiple sessions focused on specific topics."
        ));
        a.add(new AgendaActivity(
                7,
                Time.valueOf("16:00:00"),
                Time.valueOf("16:30:00"),
                "Coffee Break",
                "Relax and recharge with refreshments."
        ));
        a.add(new AgendaActivity(
                8,
                Time.valueOf("16:30:00"),
                Time.valueOf("17:30:00"),
                "Closing Ceremony",
                "Wrap up the event with final remarks and a thank you to participants."
        ));

        return a;
    }

}