package com.example.fusmobilni.fragments;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.EventSelectionSpinnerAdapter;
import com.example.fusmobilni.databinding.FragmentServiceReservationBinding;
import com.example.fusmobilni.model.Event;
import com.example.fusmobilni.model.Service;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ServiceReservationFragment extends Fragment {

    private FragmentServiceReservationBinding _binding;
    private boolean toInputsDisabled = new Random(System.currentTimeMillis()).nextBoolean();
    private Spinner _eventSelectionSpinner;
    private EventSelectionSpinnerAdapter _adapter;
    private List<Event> _events;
    private Service _service;

    private MaterialDatePicker _datePicker;

    private EditText _hoursInputFrom;
    private EditText _minutesInputFrom;
    private TextInputEditText _hoursInputTo;
    private TextInputEditText _minutesInputTo;

    public ServiceReservationFragment() {
        // Required empty public constructor
    }

    public static ServiceReservationFragment newInstance(String param1, String param2) {
        ServiceReservationFragment fragment = new ServiceReservationFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentServiceReservationBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();

        _service = getArguments().getParcelable("service");
        _events = fillEvents();


        _binding.textViewServiceDetailsTitleReservation.setText(_service.getName());

        _binding.textViewSelectedDate.setText("");
        _binding.buttonOpenDatePicker.setOnClickListener(v -> {
            _datePicker.show(getParentFragmentManager(), "Pick a date");
            _binding.buttonOpenDatePicker.setEnabled(false);
        });

        initializeHoursAndMinutes();

        initializeTimePicker(_hoursInputFrom, _minutesInputFrom, _binding.buttonPickTimeFrom);
        initializeTimePicker(_hoursInputTo, _minutesInputTo, _binding.buttonPickTimeTo);
        initializeSpinner();
        initializeDatePicker();


        disabledToInputs();

        return root;

    }

    private void initializeTimePicker(EditText inputHours, EditText inputMinutes, MaterialButton activator) {
        MaterialTimePicker timePicker = new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H).setHour(0).setMinute(0)
                .setTitleText("Select time").build();
        MaterialTimePicker finalTimePicker = timePicker;
        timePicker.addOnPositiveButtonClickListener(v -> {
            int hour = finalTimePicker.getHour();
            int minute =
                    Math.round(finalTimePicker.getMinute() / 5.0f) * 5;
            inputHours.setText(String.valueOf(hour));
            inputMinutes.setText(String.valueOf(minute));
        });
        activator.setOnClickListener(v -> {
            finalTimePicker.show(getParentFragmentManager(), finalTimePicker.toString());
        });
    }

    private void initializeHoursAndMinutes() {
        _hoursInputFrom = _binding.hoursInputFrom;
        _minutesInputFrom = _binding.minutesInputFrom;
        _hoursInputTo = _binding.hoursInputTo;
        _minutesInputTo = _binding.minutesInputTo;
        _hoursInputTo.setEnabled(false);
        _minutesInputTo.setEnabled(false);
        _hoursInputFrom.setEnabled(false);
        _minutesInputFrom.setEnabled(false);

    }

    private void initializeSpinner() {
        _eventSelectionSpinner = _binding.eventSelectionSpinner;
        _adapter = new EventSelectionSpinnerAdapter(getContext(), _events);

        _eventSelectionSpinner.setAdapter(_adapter);

        _eventSelectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void disabledToInputs() {
        if (!toInputsDisabled) {
            return;
        }
        _binding.cardViewToInputs.setStrokeColor(getResources().getColor(R.color.bg_gray));
        _binding.buttonPickTimeTo.setEnabled(false);
        _binding.buttonPickTimeTo.setBackgroundTintList(getResources().getColorStateList(R.color.bg_gray));
        _binding.buttonPickTimeTo.setTextColor(getResources().getColor(R.color.white));
    }

    private void initializeDatePicker() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setValidator(DateValidatorPointForward.now());

        builder.setTitleText("Select a date");
        builder.setCalendarConstraints(constraintsBuilder.build());

        _datePicker = builder.build();


        _datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Date selectedDate = new Date((Long) selection);

                // Format the date
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                String formattedDate = dateFormat.format(selectedDate);

                // Display the selected date
                _binding.textViewSelectedDate.setText(formattedDate);
                _binding.buttonOpenDatePicker.setEnabled(true);

            }
        });

    }


    private ArrayList<Event> fillEvents() {
        ArrayList<Event> e = new ArrayList<>();
        e.add(new Event("Food and Wine Tasting", "12-07-2024", "Napa Valley Vineyard", "Food"));
        e.add(new Event("Tech Innovators Conference", "15-08-2024", "Silicon Valley Expo Center", "Tech"));
        e.add(new Event("Autumn Art and Sculpture Exhibition", "18-09-2024", "Paris Art Museum", "Art"));
        e.add(new Event("Global Startup Pitch Event", "22-11-2024", "Berlin Startup Hub", "Tech"));
        e.add(new Event("International Film and Documentary Festival", "05-11-2024", "Toronto Film Centre", "Art"));

        e.add(new Event("New Year Gala", "01-01-2024", "Times Square", "Travel"));
        e.add(new Event("Valentine's Day Dance", "10-02-2024", "City Hall Ballroom", "Music"));
        e.add(new Event("Winter Sports Championship", "20-02-2024", "Aspen Ski Resort", "Sports"));
        e.add(new Event("Spring Fashion Week", "15-03-2024", "New York City", "Fashion"));
        e.add(new Event("Cherry Blossom Festival", "30-03-2024", "Washington D.C.", "Travel"));

        e.add(new Event("Earth Day Celebration", "22-04-2024", "Central Park", "Health"));
        e.add(new Event("Music Festival", "10-05-2024", "Coachella Valley", "Music"));
        e.add(new Event("Memorial Day Parade", "24-05-2024", "Chicago", "Travel"));
        e.add(new Event("Summer Food Festival", "07-06-2024", "Los Angeles", "Food"));
        e.add(new Event("Pride Parade", "15-06-2024", "San Francisco", "Travel"));

        e.add(new Event("Independence Day Fireworks", "04-07-2024", "Washington D.C.", "Travel"));
        e.add(new Event("Bastille Day Celebration", "14-07-2024", "Paris", "Travel"));
        e.add(new Event("International Comic Con", "30-07-2024", "San Diego", "Art"));
        e.add(new Event("Outdoor Yoga Festival", "10-08-2024", "Bali", "Health"));
        e.add(new Event("Gastronomy Festival", "20-08-2024", "Barcelona", "Food"));

        e.add(new Event("Labor Day Weekend", "01-09-2024", "New York", "Travel"));
        e.add(new Event("Tech Startups Expo", "10-09-2024", "Austin", "Tech"));
        e.add(new Event("International Film Festival", "25-09-2024", "Venice", "Art"));
        e.add(new Event("Oktoberfest", "08-10-2024", "Munich", "Food"));
        e.add(new Event("Halloween Spooktacular", "31-10-2024", "New Orleans", "Travel"));

        e.add(new Event("Thanksgiving Parade", "10-11-2024", "New York", "Travel"));
        e.add(new Event("Black Friday Shopping Event", "22-11-2024", "Mall of America", "Fashion"));
        e.add(new Event("Christmas Market", "05-12-2024", "Prague", "Food"));
        e.add(new Event("Winter Wonderland Festival", "20-12-2024", "London", "Travel"));
        e.add(new Event("New Year's Eve Countdown", "31-12-2024", "Sydney", "Travel"));

        e.add(new Event("Chocolate Festival", "14-02-2024", "Zurich", "Food"));
        e.add(new Event("St. Patrick's Day Parade", "05-03-2024", "Dublin", "Travel"));
        e.add(new Event("International Women’s Day Conference", "17-03-2024", "Los Angeles", "Education"));
        e.add(new Event("May Day Celebration", "29-04-2024", "Berlin", "Travel"));
        e.add(new Event("Flower Festival", "12-05-2024", "Amsterdam", "Travel"));

        e.add(new Event("Midsummer Festival", "19-06-2024", "Stockholm", "Travel"));
        e.add(new Event("World Music Festival", "24-07-2024", "Austin", "Music"));
        e.add(new Event("National Book Festival", "13-08-2024", "Washington D.C.", "Education"));
        e.add(new Event("Sustainable Living Expo", "21-09-2024", "San Francisco", "Health"));
        e.add(new Event("Haunted House Experience", "18-10-2024", "Los Angeles", "Travel"));

        e.add(new Event("Dia de los Muertos Festival", "02-11-2024", "Mexico City", "Travel"));
        e.add(new Event("Winter Solstice Celebration", "06-12-2024", "Reykjavik", "Travel"));
        e.add(new Event("Christmas Concert", "25-12-2024", "London", "Music"));
        e.add(new Event("Boxing Day Sales", "28-12-2024", "Toronto", "Fashion"));
        e.add(new Event("Epiphany Celebration", "03-01-2024", "Madrid", "Education"));

        e.add(new Event("Winter Carnival", "10-02-2024", "Quebec", "Travel"));
        e.add(new Event("Wi Art and Design Fair", "15-03-2024", "Tokyo", "Art"));

        return e;
    }

}