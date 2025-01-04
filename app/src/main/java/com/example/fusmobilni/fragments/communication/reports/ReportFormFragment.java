package com.example.fusmobilni.fragments.communication.reports;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentReportFormBinding;

public class ReportFormFragment extends Fragment {


    private FragmentReportFormBinding _binding;

    public ReportFormFragment() {
        // Required empty public constructor
    }

    public static ReportFormFragment newInstance(String param1, String param2) {
        ReportFormFragment fragment = new ReportFormFragment();
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
        _binding = FragmentReportFormBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        return root;
    }
}