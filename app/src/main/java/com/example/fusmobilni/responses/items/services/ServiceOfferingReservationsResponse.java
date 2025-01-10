package com.example.fusmobilni.responses.items.services;

import java.util.List;

public class ServiceOfferingReservationsResponse {
    List<ServiceOfferingReservationForEventResponse> reservations;

    public ServiceOfferingReservationsResponse(List<ServiceOfferingReservationForEventResponse> reservations) {
        this.reservations = reservations;
    }

    public List<ServiceOfferingReservationForEventResponse> getReservations() {
        return reservations;
    }

    public void setReservations(List<ServiceOfferingReservationForEventResponse> reservations) {
        this.reservations = reservations;
    }
}
