package com.travelagencies.trips;

import com.travelagencies.agencies.Agency;

import java.io.Serializable;

public abstract class Trip implements Serializable {
    private Agency agency;
    private String name;
    private String destination;
    private int freeSeats;
    private int ticketPrice;

    public Trip(String name, String destination, int freeSeats, int ticketPrice) {
        this.name = name;
        this.destination = destination;
        this.freeSeats = freeSeats;
        this.ticketPrice = ticketPrice;
    }

    // Agency
    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    // Name
    public String getName() {
        return name;
    }

    // Destination
    public String getDestination() {
        return destination;
    }

    // Seats
    public int getFreeSeats() {
        return freeSeats;
    }

    public void increaseFreeSeatsBy(int increaseBy) {
        freeSeats += increaseBy;
    }

    public void reduceFreeSeatsBy(int reduceBy) {
        freeSeats -= reduceBy;
    }


    // Ticket price
    public int getTicketPrice() {
        return ticketPrice;
    }

    // Other
    @Override
    public String toString() {
        return name + ", destination: " + destination + ", free seats: " + freeSeats + ", ticket price: " + ticketPrice;
    }
}
