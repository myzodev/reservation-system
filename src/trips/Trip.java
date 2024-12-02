package trips;

import java.io.Serializable;

public abstract class Trip implements Serializable {
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

    // Name
    public String getName() {
        return name;
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

    // Other
    @Override
    public String toString() {
        return name + ", destination: " + destination + ", free seats: " + freeSeats + ", ticket price: " + ticketPrice;
    }
}
