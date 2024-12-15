package com.example.fusmobilni.fragments.events.event.createEventSteps;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentCreateEventStepOneBinding;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.model.event.EventType;
import com.example.fusmobilni.model.items.category.OfferingsCategory;
import com.example.fusmobilni.viewModels.events.event.EventViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class CreateEventStepOne extends Fragment  implements FragmentValidation {
    private FragmentCreateEventStepOneBinding _binding;
    private EventViewModel _eventViewModel;
    private TextInputEditText _eventDate;
    private TextInputEditText _eventTime;
    private AutoCompleteTextView _eventTypeInput;
    private AutoCompleteTextView _privacyTypeInput;
    private String[] _privacyType;
    private final ArrayList<OfferingsCategory> _offeringCategory = new ArrayList<>();
    private final ArrayList<EventType> _eventTypes = new ArrayList<>();
    private ChipGroup _selectedEventCategories;

    public CreateEventStepOne() {
        // Required empty public constructor
    }
    public static CreateEventStepOne newInstance() {
        return new CreateEventStepOne();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentCreateEventStepOneBinding.inflate(getLayoutInflater());
        _eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        populateData();
        View view = _binding.getRoot();
        _privacyType = new String[] {"Private", "Public"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.dropdown_item,
                _privacyType
        );
        ArrayAdapter<EventType> eventTypeAdapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.dropdown_item,
                _eventTypes
        );
        _selectedEventCategories = _binding.selectedCategoriesChipGroup;
        _eventDate = _binding.etEventDate;
        _eventTime = _binding.etEventTime;
        _privacyTypeInput = _binding.privacyType;
        _privacyTypeInput.setAdapter(adapter);
        _eventTypeInput = _binding.eventType;
        _eventTypeInput.setAdapter(eventTypeAdapter);
        _eventDate.setOnClickListener(v-> showDatePickerDialog());
        _eventTime.setOnClickListener(v-> showTimePickerDialog());
        _eventTypeInput.setOnItemClickListener((parent, cbView, position, id) -> {
            EventType selectedEventType = (EventType) parent.getItemAtPosition(position);
            _eventViewModel.setEventType(selectedEventType);
            populateChipGroup(selectedEventType);
        });

        return view;
    }

    private void populateChipGroup(EventType eventType) {
        _selectedEventCategories.removeAllViews();

        List<OfferingsCategory> categories = eventType.getSuggestedCategories();
        if (categories != null && !categories.isEmpty()) {
            for (OfferingsCategory category : categories) {
                Chip chip = new Chip(requireContext());
                chip.setText(category.getName());
                chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary_blue_900));
                chip.setChipIconResource(R.drawable.ic_category_arts_active);
                chip.setChipIconTint(ContextCompat.getColorStateList(requireContext(), R.color.primary_blue_900));
                chip.setChipBackgroundColorResource(android.R.color.white);
                chip.setChipStrokeColor(ContextCompat.getColorStateList(requireContext(), R.color.primary_blue_900));
                chip.setChipStrokeWidth(2);
                chip.setCloseIconVisible(false);
                _selectedEventCategories.addView(chip);
            }
        } else {
            Toast.makeText(requireContext(), "No categories available for this event type", Toast.LENGTH_SHORT).show();
        }
    }
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create and show a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                R.style.CustomDatePickerDialogTheme,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format the selected date and set it in the TextInputEditText
                    String selectedDate = String.format(Locale.getDefault(), "%02d-%02d-%04d",
                            selectedDay, selectedMonth + 1, selectedYear);
                    _eventDate.setText(selectedDate);
                }, year, month, day);

        datePickerDialog.show();
    }
    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Use the custom style
        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                R.style.CustomTimePickerDialogTheme,
                (view, selectedHour, selectedMinute) -> {
                    String selectedTime = String.format(Locale.getDefault(), "%02d:%02d",
                            selectedHour, selectedMinute);
                    _eventTime.setText(selectedTime);
                }, hour, minute, true);

        timePickerDialog.show();
    }
    private void populateData() {
        _offeringCategory.add(new OfferingsCategory(1, "Sport", "Sport je jako zanimljiv i zabavan"));
        _offeringCategory.add(new OfferingsCategory(2, "Food", "Sport je jako zanimljiv i zabavan"));
        _offeringCategory.add(new OfferingsCategory(3, "Slavlje", "Sport je jako zanimljiv i zabavan"));
        _offeringCategory.add(new OfferingsCategory(4, "Hronologija", "Sport je jako zanimljiv i zabavan"));
        _offeringCategory.add(new OfferingsCategory(5, "Jelo", "Sport je jako zanimljiv i zabavan"));
        _eventTypes.add(new EventType(
                null,
                "All",
                "",
                new ArrayList<>()
        ));
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
    public boolean validate() {
        String eventTypeInput = Objects.requireNonNull(_binding.eventType.getEditableText()).toString().trim();
        EventType eventType = _eventViewModel.getEventType().getValue();
        if (eventTypeInput.isEmpty()) {
            _binding.eventType.setError("Event title is required");
            return false;
        } else {
            _binding.eventType.setError(null);
        }

        // Retrieve values from input fields
        String eventTitle = Objects.requireNonNull(_binding.eventTitleInput.getEditText()).getText().toString().trim();
        String eventDescription = Objects.requireNonNull(_binding.eventDescriptionInput.getEditText()).getText().toString().trim();
        String eventDate = Objects.requireNonNull(_binding.etEventDate.getText()).toString().trim();
        String eventTime = Objects.requireNonNull(_binding.etEventTime.getText()).toString().trim();
        String maxVisitors = Objects.requireNonNull(_binding.maxVisitorsInput.getEditText()).getText().toString().trim();
        String privacyType = Objects.requireNonNull(_binding.privacyType.getText()).toString().trim();

        // Validate each field
        if (eventTitle.isEmpty()) {
            _binding.eventTitleInput.setErrorEnabled(true);
            _binding.eventTitleInput.setError("Event title is required");
            return false;
        } else {
            _binding.eventTitleInput.setError(null);
            _binding.eventTitleInput.setErrorEnabled(false);
        }

        if (eventDescription.isEmpty()) {
            _binding.eventDescriptionInput.setErrorEnabled(true);
            _binding.eventDescriptionInput.setError("Event description is required");
            return false;
        } else {
            _binding.eventDescriptionInput.setError(null);
            _binding.eventDescriptionInput.setErrorEnabled(false);
        }

        if (eventDate.isEmpty()) {
            _binding.etEventDate.setError("Event date is required");
            return false;
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date eventDateParsed = dateFormat.parse(eventDate);
                if (eventDate == null) {
                    _binding.etEventDate.setError("Invalid date format");
                    return false;
                }
//                _eventViewModel.setEventDate(eventDate); // Set the Date object to the ViewModel
            } catch (ParseException e) {
                _binding.etEventDate.setError("Invalid date format");
                return false;
            }
            _binding.etEventDate.setError(null);
        }

        // Validate event time
        if (eventTime.isEmpty()) {
            _binding.etEventTime.setError("Event time is required");
            return false;
        } else {
            try {
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm"); // Adjust format as necessary
                // For Time, you could either use Time (if defined as an object) or convert to Date (with only the time part)
                Date eventTimeParsed = timeFormat.parse(eventTime);
                if (eventTime == null) {
                    _binding.etEventTime.setError("Invalid time format");
                    return false;
                }
//                _eventViewModel.setTime(eventTime); // Set the time object to the ViewModel
            } catch (ParseException e) {
                _binding.etEventTime.setError("Invalid time format");
                return false;
            }
            _binding.etEventTime.setError(null);
        }

        if (maxVisitors.isEmpty()) {
            _binding.maxVisitorsInput.setErrorEnabled(true);
            _binding.maxVisitorsInput.setError("Max visitors is required");
            return false;
        } else {
            _binding.maxVisitorsInput.setError(null);
            _binding.maxVisitorsInput.setErrorEnabled(false);
        }

        if (privacyType.isEmpty()) {
            _binding.privacyTypeLayout.setErrorEnabled(true);
            _binding.privacyTypeLayout.setError("Privacy type is required");
            return false;
        } else {
            _binding.privacyTypeLayout.setError(null);
            _binding.privacyTypeLayout.setErrorEnabled(false);
        }

        // Optionally, you can add logic to process the values (e.g., send to a ViewModel)
        _eventViewModel.setTitle(eventTitle);
        _eventViewModel.setDescription(eventDescription);
        _eventViewModel.setDate(eventDate);
        _eventViewModel.setTime(eventTime);
        _eventViewModel.setMaxParticipants(Integer.valueOf(maxVisitors));
        _eventViewModel.setIsPublic(privacyType == "Public"? true: false);

        return eventType != null;
    }

}