package com.example.fusmobilni.clients.webSockets;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.fusmobilni.BuildConfig;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompMessage;

public class NotificationWebSocketClient { private StompClient stompClient;
    private static final String SOCKET_URL = "ws://" + BuildConfig.IP_ADDR + ":8080/ws-mobile";

    @SuppressLint("CheckResult")
    public void connect() {
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, SOCKET_URL);
        stompClient.connect();
        stompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lifecycleEvent -> {
                    switch (lifecycleEvent.getType()) {
                        case OPENED:
                            break;
                        case ERROR:
                            Log.e("Tag", "Stomp connection error", lifecycleEvent.getException());
                            break;
                        case CLOSED:
                            break;
                        case FAILED_SERVER_HEARTBEAT:
                            break;
                    }
                });
    }


    public void sendMessage(String destination, String message) {
        stompClient.send(destination, message).subscribe();
    }

    public Flowable<StompMessage> subscribeToTopic(String topic) {
        return stompClient.topic(topic);
    }


}
