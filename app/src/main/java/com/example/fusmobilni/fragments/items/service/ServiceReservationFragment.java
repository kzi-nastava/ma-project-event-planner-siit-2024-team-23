package com.example.fusmobilni.fragments.items.service;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.events.event.EventSelectionSpinnerAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentServiceReservationBinding;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.model.event.Event;
import com.example.fusmobilni.model.items.service.Service;
import com.example.fusmobilni.responses.items.services.ServiceOverviewResponse;
import com.example.fusmobilni.responses.items.services.ServiceReservationResponse;
import com.google.android.material.button.MaterialButton;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceReservationFragment extends Fragment {

    private FragmentServiceReservationBinding _binding;
    private boolean toInputsDisabled = new Random(System.currentTimeMillis()).nextBoolean();
    private ServiceReservationResponse _service;
    private Long _serviceId;
    private SpinnerDialogFragment _loader;
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
            _serviceId = getArguments().getLong("serviceId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentServiceReservationBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();


        initializeDialogs();

        getServiceForReservation();

        return root;

    }

    private void initializePageSuccessful() {

        _binding.textViewServiceDetailsTitleReservation.setText(_service.getName());


        initializeHoursAndMinutes();

        initializeTimePicker(_hoursInputFrom, _minutesInputFrom, _binding.buttonPickTimeFrom);
        initializeTimePicker(_hoursInputTo, _minutesInputTo, _binding.buttonPickTimeTo);

        _binding.buttonApplyReservation.setOnClickListener(v -> {
            makeServiceReservation();
        });

        disabledToInputs();
    }

    private String convertTime(EditText hours, EditText minutes) {
        String hoursS = "";
        String minutesS = "";
        hoursS = hours.getText().toString();
        if (Integer.parseInt(hoursS) < 10) {
            hoursS = "0" + hoursS;
        }
        minutesS = minutes.getText().toString();
        if (Integer.parseInt(minutesS) < 10) {
            minutesS = "0" + minutesS;
        }
        return hoursS + ":" + minutesS;
    }

    private void makeServiceReservation() {
        Call<Boolean> call = ClientUtils.serviceReservationService.checkIfReservationTimeTaken(
                1L,
                _serviceId,
                convertTime(_hoursInputFrom, _minutesInputFrom),
                convertTime(_hoursInputTo, _minutesInputTo)
        );
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    boolean bool = response.body();
                    Toast.makeText(getContext(), String.valueOf(bool), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void getServiceForReservation() {
        _loader.show(getFragmentManager(), "loading_spinner");
        Call<ServiceReservationResponse> call = ClientUtils.serviceOfferingService.findServiceForReservation(_serviceId);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ServiceReservationResponse> call, Response<ServiceReservationResponse> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                _service = response.body();
                _loader.dismiss();
                initializePageSuccessful();

            }

            @Override
            public void onFailure(Call<ServiceReservationResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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


    private void disabledToInputs() {
        if (!toInputsDisabled) {
            return;
        }
        _binding.cardViewToInputs.setStrokeColor(getResources().getColor(R.color.bg_gray));
        _binding.buttonPickTimeTo.setEnabled(false);
        _binding.buttonPickTimeTo.setBackgroundTintList(getResources().getColorStateList(R.color.bg_gray));
        _binding.buttonPickTimeTo.setTextColor(getResources().getColor(R.color.white));
    }


    private void initializeDialogs() {
        _loader = new SpinnerDialogFragment();
        _loader.setCancelable(false);

    }

}