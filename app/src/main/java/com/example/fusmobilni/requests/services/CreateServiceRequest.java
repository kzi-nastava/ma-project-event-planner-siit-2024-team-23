package com.example.fusmobilni.requests.services;

import com.example.fusmobilni.model.enums.ReservationConfirmation;

public class CreateServiceRequest {
    private String name;
    private String description;
    private Long categoryId;
    private String eventTypeIds;
    private double price;
    private double discount;
    private Long serviceProviderId;
    private String specificities;
    private boolean isAvailable;
    private boolean isVisible;
    private int duration;
    private int reservationDeadline;
    private int cancellationDeadline;
    private ReservationConfirmation reservationConfirmation;


}
