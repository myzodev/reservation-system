import java.util.ArrayList;
import java.util.stream.Collectors;

public class Agency implements ReservationHandlers {
    private String name;
    private ArrayList<Trip> trips = new ArrayList<Trip>();
    private ArrayList<Reservation> reservations = new ArrayList<Reservation>();

    public Agency(String name) {
        this.name = name;
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
            if (reservation == reservationToRemove) {
                Trip reservationTrip = reservation.getTrip();
                int reservationReservedSeats = reservation.getNumberOfPassengers();
                System.out.println(reservationReservedSeats);
                reservationTrip.increaseFreeSeatsBy(reservationReservedSeats);
                return true;
            }

            return false;
        });
    }

    // Other
    @Override
    public String toString() {
        return name;
    }
}
