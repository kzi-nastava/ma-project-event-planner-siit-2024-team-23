package com.example.fusmobilni.viewModels.notifications;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fusmobilni.BuildConfig;
import com.example.fusmobilni.R;
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.responses.notifications.NotificationResponse;
import com.example.fusmobilni.responses.notifications.NotificationsResponse;

import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationViewModel extends ViewModel {
    MutableLiveData<List<NotificationResponse>> notifications = new MutableLiveData<>(new ArrayList<>());

    MutableLiveData<Integer> countUnread = new MutableLiveData<>(0);
    MutableLiveData<Long> userId = new MutableLiveData<>(null);


    public void fetchNotifications() {
        if (userId == null || userId.getValue() == null) {
            return;
        }
        Call<NotificationsResponse> call = ClientUtils.notificationsService.findAllByUserId(userId.getValue());
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<NotificationsResponse> call, Response<NotificationsResponse> response) {
                if (response.isSuccessful()) {
                    notifications.setValue(response.body().getNotifications());
                } else {
                    notifications.setValue(new ArrayList<>() {
                    });
                }
                countUnread();
            }

            @Override
            public void onFailure(Call<NotificationsResponse> call, Throwable t) {
                notifications.setValue(new ArrayList<>() {
                });
                countUnread();
            }
        });
    }

    private void countUnread() {
        Integer count = 0;
        for (NotificationResponse n : notifications.getValue()) {
            if (!n.isSeen()) {
                ++count;
            }
        }
        setCountUnread(count);
    }

    public void readAll() {
        for (NotificationResponse n : notifications.getValue()) {
            n.setSeen(true);
        }
        setCountUnread(0);
    }

    public MutableLiveData<Long> getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId.setValue(userId);
        fetchNotifications();
    }

    public MutableLiveData<Integer> getCountUnread() {
        return countUnread;
    }

    public void setCountUnread(Integer countUnread) {
        this.countUnread.setValue(countUnread);
    }

    public MutableLiveData<List<NotificationResponse>> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationResponse> notifications) {
        this.notifications.setValue(notifications);
    }

}
