package com.example.fusmobilni.adapters.events.event;

import static com.example.fusmobilni.adapters.AdapterUtils.convertToUrisFromBase64;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.requests.users.favorites.FavoriteEventRequest;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.events.home.EventHomeResponse;
import com.example.fusmobilni.responses.events.home.EventPreviewResponse;
import com.example.fusmobilni.responses.events.home.EventsHomeResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {
    private static HashMap MonthMap = new HashMap<String, String>();

    static {
        MonthMap.put("01", "Jan");
        MonthMap.put("02", "Feb");
        MonthMap.put("03", "Mar");
        MonthMap.put("04", "Apr");
        MonthMap.put("05", "May");
        MonthMap.put("06", "Jun");
        MonthMap.put("07", "Jul");
        MonthMap.put("08", "Aug");
        MonthMap.put("09", "Sept");
        MonthMap.put("10", "Oct");
        MonthMap.put("11", "Nov");
        MonthMap.put("12", "Dec");
    }

    private List<EventHomeResponse> _eventList;

    public void setData(List<EventHomeResponse> events) {
        _eventList = events;
        notifyDataSetChanged();
    }

    public void setData(EventsHomeResponse response) {
        _eventList = response.getEvents();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {

        EventHomeResponse event = _eventList.get(position);
        String[] dateParts = event.getDate().split("-");

        holder.title.setText(event.getTitle());
        holder.day.setText(dateParts[2]);
        holder.monthYear.setText(MonthMap.get(dateParts[1]) + " " + dateParts[0]);
        holder.location.setText(event.getLocation().city + ", " + event.getLocation().street + " " + event.getLocation().streetNumber);
        holder.description.setText(event.getDescription());
        try {
            holder._image.setImageURI(convertToUrisFromBase64(holder._image.getContext(),event.getImage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int numberGoing = event.getNumberGoing();

        View[] attendees = {holder.attendieOne, holder.attendieTwo, holder.attendieThree};

        for (int i = 0; i < attendees.length; i++) {
            attendees[i].setVisibility(i < numberGoing ? View.VISIBLE : View.GONE);
        }

        if (numberGoing > attendees.length) {
            holder.numberGoing.setText(String.valueOf(numberGoing - attendees.length));
        } else {
            holder.numberGoing.setText("");
        }
        holder.eventCard.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong("eventId", event.getId());
            Navigation.findNavController(v).navigate(R.id.action_eventOrganizerEventDetailsFragment_to_eventDetailsFragment, bundle);
        });
        if(event.isFavorite){
            holder.favoriteIcon.setImageResource(R.drawable.ic_heart_full);
        }
        holder.favoriteIcon.setOnClickListener(v -> {
            CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();
            LoginResponse user = prefs.getUser();
            if (user == null ){
                Toast.makeText(v.getContext(), "You must be logged in first!", Toast.LENGTH_SHORT).show();
                return;
            }
            Call<Void> request = ClientUtils.userService.addEventToFavorites(user.getId(), new FavoriteEventRequest(event.id, user.getId()));
            request.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(v.getContext(), "Success!", Toast.LENGTH_SHORT).show();
                        if(event.isFavorite){
                            event.isFavorite = false;
                            holder.favoriteIcon.setImageResource(R.drawable.ic_heart);
                        }else{
                            event.isFavorite = true;
                            holder.favoriteIcon.setImageResource(R.drawable.ic_heart_full);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return _eventList.size();
    }

    public static class EventsViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView day;
        public TextView monthYear;

        public TextView location;
        public TextView numberGoing;
        public ImageView attendieOne;
        public ImageView attendieTwo;
        public ImageView attendieThree;
        public TextView description;
        public ImageView _image;
        public ImageView favoriteIcon;
        public LinearLayout eventCard;


        public EventsViewHolder(@NonNull View itemView) {

            super(itemView);
            title = itemView.findViewById(R.id.textViewServiceTitle);
            day = itemView.findViewById(R.id.textViewDay);
            monthYear = itemView.findViewById(R.id.textViewMonthAndYear);
            location = itemView.findViewById(R.id.textViewLocation);
            numberGoing = itemView.findViewById(R.id.numberGoingTextView);
            attendieOne = itemView.findViewById(R.id.attendieOne);
            attendieTwo = itemView.findViewById(R.id.attendieTwo);
            attendieThree = itemView.findViewById(R.id.attendieThree);
            description = itemView.findViewById(R.id.textViewEventDescription);
            _image = itemView.findViewById(R.id.imageBanner);
            favoriteIcon = itemView.findViewById(R.id.bookmarkIcon);
            eventCard = itemView.findViewById(R.id.eventCard);
        }
    }

    public EventsAdapter(List<EventHomeResponse> events) {
        _eventList = events;
    }

}
