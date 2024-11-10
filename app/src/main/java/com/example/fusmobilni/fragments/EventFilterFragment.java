package com.example.fusmobilni.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.CategoryFilterAdapter;
import com.example.fusmobilni.databinding.FragmentEventFilterBinding;
import com.example.fusmobilni.model.Category;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventFilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventFilterFragment extends BottomSheetDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView categoryRecyclerView;
    private FragmentEventFilterBinding _binding;
    private List<Category> _categories;
    private CategoryFilterAdapter _adapter;

    private Spinner _locationSpinner;

    private MaterialDatePicker datePicker;

    public EventFilterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventFilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventFilterFragment newInstance(String param1, String param2) {
        EventFilterFragment fragment = new EventFilterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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

        MaterialButton datePickerButton = _binding.openDatepicker;
        MaterialDatePicker.Builder datePickerBuilder = MaterialDatePicker.Builder.datePicker();

        datePickerBuilder.setTitleText("Select a date!");
        datePicker = datePickerBuilder.build();

        this._categories = Arrays.asList(
                new Category("Sports", R.drawable.ic_category_sports_active, R.drawable.ic_category_sports_inactive),
                new Category("Music", R.drawable.ic_category_music_active, R.drawable.ic_category_music_inactive),
                new Category("Art", R.drawable.ic_category_arts_active, R.drawable.ic_category_arts_inactive),
                new Category("Food", R.drawable.ic_category_food_active, R.drawable.ic_category_food_inactive),
                new Category("Tech", R.drawable.ic_category_sports_active, R.drawable.ic_category_sports_inactive),
                new Category("Travel", R.drawable.ic_category_music_active, R.drawable.ic_category_music_inactive),
                new Category("Education", R.drawable.ic_category_arts_active, R.drawable.ic_category_arts_inactive),
                new Category("Health", R.drawable.ic_category_sports_active, R.drawable.ic_category_sports_inactive),
                new Category("Fashion", R.drawable.ic_category_music_active, R.drawable.ic_category_music_inactive)

        );
        _adapter = new CategoryFilterAdapter(_categories);
        categoryRecyclerView.setAdapter(_adapter);

        _binding.eventFilterChipToday.setOnClickListener(v -> {
            updateChipStyles(_binding.eventFilterChipToday, _binding.eventFilterChipThisWeek, _binding.eventFilterChipTomorrow);

        });
        _binding.eventFilterChipTomorrow.setOnClickListener(v -> {
            updateChipStyles(_binding.eventFilterChipTomorrow, _binding.eventFilterChipToday, _binding.eventFilterChipThisWeek);
        });
        _binding.eventFilterChipThisWeek.setOnClickListener(v -> {
            updateChipStyles(_binding.eventFilterChipThisWeek, _binding.eventFilterChipToday, _binding.eventFilterChipTomorrow);
        });

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

                        invalidateAllChips(_binding.eventFilterChipToday, _binding.eventFilterChipThisWeek, _binding.eventFilterChipTomorrow);
                        _binding.textViewSelectedDateDisplay.setText(datePicker.getHeaderText());

                    }
                });
        _locationSpinner = this._binding.spinner;
        initializeAdapter();
        return view;
    }

    private void initializeAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _locationSpinner.setAdapter(adapter);
    }

    private void invalidateAllChips(Chip... chips) {
        for (Chip chip : chips) {
            chip.setChecked(false);
            chip.setChipBackgroundColorResource(R.color.white);
            chip.setTextColor(getResources().getColor(R.color.bg_gray));
            chip.setCheckable(true); // Ensures the chip is checkable
            chip.invalidate();
        }
    }

    private void updateChipStyles(Chip selectedChip, Chip... otherChips) {
        selectedChip.setChecked(true);
        selectedChip.setChipBackgroundColorResource(R.color.primary_blue_900);
        selectedChip.setTextColor(getResources().getColor(R.color.white));
        selectedChip.setCheckable(true); // Ensures the chip is checkable

        // Deselect and reset style for other chips
        for (Chip chip : otherChips) {
            chip.setChecked(false);
            chip.setChipBackgroundColorResource(R.color.white);
            chip.setTextColor(getResources().getColor(R.color.bg_gray));
            chip.setCheckable(true); // Ensures the chip is checkable
        }

        // Force a redraw on each chip to apply changes
        selectedChip.invalidate();
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
                        invalidateAllChips(_binding.eventFilterChipToday, _binding.eventFilterChipThisWeek, _binding.eventFilterChipTomorrow);
                        _binding.textViewSelectedDateDisplay.setText(datePicker.getHeaderText());
                        resetDatePicker(); // Reset picker after selection
                    }
                });
    }
}
