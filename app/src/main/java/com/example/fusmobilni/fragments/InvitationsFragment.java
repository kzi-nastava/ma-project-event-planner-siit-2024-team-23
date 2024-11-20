package com.example.fusmobilni.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.EmailInvitationAdapter;
import com.example.fusmobilni.databinding.FragmentInvitationsBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;


public class InvitationsFragment extends Fragment {

    private FragmentInvitationsBinding _binding;
    private EmailInvitationAdapter _invitationAdapter;
    private TextInputLayout _emailInputField;
    private RecyclerView _recyclerView;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");


    public static InvitationsFragment newInstance(String param1, String param2) {
        InvitationsFragment fragment = new InvitationsFragment();
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
        _binding = FragmentInvitationsBinding.inflate(inflater, container, false);
        View view = _binding.getRoot();

        _emailInputField = _binding.textField;
        _invitationAdapter = new EmailInvitationAdapter();
        _recyclerView = _binding.chipRecyclerView;
        _recyclerView.setAdapter(_invitationAdapter);
        _binding.emailAddButton.setOnClickListener(v->{
            String inputEmail = _emailInputField.getEditText().getText().toString();
            if(validateEmail(inputEmail)){
                _binding.textView6.setVisibility(View.INVISIBLE);
                _invitationAdapter.addEmail(inputEmail);
            }
            else{
                _binding.textView6.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
    private boolean validateEmail(String email){
        return !email.isEmpty() && EMAIL_PATTERN.matcher(email).matches();
    }
}