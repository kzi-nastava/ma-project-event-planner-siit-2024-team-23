package com.example.fusmobilni.fragments.RegisterFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.activities.RegisterActivity;
import com.example.fusmobilni.databinding.FragmentRoleSelectionBinding;
import com.example.fusmobilni.model.enums.RegisterUserRole;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoleSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoleSelectionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button _eventOrganizer;
    private Button _serviceProvider;
    private FragmentRoleSelectionBinding _binding;

    public RoleSelectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RoleSelectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoleSelectionFragment newInstance(String param1, String param2) {
        RoleSelectionFragment fragment = new RoleSelectionFragment();
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

        _binding = FragmentRoleSelectionBinding.inflate(getLayoutInflater());
        View view = _binding.getRoot();


        _eventOrganizer = _binding.eventOrganizer;
        _serviceProvider = _binding.serviceProvider;

        _eventOrganizer.setOnClickListener(v->{
//            Toast.makeText(getActivity(), "Event Organizer", Toast.LENGTH_LONG).show();
            ((RegisterActivity) requireActivity()).onIntroFinished(RegisterUserRole.EVENT_ORGANIZER);
        });

        _serviceProvider.setOnClickListener(v->{
//            Toast.makeText(getActivity(), "Service provider", Toast.LENGTH_LONG).show();
            ((RegisterActivity) requireActivity()).onIntroFinished(RegisterUserRole.SERVICE_PROVIDER);
        });

        // Inflate the layout for this fragment
        return view;





    }
}