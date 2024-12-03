package com.example.fusmobilni.fragments.EventFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.CreateEventAdapter;
import com.example.fusmobilni.adapters.EventOrganizerEventAdapter;
import com.example.fusmobilni.adapters.RegistrationAdapter;
import com.example.fusmobilni.databinding.FragmentCreateEventBinding;
import com.example.fusmobilni.fragments.EventFragments.CreateEventSteps.CreateEventStepOne;
import com.example.fusmobilni.fragments.EventFragments.CreateEventSteps.CreateEventStepTwo;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.viewModels.EventViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class CreateEventFragment extends Fragment {
    private FragmentCreateEventBinding _binding;
    private CreateEventAdapter _eventAdapter;
    private EventViewModel _eventViewModel;
    private ViewPager2 _viewPager;
    private LinearLayout _signUpLayout;
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
        // Inflate the layout for this fragment
        _binding = FragmentCreateEventBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();
        _eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        populateData();
        populateInputs();
        if(Boolean.TRUE.equals(_eventViewModel.getIsUpdating().getValue())) {
            _binding.eventTitle.setText(R.string.update_event);
        }
        _viewPager = _binding.viewPager;
        _signUpLayout = _binding.signUpLayout;
        _nextButton = _binding.nextButton;
        _backButton = _binding.backButton;

        _fragments = new ArrayList<>(List.of(new CreateEventStepOne(), new CreateEventStepTwo()));
        _eventAdapter = new CreateEventAdapter(requireActivity(), _fragments);
        _viewPager.setAdapter(_eventAdapter);

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
                if(currentItem == _eventAdapter.getItemCount() - 1){
                    _backButton.setVisibility(View.GONE);
                    submitRegistration();
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