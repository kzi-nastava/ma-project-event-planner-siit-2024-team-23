package com.example.fusmobilni.responses.items.products.filter;

import java.util.List;

public class ProductsPaginationResponse {
    public List<ProductPaginationResponse> content;
    public int totalPages;
    public long totalItems;

    public ProductsPaginationResponse(List<ProductPaginationResponse> content, long totalItems, int totalPages) {
        this.content = content;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public List<ProductPaginationResponse> getContent() {
        return content;
    }

    public void setContent(List<ProductPaginationResponse> content) {
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
