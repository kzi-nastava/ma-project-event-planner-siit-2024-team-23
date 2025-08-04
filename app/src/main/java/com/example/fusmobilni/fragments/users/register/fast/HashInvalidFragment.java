package com.example.fusmobilni.fragments.users.register.fast;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.FragmentHashInvalidBinding;
import com.example.fusmobilni.viewModels.users.register.FastRegisterViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HashInvalidFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HashInvalidFragment extends Fragment {

    private FragmentHashInvalidBinding _binding;
    private FastRegisterViewModel _viewModel;

    public HashInvalidFragment() {

    }

    public static HashInvalidFragment newInstance(String param1, String param2) {
        HashInvalidFragment fragment = new HashInvalidFragment();

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
        _binding = FragmentHashInvalidBinding.inflate(inflater, container, false);
        View view = _binding.getRoot();
        _viewModel = new ViewModelProvider(this).get(FastRegisterViewModel.class);


        return view;
    }
}