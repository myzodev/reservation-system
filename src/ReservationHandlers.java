import java.util.ArrayList;

public interface ReservationHandlers {
    ArrayList<Reservation> getReservations();
    void addReservation(Reservation newReservation);
    void removeReservation(Reservation reservationToRemove);
}
