package com.example.fusmobilni.fragments.events.event.createEventSteps;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fusmobilni.adapters.events.event.forms.EmailInvitationAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.databinding.FragmentInvitationsBinding;
import com.example.fusmobilni.fragments.dialogs.FailiureDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.fragments.dialogs.SuccessDialogFragment;
import com.example.fusmobilni.interfaces.FragmentValidation;
import com.example.fusmobilni.requests.events.invitations.InvitationCreateRequest;
import com.example.fusmobilni.requests.events.invitations.InvitationsCreateRequest;
import com.example.fusmobilni.responses.events.invitations.InvitationsResponse;
import com.example.fusmobilni.viewModels.events.event.InvitationsViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InvitationsFragment extends Fragment implements FragmentValidation {

    private FragmentInvitationsBinding _binding;
    private EmailInvitationAdapter _invitationAdapter;
    private TextInputLayout _emailInputField;
    private RecyclerView _recyclerView;
    private InvitationsViewModel _viewModel;

    private SpinnerDialogFragment _loader;
    private SuccessDialogFragment _success;
    private FailiureDialogFragment _failiure;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");


    public static InvitationsFragment newInstance() {
        InvitationsFragment fragment = new InvitationsFragment();
        Bundle args = new Bundle();


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentInvitationsBinding.inflate(inflater, container, false);
        View view = _binding.getRoot();

        initializeDialogs();

        _emailInputField = _binding.textField;
        _invitationAdapter = new EmailInvitationAdapter();
        _viewModel = new ViewModelProvider(requireActivity()).get(InvitationsViewModel.class);
        _invitationAdapter = new EmailInvitationAdapter(_viewModel.getEmails().getValue());
        _recyclerView = _binding.chipRecyclerView;
        _recyclerView.setAdapter(_invitationAdapter);
        _binding.emailAddButton.setOnClickListener(v -> {
            String inputEmail = _emailInputField.getEditText().getText().toString();
            if (validateEmail(inputEmail)) {
                _binding.textView6.setVisibility(View.INVISIBLE);
                _viewModel.addEmail(inputEmail);
            } else {
                _binding.textView6.setVisibility(View.VISIBLE);
            }
        });
        _viewModel.getEmails().observe(getViewLifecycleOwner(), observer -> {
            _invitationAdapter.setData(_viewModel.getEmails().getValue());
        });
        _binding.sendInvitationsButton.setOnClickListener(v -> {
            if (_viewModel.getEmails().getValue().size() == 0) {
                return;
            }
            createInvitations();
        });

        return view;
    }
    void openSuccessWindow() {
        if (_loader != null) {
            _loader.dismiss();
        }
        Bundle args = new Bundle();
        args.putString("Title", "Success");
        args.putString("Message", "Invitations set successfully");
        _success.setArguments(args);
        _success.show(getParentFragmentManager(), "success_dialog");
    }

    void openFailiureWindow() {
        if (_loader != null) {
            _loader.dismiss();
        }
        Bundle args = new Bundle();
        args.putString("Title", "Failiure");
        args.putString("Message", "Failed to send invitations");
        _failiure.setArguments(args);
        _failiure.show(getParentFragmentManager(), "failiure_dialog");
    }
    private void createInvitations() {
        List<String> emails = _viewModel.getEmails().getValue();
        List<InvitationCreateRequest> requests = new ArrayList<>();
        for (String email : emails) {
            requests.add(new InvitationCreateRequest(email, 1L, "PENDING"));
        }
        InvitationsCreateRequest request = new InvitationsCreateRequest(requests);

        _loader.show(getParentFragmentManager(), "loading_spinner");
        sendInvitations(request);

    }
    private void sendInvitations(InvitationsCreateRequest request){
        Call<InvitationsResponse> call = ClientUtils.invitationsService.create(1L, request);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<InvitationsResponse> call, Response<InvitationsResponse> response) {
                if (response.isSuccessful()) {
                    openSuccessWindow();
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        _success.dismiss();
                    },1500);
                }else{
                    openFailiureWindow();
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        _failiure.dismiss();
                    },1500);
                }
            }

            @Override
            public void onFailure(Call<InvitationsResponse> call, Throwable t) {
                openFailiureWindow();
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    _failiure.dismiss();
                },1500);
            }
        });
    }
    private void initializeDialogs() {
        _loader = new SpinnerDialogFragment();
        _loader.setCancelable(false);
        _success = new SuccessDialogFragment();
        _success.setCancelable(false);
        _failiure = new FailiureDialogFragment();
    }
    private boolean validateEmail(String email) {
        return !email.isEmpty() && EMAIL_PATTERN.matcher(email).matches();
    }

    @Override
    public boolean validate() {
        return true;
    }
}