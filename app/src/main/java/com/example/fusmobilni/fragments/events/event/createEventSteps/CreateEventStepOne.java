package com.example.fusmobilni.fragments.events.event.createEventSteps;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentCreateEventStepOneBinding;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.model.event.eventTypes.EventType;
import com.example.fusmobilni.model.items.category.OfferingsCategory;
import com.example.fusmobilni.responses.events.GetEventByIdResponse;
import com.example.fusmobilni.responses.events.GetEventTypesWithCategoriesResponse;
import com.example.fusmobilni.viewModels.events.event.EventViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateEventStepOne extends Fragment  implements FragmentValidation {
    private FragmentCreateEventStepOneBinding _binding;
    private EventViewModel _eventViewModel;
    private TextInputEditText _eventDate;
    private TextInputEditText _eventTime;
    private AutoCompleteTextView _eventTypeInput;
    private AutoCompleteTextView _privacyTypeInput;
    private String[] _privacyType;
    private ArrayList<EventType> _eventTypes = new ArrayList<>();
    private ChipGroup _selectedEventCategories;
    private ArrayAdapter<EventType> eventTypeAdapter;

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
        if(_eventViewModel.eventId != null){
            Call<GetEventByIdResponse> request = ClientUtils.eventsService.findById(_eventViewModel.eventId);
            request.enqueue(new Callback<GetEventByIdResponse>() {
                @Override
                public void onResponse(Call<GetEventByIdResponse> call, Response<GetEventByIdResponse> response) {
                    if(response.isSuccessful()){
                        populateInputs(response.body());
                    }
                }

                @Override
                public void onFailure(Call<GetEventByIdResponse> call, Throwable t) {

                }
            });
        }

        View view = _binding.getRoot();
        _privacyType = new String[] {"Private", "Public"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.dropdown_item,
                _privacyType
        );
        _selectedEventCategories = _binding.selectedCategoriesChipGroup;
        _eventDate = _binding.etEventDate;
        _eventTime = _binding.etEventTime;
        _privacyTypeInput = _binding.privacyType;
        _privacyTypeInput.setAdapter(adapter);
        _eventTypeInput = _binding.eventType;
        _eventDate.setOnClickListener(v-> showDatePickerDialog());
        _eventTime.setOnClickListener(v-> showTimePickerDialog());
        _eventTypeInput.setOnItemClickListener((parent, cbView, position, id) -> {
            EventType selectedEventType = (EventType) parent.getItemAtPosition(position);
            _eventViewModel.setEventType(selectedEventType);
            populateChipGroup(selectedEventType);
        });
        populateData();

        return view;
    }

    private void populateInputs(GetEventByIdResponse body) {
        _binding.titleInput.setText(body.getTitle());
        _binding.descriptionInput.setText(body.getDescription());
        _binding.etEventTime.setText(body.getTime());
        _binding.etEventDate.setText(body.getDate());
        _binding.eventType.setText(body.getType().name);
        _binding.cityInput.setText(body.getLocation().city);
        _binding.etStreetAddress.setText(body.getLocation().street);
        _binding.etStreetNumber.setText(body.getLocation().streetNumber);
        _binding.privacyType.setText(body.isPublic() ? "Public" : "Private");
        _binding.visitorsInput.setText(String.valueOf(body.getMaxParticipants()));
    }

    private void populateChipGroup(EventType eventType) {
        _selectedEventCategories.removeAllViews();

        List<OfferingsCategory> categories = eventType.getSuggestedCategories().categories;
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
                    // Format the selected date as yyyy-MM-dd
                    String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d",
                            selectedYear, selectedMonth + 1, selectedDay);
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
        Call<GetEventTypesWithCategoriesResponse> request = ClientUtils.eventTypeService.findAllWIthSuggestedCategories();
        request.enqueue(new Callback<GetEventTypesWithCategoriesResponse>() {
            @Override
            public void onResponse(Call<GetEventTypesWithCategoriesResponse> call, Response<GetEventTypesWithCategoriesResponse> response) {
                _eventTypes.clear();
                _eventTypes = response.body().eventTypes;
                eventTypeAdapter = new ArrayAdapter<>(
                        requireContext(),
                        R.layout.dropdown_item,
                        _eventTypes
                );
                _eventTypeInput.setAdapter(eventTypeAdapter);
            }

            @Override
            public void onFailure(Call<GetEventTypesWithCategoriesResponse> call, Throwable t) {
            }
        });
    }

    @Override
    public boolean validate() {
        String eventTypeInput = Objects.requireNonNull(_binding.eventType.getEditableText()).toString().trim();
        EventType eventType = _eventViewModel.getEventType().getValue();
        String eventTitle = Objects.requireNonNull(_binding.eventTitleInput.getEditText()).getText().toString().trim();
        String eventDescription = Objects.requireNonNull(_binding.eventDescriptionInput.getEditText()).getText().toString().trim();
        String eventDate = Objects.requireNonNull(_binding.etEventDate.getText()).toString().trim();
        String eventTime = Objects.requireNonNull(_binding.etEventTime.getText()).toString().trim();
        String maxVisitors = Objects.requireNonNull(_binding.maxVisitorsInput.getEditText()).getText().toString().trim();
        String privacyType = Objects.requireNonNull(_binding.privacyType.getText()).toString().trim();
        String city = Objects.requireNonNull(_binding.cityInput.getText()).toString();
        String streetAddress = Objects.requireNonNull(_binding.etStreetAddress.getText()).toString();
        String streetNumber = Objects.requireNonNull(_binding.etStreetNumber.getText()).toString();



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
        if (city.isEmpty() || streetAddress.isEmpty() || streetNumber.isEmpty()) {
            _binding.eventCity.setErrorEnabled(true);
            _binding.eventCity.setError("Event location is required");
            return false;
        } else {
            _binding.eventCity.setError(null);
            _binding.eventCity.setErrorEnabled(false);
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
        if (eventTypeInput.isEmpty()) {
            _binding.eventType.setError("Event title is required");
            return false;
        } else {
            _binding.eventType.setError(null);
        }

        // Optionally, you can add logic to process the values (e.g., send to a ViewModel)
        _eventViewModel.setTitle(eventTitle);
        _eventViewModel.setDescription(eventDescription);
        _eventViewModel.setDate(eventDate);
        _eventViewModel.setTime(eventTime);
        _eventViewModel.setMaxParticipants(Integer.valueOf(maxVisitors));
        _eventViewModel.setIsPublic(privacyType.equals("Public"));
        _eventViewModel.setCity(city);
        _eventViewModel.setStreetAddress(streetAddress);
        _eventViewModel.setStreetNumber(streetNumber);

        return eventType != null;
    }

}