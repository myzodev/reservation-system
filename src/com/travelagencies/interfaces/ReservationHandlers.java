package com.travelagencies.interfaces;

import java.util.ArrayList;

public interface ReservationHandlers {
    ArrayList<com.travelagencies.reservations.Reservation> getReservations();
    void addReservation(com.travelagencies.reservations.Reservation newReservation);
    void removeReservation(com.travelagencies.reservations.Reservation reservationToRemove);
}
