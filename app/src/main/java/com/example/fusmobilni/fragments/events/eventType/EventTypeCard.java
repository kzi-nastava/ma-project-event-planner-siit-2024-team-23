package com.example.fusmobilni.fragments.events.eventType;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;

public class EventTypeCard extends Fragment {

    public EventTypeCard() {
        // Required empty public constructor
    }
    public static EventTypeCard newInstance(String param1, String param2) {
        return new EventTypeCard();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_type_card, container, false);
    }
}