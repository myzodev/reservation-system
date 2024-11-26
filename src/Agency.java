import java.util.ArrayList;
import java.util.stream.Collectors;

public class Agency {
    private String name;
    private ArrayList<Trip> trips = new ArrayList<Trip>();
    private ArrayList<Reservation> reservations = new ArrayList<Reservation>();

    private static ArrayList<Agency> agencies = new ArrayList<Agency>();

    public Agency(String name) {
        this.name = name;
        agencies.add(this);
    }

    public String getName() {
        return name;
    }

    // Trips
    public ArrayList<Trip> getAllTrips() {
        return trips;
    }

    public ArrayList<Trip> getAllTripsWithFreeSeats() {
        return trips.stream()
                .filter(trip -> trip.getFreeSeats() > 0)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void createTrip(String name, String destination, int freeSeats) {
        Trip newTrip = new Trip(name, destination, freeSeats);
        trips.add(newTrip);
    }

    // Reservations
    public ArrayList<Reservation> getAllReservations() {
        return reservations;
    }

    public void createReservation(User user, Trip trip, int numberOfPassengers, boolean isPaid) {
        boolean hasFreeSeats = trip.getFreeSeats() != 0;
        boolean hasEnoughSeats = trip.getFreeSeats() - numberOfPassengers >= 0;

        if (hasFreeSeats && hasEnoughSeats) {
            trip.reduceFreeSeatsBy(numberOfPassengers);
            Reservation newReservation = new Reservation(user, trip, numberOfPassengers, isPaid);
            reservations.add(newReservation);
        }
    }

    // Other
    public static ArrayList<Agency> getAllAgencies() {
        return agencies;
    }
}
