package com.example.fusmobilni.responses.items.services;

import com.example.fusmobilni.model.enums.ReservationStatus;
import com.example.fusmobilni.responses.items.CategoryResponse;

public class ServiceOfferingReservationForEventResponse {
    private Long id;
    private String ServiceName;
    private String ServiceDescription;
    private Double price;
    private String ServiceImage;
    private CategoryResponse category;
    private String eventTitle;
    private Long eventId;
    private String organizerName;
    private String organizerLastName;
    private Long organizerId;
    private ReservationStatus status;

    public ServiceOfferingReservationForEventResponse(Long id, String serviceName, String serviceDescription, Double price, String serviceImage, CategoryResponse category, String eventTitle, Long eventId, String organizerName, String organizerLastName, Long organizerId, ReservationStatus status) {
        this.id = id;
        ServiceName = serviceName;
        ServiceDescription = serviceDescription;
        this.price = price;
        ServiceImage = serviceImage;
        this.category = category;
        this.eventTitle = eventTitle;
        this.eventId = eventId;
        this.organizerName = organizerName;
        this.organizerLastName = organizerLastName;
        this.organizerId = organizerId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getServiceDescription() {
        return ServiceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        ServiceDescription = serviceDescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getServiceImage() {
        return ServiceImage;
    }

    public void setServiceImage(String serviceImage) {
        ServiceImage = serviceImage;
    }

    public CategoryResponse getCategory() {
        return category;
    }

    public void setCategory(CategoryResponse category) {
        this.category = category;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getOrganizerLastName() {
        return organizerLastName;
    }

    public void setOrganizerLastName(String organizerLastName) {
        this.organizerLastName = organizerLastName;
    }

    public Long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Long organizerId) {
        this.organizerId = organizerId;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
