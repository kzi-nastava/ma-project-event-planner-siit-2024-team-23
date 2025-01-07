package com.example.fusmobilni.fragments.events.event;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.events.AgendaActivityAdapter;
import com.example.fusmobilni.adapters.events.EventComponentAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentEventDetailsBinding;
import com.example.fusmobilni.model.enums.UserType;
import com.example.fusmobilni.model.event.AgendaActivity;
import com.example.fusmobilni.model.event.Event;
import com.example.fusmobilni.model.users.User;
import com.example.fusmobilni.requests.communication.chat.ChatCreateRequest;
import com.example.fusmobilni.requests.users.favorites.FavoriteEventRequest;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.events.EventDetailsResponse;
import com.example.fusmobilni.responses.events.attendance.HasAttendedResponse;
import com.example.fusmobilni.responses.events.components.EventComponentsResponse;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailsFragment extends Fragment {
    private FragmentEventDetailsBinding _binding;
    private boolean favorite = false;
    private AgendaActivityAdapter _adapter;
    private EventComponentAdapter _eventComponentAdapter;
    private EventDetailsResponse event;
    private LoginResponse user;

    Snackbar snackbar;

    public EventDetailsFragment() {
        // Required empty public constructor
    }

    public static EventDetailsFragment newInstance() {
        EventDetailsFragment fragment = new EventDetailsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();
        user = prefs.getUser();
        _binding = FragmentEventDetailsBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        if (getArguments() == null) {
            Toast.makeText(requireContext(), "Something went wrong with event or invalid event id is provided!",
                    Toast.LENGTH_LONG).show();
        }
        _binding.generatePdfBtn.setOnClickListener(v -> onGeneratePdfClick());
        animateFavoriteButton();
        initializeFavoriteButton();
        Long eventId = getArguments().getLong("eventId");
        fetchData(eventId);
        fetchEventImage(eventId);
        setUpEditButton();
        _binding.button2.setOnClickListener(v -> createChat());
        _binding.statsButton.setOnClickListener(v->{
            Toast.makeText(requireContext(), "Klik", Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putLong("eventId", eventId);
            Navigation.findNavController(requireView()).navigate(R.id.action_to_event_statistics, bundle);
        });
        return root;
    }

    private boolean isEventOwner() {
        LoginResponse user = CustomSharedPrefs.getInstance().getUser();
        if(user == null || event == null){
            return false;
        }
        return event.getEventOrganizer().getId().equals(user.getId());
    }

    private boolean isAdmin() {
        LoginResponse user = CustomSharedPrefs.getInstance().getUser();
        if(user == null || event == null){
            return false;
        }
        return user.getRole().equals(UserType.ADMIN);
    }

    private void onGeneratePdfClick() {
        Call<ResponseBody> request = ClientUtils.eventsService.downloadEventPdf(event.getId());
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        try {
                            String fileName = "event_details_" + event.getId() + "_" + System.currentTimeMillis() + ".pdf";
                            File file = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);

                            try (InputStream inputStream = response.body().byteStream();
                                 FileOutputStream outputStream = new FileOutputStream(file)) {
                                byte[] buffer = new byte[4096];
                                int read;
                                while ((read = inputStream.read(buffer)) != -1) {
                                    outputStream.write(buffer, 0, read);
                                }
                                outputStream.flush();
                            }

                            // Notify download complete
                            new Handler(Looper.getMainLooper()).post(() -> {
                                Toast.makeText(requireContext(), "PDF downloaded: " + fileName, Toast.LENGTH_LONG).show();
                                openPdf(requireContext(), file);
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                            new Handler(Looper.getMainLooper()).post(() ->
                                    Toast.makeText(requireContext(), "Download failed", Toast.LENGTH_SHORT).show()
                            );
                        }
                    }).start();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Download failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static void openPdf(Context context, File file) {
        Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No PDF viewer installed", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkIfHasAttended() {
        if (getUserId() == null) {
            return;
        }
        Call<HasAttendedResponse> call = ClientUtils.attendanceService.checkIfUserHasAttended(event.getId(), getUserId());
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<HasAttendedResponse> call, Response<HasAttendedResponse> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                if (!response.body().isHasAttended()) {
                    return;
                }
                showSnackBar();
            }

            @Override
            public void onFailure(Call<HasAttendedResponse> call, Throwable t) {

            }
        });
    }

    private void fetchEventImage(Long eventId) {
        Call<ResponseBody> request = ClientUtils.eventsService.findEventImage(eventId);
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        try (ResponseBody responseBody = response.body()) {
                            Bitmap bitmap = BitmapFactory.decodeStream(responseBody.byteStream());
                            _binding.eventImage.post(() -> _binding.eventImage.setImageBitmap(bitmap));
                        }
                    }).start();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("EventRepository", "Failed to load image: " + t.getMessage());
                _binding.eventImage.post(() -> _binding.eventImage.setImageResource(R.drawable.event_image_placeholder));
            }
        });
    }

    private void fetchData(Long eventId) {
        Call<EventDetailsResponse> request = ClientUtils.eventsService.findEventById(eventId);
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<EventDetailsResponse> call, @NonNull Response<EventDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    event = response.body();
                    _binding.eventDetailsText.setText(event.getTitle());
                    _binding.textViewEventLocationHorizontal.setText(event.getLocation().toString());
                    _binding.textViewOrganizerNameServiceDetails.setText(String.format("%s %s.", event.getEventOrganizer().firstName, event.getEventOrganizer().getLastName().charAt(0)));
                    _binding.textViewEventDescriptionDetails.setText(event.getDescription());
                    if (event.getAgendas().isEmpty()) {
                        _binding.eventActivitiesRecycleView.setVisibility(View.GONE);
                        _binding.eventAgendaLbl.setVisibility(View.GONE);
                    } else {
                        _binding.eventActivitiesRecycleView.setVisibility(View.VISIBLE);
                        _binding.eventAgendaLbl.setVisibility(View.VISIBLE);
                        _adapter = new AgendaActivityAdapter(event.getAgendas());
                        _binding.eventActivitiesRecycleView.setAdapter(_adapter);
                    }
                    if(isAdmin() || isEventOwner()) {
                        _binding.statsButton.setVisibility(View.VISIBLE);
                    }
                    else{
                        _binding.statsButton.setVisibility(View.GONE);
                    }
                    fetchEventOrganizerImage(event.getEventOrganizer().id);
                    fetchEventComponents(event.getId());
                    checkIfHasAttended();
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventDetailsResponse> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Cannot fetch event data!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void fetchEventComponents(Long id) {
        Call<EventComponentsResponse> request = ClientUtils.eventsService.findComponentsByEventId(id);
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<EventComponentsResponse> call, @NonNull Response<EventComponentsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().components.isEmpty()) {
                        _binding.componentsLbl.setVisibility(View.GONE);
                        _binding.eventComponentsRV.setVisibility(View.GONE);
                    } else {
                        _binding.componentsLbl.setVisibility(View.VISIBLE);
                        _binding.eventComponentsRV.setVisibility(View.VISIBLE);
                        _eventComponentAdapter = new EventComponentAdapter(response.body().components);
                        _binding.eventComponentsRV.setAdapter(_eventComponentAdapter);
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<EventComponentsResponse> call, @NonNull Throwable t) {

            }
        });
    }

    private void fetchEventOrganizerImage(Long id) {
        initializeUserProfileActions();
        Call<ResponseBody> request = ClientUtils.authService.findProfileImageByUserId(id);
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        try (ResponseBody responseBody = response.body()) {
                            Bitmap bitmap = BitmapFactory.decodeStream(responseBody.byteStream());
                            _binding.eoProfileImage.post(() -> _binding.eoProfileImage.setImageBitmap(bitmap));
                        }
                    }).start();

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("AuthRepository", "Failed to load image: " + t.getMessage());
                _binding.eoProfileImage.post(() -> _binding.eoProfileImage.setImageResource(R.drawable.person));
            }
        });
    }

    private void animateFavoriteButton() {
        _binding.favoriteButton.animate()
                .alpha(0f)
                .setDuration(100)
                .withEndAction(() -> {
                    _binding.favoriteButton.setIconResource(favorite ? R.drawable.ic_heart_full : R.drawable.ic_heart);

                    _binding.favoriteButton.animate()
                            .alpha(1f)
                            .setDuration(100)
                            .start();
                })
                .start();
    }

    private void showSnackBar() {
        if (getUserId() == null) {
            return;
        }
        View rootView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        snackbar = Snackbar.make(rootView, "You have attended this event, give us a review", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Review", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("eventName", event.getTitle());
                args.putLong("eventId", event.getId());
                args.putLong("userId", getUserId());
                Navigation.findNavController(getView()).navigate(R.id.action_to_event_review, args);
            }
        });
        snackbar.show();
    }

    private void initializeFavoriteButton() {
        _binding.favoriteButton.setOnClickListener(v -> {
            if (user == null) {
                Toast.makeText(v.getContext(), "You must be logged in first!", Toast.LENGTH_SHORT).show();
                return;
            }
            Call<Void> request = ClientUtils.userService.addEventToFavorites(user.getId(), new FavoriteEventRequest(event.getId(), user.getId()));
            request.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(v.getContext(), "Success!", Toast.LENGTH_SHORT).show();
                        favorite = !favorite;
                        animateFavoriteButton();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

                }
            });
        });
    }

    private void setUpEditButton() {
        _binding.editBtn.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("currFragment", 1);
            bundle.putLong("eventId", event.getId());
            Navigation.findNavController(_binding.getRoot()).navigate(R.id.action_eventDetails_toBudgetPlanning, bundle);
        });
    }

    private void initializeUserProfileActions() {
        _binding.eoProfileImage.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_toForeignUserProfile, createUserProfileBundle());
        });
    }

    private Bundle createUserProfileBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong("userId", event.getEventOrganizer().getId());
        return bundle;
    }

    private Long getUserId() {
        LoginResponse user = CustomSharedPrefs.getInstance(getContext()).getUser();
        if (user == null)
            return null;
        return user.getId();
    }

    private void createChat() {
        Long userId = getUserId();
        if (userId == null) {
            return;
        }
        ChatCreateRequest request = new ChatCreateRequest(userId, event.getEventOrganizer().getId());
        ClientUtils.chatService.create(userId, request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Navigation.findNavController(_binding.getRoot()).navigate(R.id.action_toChatsFragment);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}