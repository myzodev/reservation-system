import java.io.Serializable;
import java.util.ArrayList;

public class User implements ReservationHandlers, Serializable {
    private String name;
    private String email;
    private String phone;
    private ArrayList<Reservation> reservations = new ArrayList<Reservation>();

    public User(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Name
    public String getName() {
        return name;
    }

    // Email
    public String getEmail() {
        return email;
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
        return name + ", " + email + ", " + phone;
    }
}
