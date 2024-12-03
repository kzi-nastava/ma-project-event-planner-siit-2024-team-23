package com.example.fusmobilni.fragments.EventFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;

public class ItemEventActivity extends Fragment {

    public ItemEventActivity() {
        // Required empty public constructor
    }
    public static ItemEventActivity newInstance() {
        return new ItemEventActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_event_activity, container, false);
    }
}