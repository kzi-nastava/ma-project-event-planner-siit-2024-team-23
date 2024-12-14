package com.example.fusmobilni.responses.items.services.filter;

import java.util.List;

public class ServicesPaginationResponse {
    public List<ServicePaginationResponse> content;
    public int totalPages;
    public long totalItems;

    public ServicesPaginationResponse(List<ServicePaginationResponse> content, long totalItems, int totalPages) {
        this.content = content;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public List<ServicePaginationResponse> getContent() {
        return content;
    }

    public void setContent(List<ServicePaginationResponse> content) {
        this.content = content;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
