package com.example.fusmobilni.fragments.items.reviews;

import com.example.fusmobilni.responses.items.ItemReviewResponse;

public interface OnItemReviewActionListener {

    void onApprove(ItemReviewResponse review);

    void onDecline(ItemReviewResponse review);
}
