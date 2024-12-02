package trips;

public class BoatTrip extends Trip {
    private String boatName;

    public BoatTrip(String name, String destination, int freeSeats, int ticketPrice, String boatName) {
        super(name, destination, freeSeats, ticketPrice);
        this.boatName = boatName;
    }

    // Other
    @Override
    public String toString() {
        return super.toString() + ", boat name: " + boatName;
    }
}
