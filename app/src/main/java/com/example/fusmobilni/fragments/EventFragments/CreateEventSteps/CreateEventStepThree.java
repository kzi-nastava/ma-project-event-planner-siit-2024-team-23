package com.example.fusmobilni.fragments.EventFragments.CreateEventSteps;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.AgendaActivityEditableAdapter;
import com.example.fusmobilni.databinding.FragmentCreateEventStepThreeBinding;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.model.AgendaActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class CreateEventStepThree extends Fragment implements FragmentValidation {
    FragmentCreateEventStepThreeBinding _binding;
    private AgendaActivityEditableAdapter _adapter;
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
        agenda = fillAgenda();
        _adapter = new AgendaActivityEditableAdapter(agenda);
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

        Time startTime = convertStringToTime(startTimeStr);
        Time endTime = convertStringToTime(endTimeStr);

        AgendaActivity newActivity = new AgendaActivity(0, startTime, endTime, activityTitle, activityDescription);
        _binding.eventTitleInput.setEndIconVisible(false);
        _binding.eventDescriptionInput.setEndIconVisible(false);
        _binding.etEndTimeInput.setEndIconVisible(false);
        _binding.etStartTimeInput.setEndIconVisible(false);
        Toast.makeText(requireContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
        _adapter.addAgendaActivity(newActivity);
    }

    private Time convertStringToTime(String timeStr) {
        String[] parts = timeStr.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        return new Time(hour, minute, 0);
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