package com.travelagencies.agencies;

import com.travelagencies.trips.Trip;
import com.travelagencies.reservations.Reservation;
import com.travelagencies.interfaces.ReservationHandlers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Agency implements ReservationHandlers, Serializable {
    private String name;
    private ArrayList<Trip> trips = new ArrayList<>();
    private ArrayList<Reservation> reservations = new ArrayList<>();
    private static ArrayList<Agency> agencies = new ArrayList<>();

    public Agency(String name) {
        this.name = name;
        agencies.add(this);
    }

    // Agencies
    public static ArrayList<Agency> getAgencies() {
        return agencies;
    }

    public static void setAgencies(ArrayList<Agency> newAgencies) {
        agencies = newAgencies;
    }

    // Name
    public String getName() {
        return name;
    }

    // Trips
    public ArrayList<Trip> getTrips() {
        return trips;
    }

    public ArrayList<Trip> getTripsWithFreeSeats() {
        return trips.stream()
                .filter(trip -> trip.getFreeSeats() > 0)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void addTrip(Trip newTrip) {
        newTrip.setAgency(this);
        trips.add(newTrip);
    }

    // Reservations
    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation newReservation) {
        Trip trip = newReservation.getTrip();
        boolean hasFreeSeats = trip.getFreeSeats() != 0;
        boolean hasEnoughSeats = trip.getFreeSeats() - newReservation.getNumberOfPassengers() >= 0;

        newReservation.setAgency(this);

        if (hasFreeSeats && hasEnoughSeats) {
            trip.reduceFreeSeatsBy(newReservation.getNumberOfPassengers());
            reservations.add(newReservation);
        }
    }

    public void removeReservation(Reservation reservationToRemove) {
        reservations.removeIf(reservation -> {
            if (reservation != reservationToRemove) return false;

            Trip reservationTrip = reservation.getTrip();

            int reservationReservedSeats = reservation.getNumberOfPassengers();
            reservationTrip.increaseFreeSeatsBy(reservationReservedSeats);

            return true;
        });
    }

    // Other
    @Override
    public String toString() {
        return name;
    }
}
