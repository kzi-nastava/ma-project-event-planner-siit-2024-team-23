package com.example.fusmobilni.fragments.dialogs;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.databinding.FragmentSucessDialogBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SuccessDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuccessDialogFragment extends DialogFragment {

    private String _title;
    private String _message;
    private FragmentSucessDialogBinding _binding;

    public SuccessDialogFragment() {
        // Required empty public constructor
    }


    public static SuccessDialogFragment newInstance(String param1, String param2) {
        SuccessDialogFragment fragment = new SuccessDialogFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _title = getArguments().getString("Title");
            _message = getArguments().getString("Message");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentSucessDialogBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        _binding.successMessage.setText(_title);
        _binding.subMessage.setText(_message);
        return root;
    }
}