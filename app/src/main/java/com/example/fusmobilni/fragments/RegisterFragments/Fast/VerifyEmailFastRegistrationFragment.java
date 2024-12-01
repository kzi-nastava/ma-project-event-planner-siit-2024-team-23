package com.example.fusmobilni.fragments.RegisterFragments.Fast;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;

public class VerifyEmailFastRegistrationFragment extends Fragment {

    public VerifyEmailFastRegistrationFragment() {
        // Required empty public constructor
    }


    public static VerifyEmailFastRegistrationFragment newInstance(String param1, String param2) {
        VerifyEmailFastRegistrationFragment fragment = new VerifyEmailFastRegistrationFragment();
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
        return inflater.inflate(R.layout.fragment_verify_email_fast_registration, container, false);
    }
}