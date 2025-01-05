package com.example.fusmobilni.adapters.communication.notifications;

import static com.example.fusmobilni.adapters.AdapterUtils.convertToUrisFromBase64;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fusmobilni.R;
import com.example.fusmobilni.responses.notifications.NotificationResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM_REVIEW = 0;
    private static final int TYPE_ITEM_REVIEW_UNREAD = 1;
    private static final int TYPE_EVENT_REVIEW = 2;
    private static final int TYPE_EVENT_REVIEW_UNREAD = 3;
    private static final int TYPE_EVENT_UPDATE = 4;
    private static final int TYPE_EVENT_UPDATE_UNREAD = 5;

    private List<NotificationResponse> items = new ArrayList<>();

    public void clearAdapter() {
        items = new ArrayList<>();
    }

    public boolean existsUnread() {
        for (NotificationResponse notification : items) {
            if (!notification.isSeen()) {
                return true;
            }
        }
        return false;
    }

    public NotificationsAdapter() {
        this.items = new ArrayList<>();
    }

    public NotificationsAdapter(List<NotificationResponse> items) {
        this.items = items;

    }

    public void unshiftNotifications(List<NotificationResponse> items) {
        for (NotificationResponse item : items) {
            appendNotification(item);
        }
    }

    public void appendNotification(NotificationResponse item) {
        this.items.add(0, item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM_REVIEW_UNREAD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_review_notification_unread, parent, false);
            return new ItemReviewNotificationsViewHolder(view);
        } else if (viewType == TYPE_ITEM_REVIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_review_notification, parent, false);
            return new ItemReviewNotificationsViewHolder(view);
        } else if (viewType == TYPE_EVENT_REVIEW_UNREAD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_event_review_notification_unread, parent, false);
            return new EventReviewNotificationsViewHolder(view);
        } else if (viewType == TYPE_EVENT_REVIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_event_review_notifcation, parent, false);
            return new EventReviewNotificationsViewHolder(view);
        } else if (viewType == TYPE_EVENT_UPDATE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_event_update_notification, parent, false);
            return new EventUpdateNotificationsViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_event_update_notification_unread, parent, false);
            return new EventUpdateNotificationsViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        if (viewType == TYPE_ITEM_REVIEW) {
            ItemReviewNotificationsViewHolder viewHolder = (ItemReviewNotificationsViewHolder) holder;
            NotificationResponse notification = items.get(position);
            viewHolder.notificationText.setText(notification.getContent());
            viewHolder.timeStamp.setText(notification.getTimeStamp());
            try {
                viewHolder.eoImage.setImageURI(convertToUrisFromBase64(viewHolder.eoImage.getContext(), notification.getImage()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (viewType == TYPE_ITEM_REVIEW_UNREAD) {
            ItemReviewNotificationsViewHolder viewHolder = (ItemReviewNotificationsViewHolder) holder;
            NotificationResponse notification = items.get(position);
            viewHolder.notificationText.setText(notification.getContent());
            viewHolder.timeStamp.setText(notification.getTimeStamp());
            try {
                viewHolder.eoImage.setImageURI(convertToUrisFromBase64(viewHolder.eoImage.getContext(), notification.getImage()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (viewType == TYPE_EVENT_REVIEW_UNREAD) {
            EventReviewNotificationsViewHolder viewHolder = (EventReviewNotificationsViewHolder) holder;
            NotificationResponse notificationResponse = items.get(position);
            viewHolder.notificationText.setText(notificationResponse.getContent());
            viewHolder.timeStamp.setText(notificationResponse.getTimeStamp());
            try {
                viewHolder.userImage.setImageURI(convertToUrisFromBase64(viewHolder.userImage.getContext(), notificationResponse.getImage()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (viewType == TYPE_EVENT_REVIEW) {
            EventReviewNotificationsViewHolder viewHolder = (EventReviewNotificationsViewHolder) holder;
            NotificationResponse notificationResponse = items.get(position);
            viewHolder.notificationText.setText(notificationResponse.getContent());
            viewHolder.timeStamp.setText(notificationResponse.getTimeStamp());
            try {
                viewHolder.userImage.setImageURI(convertToUrisFromBase64(viewHolder.userImage.getContext(), notificationResponse.getImage()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (viewType == TYPE_EVENT_UPDATE) {
            EventUpdateNotificationsViewHolder viewHolder = (EventUpdateNotificationsViewHolder) holder;
            NotificationResponse notificationResponse = items.get(position);
            viewHolder.notificationText.setText(notificationResponse.getContent());
            viewHolder.timeStamp.setText(notificationResponse.getTimeStamp());
            try {
                viewHolder.userImage.setImageURI(convertToUrisFromBase64(viewHolder.userImage.getContext(), notificationResponse.getImage()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (viewType == TYPE_EVENT_UPDATE_UNREAD) {
            EventUpdateNotificationsViewHolder viewHolder = (EventUpdateNotificationsViewHolder) holder;
            NotificationResponse notificationResponse = items.get(position);
            viewHolder.notificationText.setText(notificationResponse.getContent());
            viewHolder.timeStamp.setText(notificationResponse.getTimeStamp());
            try {
                viewHolder.userImage.setImageURI(convertToUrisFromBase64(viewHolder.userImage.getContext(), notificationResponse.getImage()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof NotificationResponse) {
            NotificationResponse notification = (NotificationResponse) items.get(position);
            if (notification.seen) {

                return TYPE_ITEM_REVIEW;
            } else {
                return TYPE_ITEM_REVIEW_UNREAD;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class EventReviewNotificationsViewHolder extends RecyclerView.ViewHolder {
        TextView timeStamp;
        TextView notificationText;
        ImageView userImage;

        public EventReviewNotificationsViewHolder(@NonNull View itemView) {
            super(itemView);
            timeStamp = itemView.findViewById(R.id.notificationTimeStamp);
            notificationText = itemView.findViewById(R.id.textViewEventReviewNotification);
            userImage = itemView.findViewById(R.id.userImageView);

        }
    }

    public static class EventUpdateNotificationsViewHolder extends RecyclerView.ViewHolder {
        TextView timeStamp;
        TextView notificationText;
        ImageView userImage;

        public EventUpdateNotificationsViewHolder(@NonNull View itemView) {
            super(itemView);
            timeStamp = itemView.findViewById(R.id.notificationTimeStamp);
            notificationText = itemView.findViewById(R.id.textViewItemReviewNotification);
            userImage = itemView.findViewById(R.id.eoImageView);

        }

    }

    public static class ItemReviewNotificationsViewHolder extends RecyclerView.ViewHolder {
        TextView timeStamp;
        TextView notificationText;
        ImageView eoImage;

        public ItemReviewNotificationsViewHolder(@NonNull View itemView) {
            super(itemView);
            timeStamp = itemView.findViewById(R.id.notificationTimeStamp);
            notificationText = itemView.findViewById(R.id.textViewItemReviewNotification);
            eoImage = itemView.findViewById(R.id.eoImageView);

        }

    }
}
