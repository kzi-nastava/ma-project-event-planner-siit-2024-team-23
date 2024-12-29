package com.example.fusmobilni.fragments.communication.notifications;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fusmobilni.adapters.serviceProvider.NotificationsAdapter;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.FragmentNotificationsBinding;
import com.example.fusmobilni.fragments.dialogs.SpinnerDialogFragment;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.example.fusmobilni.responses.notifications.NotificationResponse;
import com.example.fusmobilni.viewModels.notifications.NotificationViewModel;

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
    private NotificationViewModel _notificationsViewModel;

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
        _notificationsViewModel = new ViewModelProvider(getActivity()).get(NotificationViewModel.class);
        _notificationsView = _binding.notificationRecycler;
        initializeUserExisting();

        return root;
    }

    private void initializeUserExisting() {
        CustomSharedPrefs prefs = CustomSharedPrefs.getInstance();
        if (prefs.getUser() == null) {
            return;
        }
        userId = prefs.getUser().getId();
        _adapter = new NotificationsAdapter();
        _notificationsView.setAdapter(_adapter);

        fetchNotifications();
    }

    private void initializeDialogs() {
        _loader = new SpinnerDialogFragment();
        _loader.setCancelable(false);

    }

    @Override
    public void onDestroyView() {
        LoginResponse user = CustomSharedPrefs.getInstance().getUser();

        if (user != null && _adapter.existsUnread()) {
            readAllUnreadNotifications();
        }
        super.onDestroyView();
    }

    public void readAllUnreadNotifications() {
        Call<Void> call = ClientUtils.notificationsService.readAllByUserId(userId);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    _notificationsViewModel.readAll();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void displayNotifications() {
        _loader.show(getFragmentManager(), "loading_spinner");
        _adapter.clearAdapter();
        List<NotificationResponse> notifications = _notificationsViewModel.getNotifications().getValue();
        for (NotificationResponse n : notifications) {
            _adapter.appendNotification(n);
        }
        _notificationsView.setAdapter(_adapter);
        _notificationsView.scrollToPosition(_notificationsViewModel.getNotifications().getValue().size() - 1);
        _loader.dismiss();
    }

    public void fetchNotifications() {
        displayNotifications();
        _notificationsViewModel.getNotifications().observe(getViewLifecycleOwner(), v -> {
            displayNotifications();
        });
    }
}