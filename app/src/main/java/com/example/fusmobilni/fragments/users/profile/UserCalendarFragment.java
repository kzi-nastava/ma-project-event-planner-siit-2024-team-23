package com.example.fusmobilni.fragments.users.profile;

import static com.kizitonwose.calendar.core.ExtensionsKt.firstDayOfWeekFromLocale;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.users.ViewProfileEventAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.core.calendar.DayBinder;
import com.example.fusmobilni.core.calendar.MonthHeaderBinder;
import com.example.fusmobilni.databinding.FragmentUserCalendarBinding;
import com.example.fusmobilni.interfaces.EventClickListener;
import com.example.fusmobilni.interfaces.ICalendarCallback;
import com.example.fusmobilni.model.event.Event;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.events.home.EventHomeResponse;
import com.example.fusmobilni.responses.events.home.EventsHomeResponse;
import com.kizitonwose.calendar.view.CalendarView;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserCalendarFragment extends Fragment implements EventClickListener {
    private FragmentUserCalendarBinding _binding;
    private CalendarView _calendar;
    private ProgressBar loadingSpinner;
    private List<EventHomeResponse> _events = new ArrayList<>();

    public UserCalendarFragment() {
        // Required empty public constructor
    }
    public static UserCalendarFragment newInstance() {
        return new UserCalendarFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentUserCalendarBinding.inflate(getLayoutInflater());
        loadingSpinner = _binding.loadingSpinner;

        loadingSpinner.setVisibility(View.VISIBLE);
        fillEvents();


        return _binding.getRoot();
    }
    private void fillEvents() {
        CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();
        LoginResponse user = prefs.getUser();
        Call<EventsHomeResponse> request = ClientUtils.userService.findEventsForUser(user.getId());
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<EventsHomeResponse> call, @NonNull Response<EventsHomeResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    _events.addAll(response.body().getEvents());
                    loadingSpinner.setVisibility(View.GONE);



                    _calendar = _binding.calendarView;
                    _calendar.setup(
                            YearMonth.now(),
                            YearMonth.now().plusMonths(12),
                            firstDayOfWeekFromLocale()
                    );
                    _calendar.scrollToMonth(YearMonth.now());
                    _calendar.setMonthHeaderBinder(new MonthHeaderBinder());
                    _calendar.setDayBinder(new DayBinder(_events, getContext(), new ICalendarCallback() {
                        @Override
                        public void onCalendarDateClick(EventsHomeResponse events) {
                            RecyclerView eventList = _binding.eventsList;
                            _events = events.events;
                            ViewProfileEventAdapter eventsHorizontalAdapter = new ViewProfileEventAdapter(_events, UserCalendarFragment.this);
                            eventList.setAdapter(eventsHorizontalAdapter);
                        }
                    }));
                }
            }
            @Override
            public void onFailure(@NonNull Call<EventsHomeResponse> call, @NonNull Throwable t) {
            }
        });
    }

    @Override
    public void onEventClick(int position) {
        EventHomeResponse event = _events.get(position);
        Toast.makeText(getContext(), event.getTitle(), Toast.LENGTH_SHORT).show();
    }
}



