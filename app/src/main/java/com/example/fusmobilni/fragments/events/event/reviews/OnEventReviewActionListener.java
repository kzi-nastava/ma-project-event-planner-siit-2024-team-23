package com.example.fusmobilni.fragments.events.event.reviews;

import com.example.fusmobilni.responses.events.review.EventReviewResponse;
import com.example.fusmobilni.responses.items.ItemReviewResponse;

public interface OnEventReviewActionListener {

    void onApprove(EventReviewResponse review);

    void onDecline(EventReviewResponse review);
}
