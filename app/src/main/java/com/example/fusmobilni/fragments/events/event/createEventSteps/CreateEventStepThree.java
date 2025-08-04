package com.example.fusmobilni.fragments.events.event.createEventSteps;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.events.AgendaActivityAdapter;
import com.example.fusmobilni.adapters.events.AgendaActivityEditableAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentCreateEventStepThreeBinding;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.model.event.AgendaActivity;
import com.example.fusmobilni.requests.events.event.AgendaActivitiesResponse;
import com.example.fusmobilni.requests.events.event.CreateAgendaActivityRequest;
import com.example.fusmobilni.responses.events.components.AgendaActivityResponse;
import com.example.fusmobilni.viewModels.events.event.EventViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEventStepThree extends Fragment implements FragmentValidation {
    FragmentCreateEventStepThreeBinding _binding;
    private AgendaActivityEditableAdapter _adapter;

    private EventViewModel _eventViewModel;
    private TextInputEditText _endTime;
    private TextInputEditText _startTime;
    private ArrayList<AgendaActivity> agenda = new ArrayList<>();
    public CreateEventStepThree() {
        // Required empty public constructor
    }
    public static CreateEventStepThree newInstance() {
        return new CreateEventStepThree();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentCreateEventStepThreeBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();
        _eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        _adapter = new AgendaActivityEditableAdapter(agenda, _eventViewModel.eventId);
        Call<AgendaActivitiesResponse> request = ClientUtils.eventsService.findAllAgendasByEventId(_eventViewModel.eventId);
        request.enqueue(new Callback<AgendaActivitiesResponse>() {
            @Override
            public void onResponse(Call<AgendaActivitiesResponse> call, Response<AgendaActivitiesResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    agenda.clear();
                    for (AgendaActivityResponse a: response.body().eventActivities) {
                        agenda.add(a.toAgenda());
                    }
                    _adapter = new AgendaActivityEditableAdapter(agenda, _eventViewModel.eventId);
                    _binding.eventActivitiesRecycleView.setAdapter(_adapter);
                } else {
                    Log.d("Tag", "dodavanje "+  String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<AgendaActivitiesResponse> call, Throwable t) {
                Log.d("Tag", t.getMessage());
            }
        });
        TextView addCategoryButton = _binding.addButton;
        addCategoryButton.setOnClickListener(v -> addActivity());
        _binding.eventActivitiesRecycleView.setAdapter(_adapter);
        _endTime = _binding.etEndTime;
        _startTime = _binding.etStartTime;
        _binding.eventTitleInput.setEndIconVisible(false);
        _binding.eventDescriptionInput.setEndIconVisible(false);
        _binding.etEndTimeInput.setEndIconVisible(false);
        _binding.etStartTimeInput.setEndIconVisible(false);
        _endTime.setOnClickListener(v-> showTimePickerDialog(_endTime));
        _startTime.setOnClickListener(v-> showTimePickerDialog(_startTime));
        return view;
    }

    private void addActivity() {
        String activityTitle = Objects.requireNonNull(_binding.eventTitleInput.getEditText()).getText().toString().trim();
        String activityDescription = Objects.requireNonNull(_binding.eventDescriptionInput.getEditText()).getText().toString().trim();
        String startTimeStr = Objects.requireNonNull(_startTime.getText()).toString();
        String endTimeStr = Objects.requireNonNull(_endTime.getText()).toString();
        if(activityTitle.isEmpty() || activityDescription.isEmpty() || startTimeStr.isEmpty() || endTimeStr.isEmpty()){
            _binding.eventTitleInput.setEndIconVisible(activityTitle.isEmpty());
            _binding.eventDescriptionInput.setEndIconVisible(activityDescription.isEmpty());
            _binding.etEndTimeInput.setEndIconVisible(activityDescription.isEmpty());
            _binding.etStartTimeInput.setEndIconVisible(activityDescription.isEmpty());
            Toast.makeText(requireContext(), "Fill the fields first!", Toast.LENGTH_SHORT).show();
            return;
        }

        CreateAgendaActivityRequest request = new CreateAgendaActivityRequest(activityTitle, _eventViewModel.eventId,
                endTimeStr, startTimeStr, activityDescription);

        Call<AgendaActivityResponse> createRequest = ClientUtils.eventsService.createAgenda(_eventViewModel.eventId, request);
        createRequest.enqueue(new Callback<AgendaActivityResponse>() {
            @Override
            public void onResponse(Call<AgendaActivityResponse> call, Response<AgendaActivityResponse> response) {
                if (response.isSuccessful()) {
                    agenda.add(response.body().toAgenda());
                    _adapter.notifyDataSetChanged();
                    Toast.makeText(requireContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AgendaActivityResponse> call, Throwable t) {

            }
        });


        _binding.eventTitleInput.setEndIconVisible(false);
        _binding.eventDescriptionInput.setEndIconVisible(false);
        _binding.etEndTimeInput.setEndIconVisible(false);
        _binding.etStartTimeInput.setEndIconVisible(false);
    }

    private void showTimePickerDialog(TextInputEditText timeInput) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Use the custom style
        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                R.style.CustomTimePickerDialogTheme,
                (view, selectedHour, selectedMinute) -> {
                    String selectedTime = String.format(Locale.getDefault(), "%02d:%02d",
                            selectedHour, selectedMinute);
                    timeInput.setText(selectedTime);
                }, hour, minute, true);

        timePickerDialog.show();
    }

    @Override
    public boolean validate() {
        return true;
    }
}