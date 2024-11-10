package com.example.fusmobilni.fragments.RegisterFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.activities.LoginActivity;
import com.example.fusmobilni.databinding.FragmentStepOneBinding;
import com.example.fusmobilni.databinding.FragmentVerifyEmailBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VerifyEmailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerifyEmailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentVerifyEmailBinding _binding;

    public VerifyEmailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerifyEmailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VerifyEmailFragment newInstance(String param1, String param2) {
        VerifyEmailFragment fragment = new VerifyEmailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _binding = FragmentVerifyEmailBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();

        _binding.loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                // prevent going back once the register process is finished
                requireActivity().finish();

            }
        });

        return view;
    }
}