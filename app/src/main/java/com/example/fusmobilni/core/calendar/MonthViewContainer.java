package com.example.fusmobilni.core.calendar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.fusmobilni.R;
import com.kizitonwose.calendar.view.ViewContainer;

public class MonthViewContainer extends ViewContainer {
    public final ViewGroup titlesContainer;
    public final TextView monthYearTitle;

    public MonthViewContainer(@NonNull View view) {
        super(view);
        // The root view is assumed to be a ViewGroup
        monthYearTitle = view.findViewById(R.id.monthYearTitle);
        titlesContainer = view.findViewById(R.id.daysOfWeekContainer);
    }
}