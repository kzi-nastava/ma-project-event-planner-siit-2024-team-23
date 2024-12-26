package com.example.fusmobilni.fragments.communication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.R;
import com.example.fusmobilni.adapters.serviceProvider.NotificationsAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentNotificationsBinding;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.model.UserNotification;
import com.example.fusmobilni.responses.items.notifications.ItemReviewNotificationResponse;
import com.example.fusmobilni.responses.items.notifications.ItemReviewNotificationsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationsFragment extends Fragment {
    private FragmentNotificationsBinding _binding;
    private NotificationsAdapter _adapter;
    private RecyclerView _notificationsView;
    private Long userId;
    private SpinnerDialogFragment _loader;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    public static NotificationsFragment newInstance(String param1, String param2) {
        NotificationsFragment fragment = new NotificationsFragment();
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
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = _binding.getRoot();
        initializeDialogs();
        _notificationsView = _binding.notificationRecycler;
        CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();
        userId = prefs.getUser().getId();
        _adapter = new NotificationsAdapter();
        _notificationsView.setAdapter(_adapter);

        fetchNotifications();

        return root;
    }
    private void initializeDialogs() {
        _loader = new SpinnerDialogFragment();
        _loader.setCancelable(false);

    }

    @Override
    public void onDestroyView() {
        if (_adapter.existsUnread()) {
            readAllUnreadNotifications();
        }
        super.onDestroyView();
    }

    public void readAllUnreadNotifications() {
        Call<Void> call = ClientUtils.itemReviewNotificationsService.readAllByUserId(userId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void fetchNotifications() {
        _loader.show(getFragmentManager(), "loading_spinner");
        Call<ItemReviewNotificationsResponse> call = ClientUtils.itemReviewNotificationsService.findAllByUserId(userId);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ItemReviewNotificationsResponse> call, Response<ItemReviewNotificationsResponse> response) {
                if (response.isSuccessful()) {
                    List<ItemReviewNotificationResponse> notificationsResponseList = response.body().getNotifications();
                    for (UserNotification n : notificationsResponseList) {
                        _adapter.appendNotification(n);
                    }
                    _notificationsView.setAdapter(_adapter);
                    _loader.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ItemReviewNotificationsResponse> call, Throwable t) {

            }
        });
    }
}