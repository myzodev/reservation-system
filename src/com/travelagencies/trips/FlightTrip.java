package com.travelagencies.trips;

public class FlightTrip extends Trip {
    private String flightNumber;

    public FlightTrip(String name, String destination, int freeSeats, int ticketPrice, String flightNumber) {
        super(name, destination, freeSeats, ticketPrice);
        this.flightNumber = flightNumber;
    }

    // Other
    @Override
    public String toString() {
        return super.toString() + ", flight number: " + flightNumber;
    }
}
