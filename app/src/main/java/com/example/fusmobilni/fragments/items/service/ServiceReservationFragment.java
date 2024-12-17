package com.example.fusmobilni.fragments.items.service;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentServiceReservationBinding;
import com.example.fusmobilni.fragments.dialogs.FailiureDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SuccessDialogFragment;
import com.example.fusmobilni.requests.events.event.EventComponentReservationRequest;
import com.example.fusmobilni.responses.items.services.ServiceReservationResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceReservationFragment extends Fragment {

    private FragmentServiceReservationBinding _binding;
    private ServiceReservationResponse _service;
    private Long _serviceId;
    private Long _eventId;
    private double _estimatedBudget;
    private EditText _hoursInputFrom;
    private EditText _minutesInputFrom;
    private TextInputEditText _hoursInputTo;
    private TextInputEditText _minutesInputTo;

    private SpinnerDialogFragment _loader;
    private SuccessDialogFragment _success;
    private FailiureDialogFragment _failiure;

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
            _eventId = getArguments().getLong("eventId");
            _estimatedBudget = getArguments().getDouble("estimatedBudget");
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

        _binding.hoursInputFrom.setText("7");
        _binding.minutesInputFrom.setText("0");
        _binding.hoursInputTo.setText("7");
        _binding.minutesInputTo.setText("0");

        initializeHoursAndMinutes();

        initializeTimePicker(_hoursInputFrom, _minutesInputFrom, _binding.buttonPickTimeFrom);
        initializeTimePicker(_hoursInputTo, _minutesInputTo, _binding.buttonPickTimeTo);

        _binding.buttonApplyReservation.setOnClickListener(v -> {
            if(checkTimeValid()){
                checkTimeForReservation();
            }
            else{
                openFailiureWindow("Time from must be before time to");
            }
        });
    }

    private boolean checkTimeValid() {
        String timeFromString = convertTime(_hoursInputFrom, _minutesInputFrom);
        String timeToString = convertTime(_hoursInputTo, _minutesInputTo);
        LocalTime timeFrom = LocalTime.parse(timeFromString);
        LocalTime timeTo = LocalTime.parse(timeToString);
        return timeFrom.isBefore(timeTo);
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

    private void sendBackToStepTwo() {
        openSuccessWindow("Successfully made a reservation");

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Bundle bundle = new Bundle();
            bundle.putInt("currFragment", 1);
            bundle.putLong("eventId", _eventId);
            _success.dismiss();
            Navigation.findNavController(_binding.getRoot()).navigate(R.id.action_serviceReservation_to_stepTeoFragment, bundle);
        }, 1500);
    }

    private void makeReservation() {
        Call<Void> reservationRequest = ClientUtils.eventsService.createComponentWithReservation(_eventId, createReservation());
        reservationRequest.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    sendBackToStepTwo();
                } else {
                    openFailiureWindow("Failed to make a reservation");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                openFailiureWindow(t.getMessage());
            }
        });
    }

    private void checkTimeForReservation() {

        _loader.show(getFragmentManager(), "loading_spinner");
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
                    if (bool) {
                        makeReservation();
                    } else {
                        openFailiureWindow("Time you have specified is taken");
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                openFailiureWindow(t.getMessage());
            }
        });
    }

    public EventComponentReservationRequest createReservation() {
        return new EventComponentReservationRequest(_service.category.id, _estimatedBudget,
                _serviceId, _service.getPrice(), _service.name, _service.description,
                convertTime(_hoursInputFrom, _minutesInputFrom),
                convertTime(_hoursInputTo, _minutesInputTo));

    }

    void openSuccessWindow(String message) {
        if (_loader != null) {
            _loader.dismiss();
        }
        Bundle args = new Bundle();
        args.putString("Title", "Success");
        args.putString("Message", message);
        _success.setArguments(args);
        _success.show(getParentFragmentManager(), "success_dialog");
    }

    void openFailiureWindow(String message) {
        if (_loader != null) {
            _loader.dismiss();
        }
        Bundle args = new Bundle();
        args.putString("Title", "Failiure");
        args.putString("Message", message);
        _failiure.setArguments(args);
        _failiure.show(getParentFragmentManager(), "failiure_dialog");
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

    private void initializeDialogs() {
        _loader = new SpinnerDialogFragment();
        _loader.setCancelable(false);
        _success = new SuccessDialogFragment();
        _failiure = new FailiureDialogFragment();
    }

}