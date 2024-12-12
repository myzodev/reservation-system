import java.io.Serializable;
import java.util.ArrayList;

public class User implements ReservationHandlers, Serializable {
    private String name;
    private String email;
    private String phone;
    private ArrayList<Reservation> reservations = new ArrayList<>();

    private static ArrayList<User> users = new ArrayList<>();

    public User(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;

        users.add(this);
    }

    // User
    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> newUsers) {
        users = newUsers;
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
