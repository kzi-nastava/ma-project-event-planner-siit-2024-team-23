package com.example.fusmobilni.fragments.dialogs;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentFailiureDialogBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FailiureDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FailiureDialogFragment extends DialogFragment {
    private String _title;
    private String _message;
    private FragmentFailiureDialogBinding _binding;

    public FailiureDialogFragment() {
        // Required empty public constructor
    }


    public static FailiureDialogFragment newInstance(String param1, String param2) {
        FailiureDialogFragment fragment = new FailiureDialogFragment();


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
        _binding = FragmentFailiureDialogBinding.inflate(inflater,container,false);
        View root = _binding.getRoot();
        _binding.successMessage.setText(_title);
        _binding.subMessage.setText(_message);
        return root;
    }
}