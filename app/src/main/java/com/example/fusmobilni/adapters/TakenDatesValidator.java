package com.example.fusmobilni.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.datepicker.CalendarConstraints;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TakenDatesValidator implements CalendarConstraints.DateValidator {
    private final Set<Long> takenDates;

    public TakenDatesValidator(Set<Long> takenDates) {
        this.takenDates = new HashSet<>(takenDates); // Ensure immutability
    }

    @Override
    public boolean isValid(long date) {
        return !takenDates.contains(date); // Disable taken dates
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull android.os.Parcel dest, int flags) {
        dest.writeList(new ArrayList<>(takenDates)); // Convert to a List
    }

    public static final Creator<TakenDatesValidator> CREATOR = new Creator<TakenDatesValidator>() {
        @Nullable
        @Override
        public TakenDatesValidator createFromParcel(@NonNull android.os.Parcel source) {
            List<Long> takenDatesList = new ArrayList<>();
            source.readList(takenDatesList, Long.class.getClassLoader());
            return new TakenDatesValidator(new HashSet<>(takenDatesList)); // Convert back to Set
        }

        @NonNull
        @Override
        public TakenDatesValidator[] newArray(int size) {
            return new TakenDatesValidator[size];
        }
    };
}
