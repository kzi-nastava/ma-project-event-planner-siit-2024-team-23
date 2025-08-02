package com.example.fusmobilni.helper;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.fusmobilni.R;
import com.example.fusmobilni.activities.HomeActivity;
import com.example.fusmobilni.responses.notifications.NotificationResponse;

public class NotificationHelper {
    public static  void showNotification(FragmentActivity activity, NotificationResponse notification){
        Intent intent = new Intent(activity, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("navigateTo","notifications");
        PendingIntent pendingIntent = PendingIntent.getActivity(
                activity,0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, "socket_channel_id")
                .setSmallIcon(R.drawable.ic_notification) // Add this icon in res/drawable
                .setContentTitle(notification.getContent() != null ? notification.getContent() : "New Notification")
                .setContentText(notification.getContent()) // Or getMessage(), etc.
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(activity);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }

}
