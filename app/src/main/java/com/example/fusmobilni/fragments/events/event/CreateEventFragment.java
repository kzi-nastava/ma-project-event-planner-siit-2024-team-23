package com.example.fusmobilni.fragments.events.event;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.events.event.forms.CreateEventAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentCreateEventBinding;
import com.example.fusmobilni.fragments.events.event.createEventSteps.CreateEventStepOne;
import com.example.fusmobilni.fragments.events.event.createEventSteps.CreateEventStepThree;
import com.example.fusmobilni.fragments.events.event.createEventSteps.CreateEventStepTwo;
import com.example.fusmobilni.fragments.events.event.createEventSteps.InvitationsFragment;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.requests.events.event.CreateEventRequest;
import com.example.fusmobilni.viewModels.events.event.EventViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class CreateEventFragment extends Fragment {
    private FragmentCreateEventBinding _binding;
    private CreateEventAdapter _eventAdapter;
    private EventViewModel _eventViewModel;
    private ViewPager2 _viewPager;
    private Long eventId;
    private List<Fragment> _fragments;
    private MaterialButton _nextButton;
    private MaterialButton _backButton;

    public CreateEventFragment() {
        // Required empty public constructor
    }
    public static CreateEventFragment newInstance() {
        return new CreateEventFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentCreateEventBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();

        _eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        populateData();
        populateInputs();
        if(Boolean.TRUE.equals(_eventViewModel.getIsUpdating().getValue())) {
            _binding.eventTitle.setText(R.string.update_event);
        }

        _nextButton = _binding.nextButton;
        _backButton = _binding.backButton;
        _viewPager = _binding.viewPager;

        _fragments = new ArrayList<>(List.of(new CreateEventStepOne(), new CreateEventStepTwo(), new CreateEventStepThree(), new InvitationsFragment()));
        _eventAdapter = new CreateEventAdapter(requireActivity(), _fragments);
        _viewPager.setAdapter(_eventAdapter);
        if (getArguments() != null) {
            int currFragment = getArguments().getInt("currFragment");
            eventId =  getArguments().getLong("eventId");
            _eventViewModel.eventId = eventId;
            _backButton.setVisibility(View.VISIBLE);
            _viewPager.setCurrentItem(currFragment);

        }
        _nextButton.setOnClickListener(v -> nextButtonClick());

        _backButton.setOnClickListener(v -> {
            if (_viewPager.getCurrentItem() > 0) {
                _viewPager.setCurrentItem(_viewPager.getCurrentItem() - 1);
                _backButton.setVisibility(View.VISIBLE);
            }
            if (_viewPager.getCurrentItem() == 0){
                _backButton.setVisibility(View.GONE);
            }
            _nextButton.setText(R.string.next);
        });
        return view;
    }

    private void populateInputs() {

    }

    private void populateData() {
    }

    private void nextButtonClick() {
        int currentItem = _viewPager.getCurrentItem();
        if (currentItem <= _eventAdapter.getItemCount() - 1) {
            Fragment currentFragment = _fragments.get(currentItem);

            if (((FragmentValidation) currentFragment).validate()){
                // http request
                if(currentItem == 0){
                    _eventViewModel.submit();
                }

                if(currentItem == _eventAdapter.getItemCount() - 1){
                    _backButton.setVisibility(View.GONE);
                    submitRegistration();
                    _eventViewModel.eventId = null;
                    return;
                }
                _backButton.setVisibility(View.VISIBLE);
                _viewPager.setCurrentItem(currentItem + 1);
                // if we are on fragment change btn text
                if(_viewPager.getCurrentItem() == _eventAdapter.getItemCount() - 1){
                    _nextButton.setText(R.string.finish);
                }
            }
        }
    }
    private void submitRegistration() {
        Toast.makeText(getActivity(), "Profile updated successfully!", Toast.LENGTH_LONG).show();
        Navigation.findNavController(_binding.getRoot()).navigate(R.id.action_createEventFragment_to_eventOrganizerEventDetailsFragment);
    }
}