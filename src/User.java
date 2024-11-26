import java.util.ArrayList;

public class User implements ReservationHandlers {
    private String name;
    private ArrayList<Reservation> reservations = new ArrayList<Reservation>();

    public User(String name) {
        this.name = name;
    }

    // Name
    public String getName() {
        return name;
    }

    // Reservations
    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation newReservation) {
        reservations.add(newReservation);
    }

    public void removeReservation(Reservation reservationToRemove) {
        reservations.removeIf(reservation -> reservation == reservationToRemove);
    }

    // Other
    @Override
    public String toString() {
        return name;
    }
}
