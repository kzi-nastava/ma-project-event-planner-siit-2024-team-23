package com.example.fusmobilni.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.CategoryFilterAdapter;
import com.example.fusmobilni.databinding.FragmentEventFilterBinding;
import com.example.fusmobilni.model.Category;
import com.example.fusmobilni.viewModels.EventSearchViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventFilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventFilterFragment extends BottomSheetDialogFragment {

    private EventSearchViewModel _viewModel;
    private RecyclerView categoryRecyclerView;
    private FragmentEventFilterBinding _binding;
    private List<Category> _categories;
    private CategoryFilterAdapter _adapter;

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

        _viewModel = new ViewModelProvider(requireActivity()).get(EventSearchViewModel.class);

        datePickerButton = _binding.openDatepicker;
        MaterialDatePicker.Builder datePickerBuilder = MaterialDatePicker.Builder.datePicker();

        datePickerBuilder.setTitleText("Select a date!");
        datePicker = datePickerBuilder.build();

        this._categories = Arrays.asList(
                new Category(1, "Sports", R.drawable.ic_category_sports_active, R.drawable.ic_category_sports_inactive),
                new Category(2, "Music", R.drawable.ic_category_music_active, R.drawable.ic_category_music_inactive),
                new Category(3, "Art", R.drawable.ic_category_arts_active, R.drawable.ic_category_arts_inactive),
                new Category(4, "Food", R.drawable.ic_category_food_active, R.drawable.ic_category_food_inactive),
                new Category(5, "Tech", R.drawable.ic_category_sports_active, R.drawable.ic_category_sports_inactive),
                new Category(6, "Travel", R.drawable.ic_category_music_active, R.drawable.ic_category_music_inactive),
                new Category(7, "Education", R.drawable.ic_category_arts_active, R.drawable.ic_category_arts_inactive),
                new Category(8, "Health", R.drawable.ic_category_sports_active, R.drawable.ic_category_sports_inactive),
                new Category(9, "Fashion", R.drawable.ic_category_music_active, R.drawable.ic_category_music_inactive)
        );
        _adapter = new CategoryFilterAdapter(_categories);
        categoryRecyclerView.setAdapter(_adapter);

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
            _adapter.resetCategories();
        });

        _binding.eventsFilterApplyButton.setOnClickListener(v -> {
            initializeApplyButton();
        });

        _binding.eventFilterResetButton.setOnClickListener(v -> {
            _viewModel.resetFilters();
            dismiss();
        });

        initializeDatePicker();

        _locationSpinner = this._binding.spinner;
        initializeLocationSpinner();

        initializeFields();
        return view;
    }

    private void initializeApplyButton() {
        Category fetchedCategory = _adapter.getSelectedCategory();

        _viewModel.setCategory(fetchedCategory);
        _viewModel.setLocation(String.valueOf(_locationSpinner.getSelectedItem()));

        String date = convertDateToString(datePicker.getSelection());
        if (date.equals("")) {
            if (_binding.eventFilterChipToday.isChecked()) {
                date = getTodayDate();
            } else if (_binding.eventFilterChipTomorrow.isChecked()) {
                date = getTomorrowDate();
            }
        }
        _viewModel.setDate(date);

        dismiss();
    }

    private String getTomorrowDate() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return tomorrow.format(formatter);
    }

    private String getTodayDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
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
        if (_viewModel.getCategory().getValue() != null) {
            _adapter.setSelectedCategory(_viewModel.getCategory().getValue().getId());
        }
        if (!_viewModel.getDate().getValue().isEmpty()) {
            datePicker = MaterialDatePicker.Builder.datePicker().setTitleText(_viewModel.getDate().getValue()).setSelection(convertStringToDate(_viewModel.getDate().getValue())).build();
            _binding.textViewSelectedDateDisplay.setText(_viewModel.getDate().getValue());
            initializeDatePicker();
        }
        _locationSpinner.setSelection(extractLocationPosition(_viewModel.getLocation().getValue()));
    }

    private void initializeLocationSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.event_locations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _locationSpinner.setAdapter(adapter);
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
        return (new SimpleDateFormat("dd-MM-yyyy")).format(date);
    }

    private Long convertStringToDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
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
