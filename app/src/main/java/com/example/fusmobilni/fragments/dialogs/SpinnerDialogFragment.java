package com.example.fusmobilni.fragments.dialogs;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentSpinnerDialogBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpinnerDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpinnerDialogFragment extends DialogFragment {

    private FragmentSpinnerDialogBinding _binding;

    public SpinnerDialogFragment() {

    }


    public static SpinnerDialogFragment newInstance(String param1, String param2) {
        SpinnerDialogFragment fragment = new SpinnerDialogFragment();
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
        _binding = FragmentSpinnerDialogBinding.inflate(inflater,container,false);
        View root = _binding.getRoot();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }
}