package com.example.fusmobilni.fragments.EventFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;

public class EOEventCardHorizontal extends Fragment {

    public EOEventCardHorizontal() {
        // Required empty public constructor
    }
    public static EOEventCardHorizontal newInstance() {
        return new EOEventCardHorizontal();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_e_o_event_card_horizontal, container, false);
    }
}