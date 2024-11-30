package com.example.fusmobilni.core.calendar;

import static com.kizitonwose.calendar.core.ExtensionsKt.daysOfWeek;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.kizitonwose.calendar.core.CalendarMonth;
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

public class MonthHeaderBinder implements MonthHeaderFooterBinder<MonthViewContainer> {
    @NonNull
    @Override
    public MonthViewContainer create(@NonNull View view) {
        return new MonthViewContainer(view);
    }

    @Override
    public void bind(@NonNull MonthViewContainer container, @NonNull CalendarMonth data) {
        // Set Month and Year Title
        String monthYear = data.getYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())
                + " " + data.getYearMonth().getYear();
        container.monthYearTitle.setText(monthYear);
        if (container.titlesContainer.getTag() == null) {
            container.titlesContainer.setTag(data.getYearMonth());

            // Iterate through the children and bind day titles
            for (int i = 0; i < container.titlesContainer.getChildCount(); i++) {
                View child = container.titlesContainer.getChildAt(i);
                if (child instanceof TextView) {
                    TextView textView = (TextView) child;

                    // Get the day of the week and set its short name
                    DayOfWeek dayOfWeek = daysOfWeek().get(i);
                    String title = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault());
                    textView.setText(title);
                }
            }
        }
    }

}