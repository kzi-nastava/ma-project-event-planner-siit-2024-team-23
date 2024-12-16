package com.example.fusmobilni.fragments.events.event.filters;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.events.eventType.EventTypeFilterAdapter;
import com.example.fusmobilni.adapters.items.category.CategoryFilterAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentEventFilterBinding;
import com.example.fusmobilni.model.items.category.Category;
import com.example.fusmobilni.responses.events.EventTypeResponse;
import com.example.fusmobilni.responses.events.EventTypesResponse;
import com.example.fusmobilni.responses.events.filter.EventLocationsResponse;
import com.example.fusmobilni.responses.location.LocationResponse;
import com.example.fusmobilni.viewModels.events.filters.EventSearchViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventFilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventFilterFragment extends BottomSheetDialogFragment {

    private EventSearchViewModel _viewModel;
    private RecyclerView categoryRecyclerView;
    private FragmentEventFilterBinding _binding;
    private EventTypeFilterAdapter _adapter;

    private Spinner _locationSpinner;

    private MaterialDatePicker datePicker;
    private MaterialButton datePickerButton;

    public EventFilterFragment() {
        // Required empty public constructor
    }

    public static EventFilterFragment newInstance(String param1, String param2) {
        EventFilterFragment fragment = new EventFilterFragment();
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
        // Inflate the layout for this fragment
        _binding = FragmentEventFilterBinding.inflate(inflater, container, false);
        View view = _binding.getRoot();
        _binding.textViewSelectedDateDisplay.setText("");
        categoryRecyclerView = _binding.categoryRecyclerView;

        _viewModel = new ViewModelProvider(requireParentFragment()).get(EventSearchViewModel.class);

        _adapter = new EventTypeFilterAdapter(_viewModel.getEventTypes().getValue());
        datePickerButton = _binding.openDatepicker;
        MaterialDatePicker.Builder datePickerBuilder = MaterialDatePicker.Builder.datePicker();

        datePickerBuilder.setTitleText("Select a date!");
        datePicker = datePickerBuilder.build();

        _locationSpinner = this._binding.spinner;
        categoryRecyclerView.setAdapter(_adapter);

        initializeLocationSpinner(_viewModel.getLocations().getValue());
        _binding.eventFilterChipToday.setOnClickListener(v -> {
            updateChipStyles(_binding.eventFilterChipToday, _binding.eventFilterChipTomorrow);

        });
        _binding.eventFilterChipTomorrow.setOnClickListener(v -> {
            updateChipStyles(_binding.eventFilterChipTomorrow, _binding.eventFilterChipToday);
        });

        _binding.eventFilterResetButton.setOnClickListener(v -> {
            invalidateAllChips(_binding.eventFilterChipToday, _binding.eventFilterChipTomorrow);
            _binding.textViewSelectedDateDisplay.setText("");
            resetDatePicker();
            _adapter.resetTypes();
        });

        _binding.eventsFilterApplyButton.setOnClickListener(v -> {
            initializeApplyButton();
        });

        _binding.eventFilterResetButton.setOnClickListener(v -> {

            _viewModel.resetPage();
            _viewModel.resetFilters();
            dismiss();
        });

        initializeDatePicker();


        initializeFields();
        return view;
    }

    private void initializeApplyButton() {
        _viewModel.resetPage();
        EventTypeResponse eventType = _adapter.getSelectedEventTypeByIndex();

        _viewModel.setEventType(eventType);

        String date = convertDateToString(datePicker.getSelection());
        if (date.equals("")) {
            if (_binding.eventFilterChipToday.isChecked()) {
                date = getTodayDate();
            } else if (_binding.eventFilterChipTomorrow.isChecked()) {
                date = getTomorrowDate();
            }
        }
        _viewModel.setDate(date);

        _viewModel.doFilter();

        dismiss();
    }

    private String getTomorrowDate() {
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(2);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return tomorrow.format(formatter);
    }

    private String getTodayDate() {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return today.format(formatter);
    }

    private void initializeDatePicker() {
        datePickerButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        datePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");
                    }
                });

        datePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {

                        invalidateAllChips(_binding.eventFilterChipToday, _binding.eventFilterChipTomorrow);

                        _binding.textViewSelectedDateDisplay.setText(convertDateToString(datePicker.getSelection()));

                    }
                });

    }



    private void initializeFields() {
        if (_viewModel.getEventType().getValue() != null) {
            _adapter.setSelectedEventType(_viewModel.getEventType().getValue());
        }
        if (!_viewModel.getDate().getValue().isEmpty()) {
            datePicker = MaterialDatePicker.Builder.datePicker().setTitleText(_viewModel.getDate().getValue()).setSelection(convertStringToDate(_viewModel.getDate().getValue())).build();
            _binding.textViewSelectedDateDisplay.setText(_viewModel.getDate().getValue());
            initializeDatePicker();
        }
        if (_viewModel.getLocation().getValue() != null)
            _locationSpinner.setSelection(findLocation(_viewModel.getLocation().getValue().getCity()));
        else
            _locationSpinner.setSelection(_viewModel.getLocations().getValue().size());
    }

    private int findLocation(String city) {
        for (int i = 0; i < _viewModel.getLocations().getValue().size(); ++i) {
            if (city.equals(_viewModel.getLocations().getValue().get(i).getCity())) {
                return i;
            }
        }
        return -1;
    }

    private void initializeLocationSpinner(List<LocationResponse> locations) {

        List<String> cityNames = new ArrayList<>();
        for (LocationResponse l : locations) {
            cityNames.add(l.getCity());
        }
        cityNames.add("");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, cityNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _locationSpinner.setAdapter(adapter);
        _locationSpinner.setSelection(adapter.getPosition(""));
        _locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCity = parent.getItemAtPosition(position).toString();
                LocationResponse location = null;
                for (LocationResponse l : _viewModel.getLocations().getValue()) {
                    if (l.getCity().equals(selectedCity)) {
                        location = l;
                    }
                }
                _viewModel.setLocation(location);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private int extractLocationPosition(String location) {
        String[] locations = extractLocations();
        for (int i = 0; i < locations.length; ++i) {
            if (locations[i].equals(location)) {
                return i;
            }
        }
        return -1;
    }

    private String[] extractLocations() {
        return getResources().getStringArray(R.array.event_locations);
    }

    private void invalidateAllChips(Chip... chips) {
        for (Chip chip : chips) {

            chip.setChipBackgroundColorResource(R.color.white);
            chip.setTextColor(getResources().getColor(R.color.bg_gray));
            chip.setCheckable(true); // Ensures the chip is checkable
            chip.setChecked(false);
            chip.invalidate();
        }
    }

    private void updateChipStyles(Chip selectedChip, Chip... otherChips) {
        selectedChip.invalidate();
        selectedChip.setChipBackgroundColorResource(R.color.primary_blue_900);
        selectedChip.setTextColor(getResources().getColor(R.color.white));
        selectedChip.setCheckable(true); // Ensures the chip is checkable
        selectedChip.setChecked(true);
        // Deselect and reset style for other chips
        for (Chip chip : otherChips) {

            chip.setChipBackgroundColorResource(R.color.white);
            chip.setTextColor(getResources().getColor(R.color.bg_gray));
            chip.setCheckable(true); // Ensures the chip is checkable
            chip.setChecked(false);
        }

        // Force a redraw on each chip to apply changes

        for (Chip chip : otherChips) {
            chip.invalidate();
        }
        _binding.textViewSelectedDateDisplay.setText("");
        resetDatePicker();
    }

    private void resetDatePicker() {
        MaterialDatePicker.Builder<Long> datePickerBuilder = MaterialDatePicker.Builder.datePicker();
        datePickerBuilder.setTitleText("Select a date!");
        datePicker = datePickerBuilder.build();
        datePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        invalidateAllChips(_binding.eventFilterChipToday, _binding.eventFilterChipTomorrow);
                        _binding.textViewSelectedDateDisplay.setText(convertDateToString(convertStringToDate(datePicker.getHeaderText())));
                        resetDatePicker(); // Reset picker after selection
                    }
                });
    }

    private String convertDateToString(Object fetchedDate) {


        if (fetchedDate == null) {
            return "";
        }

        Long dateInMiliseconds = (Long) fetchedDate;
        Date date = new Date(dateInMiliseconds);
        return (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).format(date);
    }

    private Long convertStringToDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));  // Set to UTC to avoid local time zone discrepancies
        try {
            Date date = dateFormat.parse(dateString);
            return date != null ? date.getTime() : null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
