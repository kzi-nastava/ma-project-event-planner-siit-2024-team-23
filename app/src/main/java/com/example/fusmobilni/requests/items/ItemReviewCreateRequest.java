package com.example.fusmobilni.requests.items;

public class ItemReviewCreateRequest {
    public String content;
    public int rating;
    public Long eoId;
    public Long itemId;

    public ItemReviewCreateRequest(String content, Long eoId, Long itemId, int rating) {
        this.content = content;
        this.eoId = eoId;
        this.itemId = itemId;
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getEoId() {
        return eoId;
    }

    public void setEoId(Long eoId) {
        this.eoId = eoId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
