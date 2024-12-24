package com.example.fusmobilni.requests.users.favorites;

public class FavoriteServiceRequest {
    private Long serviceId;
    private Long userId;

    public FavoriteServiceRequest(Long serviceId, Long userId) {
        this.serviceId = serviceId;
        this.userId = userId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
