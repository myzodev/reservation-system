import java.util.ArrayList;

public class User {
    private String name;
    private ArrayList<Reservation> reservations = new ArrayList<Reservation>();

    private static ArrayList<User> users = new ArrayList<User>();

    public User(String name) {
        this.name = name;
        users.add(this);
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
        reservations.removeIf(reservation -> {
            if (reservation == reservationToRemove) {
                Trip correspondingTrip = reservation.getTrip();
                correspondingTrip.setFreeSeats(correspondingTrip.getFreeSeats() + reservation.getNumberOfPassengers());
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

    public static ArrayList<User> getAllUsers() {
        return users;
    }
}
