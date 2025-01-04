package com.example.fusmobilni.fragments.communication.blocks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlockFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlockFormFragment extends Fragment {

    public BlockFormFragment() {
        // Required empty public constructor
    }

    public static BlockFormFragment newInstance(String param1, String param2) {
        BlockFormFragment fragment = new BlockFormFragment();
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
        return inflater.inflate(R.layout.fragment_block_form, container, false);
    }
}