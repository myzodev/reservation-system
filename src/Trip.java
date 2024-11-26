public class Trip {
    private String name;
    private String destination;
    private int freeSeats;

    public Trip(String name, String destination, int freeSeats) {
        this.name = name;
        this.destination = destination;
        this.freeSeats = freeSeats;
    }

    // Name
    public String getName() {
        return name;
    }

    // Seats
    public int getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
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
        return name + ", destination: " + destination + ", free seats: " + freeSeats;
    }
}
