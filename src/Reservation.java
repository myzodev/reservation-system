public class Reservation {
    private User user;
    private Trip trip;
    private int numberOfPassengers;
    private boolean isPaid;

    public Reservation(User user, Trip trip, int numberOfPassengers, boolean isPaid) {
        this.user = user;
        this.trip = trip;
        this.numberOfPassengers = numberOfPassengers;
        this.isPaid = isPaid;

        user.addReservation(this);
    }

    // Trip
    public Trip getTrip() {
        return trip;
    }

    // Passenger
    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    // Payment
    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    // Other
    @Override
    public String toString() {
        return user + " | to: " + trip + " | reserved seats - " + numberOfPassengers + " | is paid: " + isPaid;
    }
}
