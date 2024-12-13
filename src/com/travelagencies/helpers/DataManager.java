package com.travelagencies.helpers;

import com.travelagencies.agencies.Agency;
import com.travelagencies.trips.*;
import com.travelagencies.users.User;
import com.travelagencies.reservations.Reservation;

import java.io.*;
import java.util.ArrayList;

public class DataManager {
    private static final String AGENCY_FILE = "agencies.dat";
    private static final String USER_FILE = "users.dat";

    public static void saveData() {
        try (ObjectOutputStream agencyOutputStream = new ObjectOutputStream(new FileOutputStream(AGENCY_FILE));
             ObjectOutputStream userOutputStream = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {

            agencyOutputStream.writeObject(Agency.getAgencies());
            userOutputStream.writeObject(User.getUsers());

            System.out.println("\nData successfully saved to files.");
        } catch (IOException e) {
            System.out.println("\nError saving data: " + e.getMessage());
        }
    }

    public static void loadData() {
        try (ObjectInputStream agencyInputStream = new ObjectInputStream(new FileInputStream(AGENCY_FILE));
             ObjectInputStream userInputStream = new ObjectInputStream(new FileInputStream(USER_FILE))) {

            Agency.setAgencies((ArrayList<Agency>) agencyInputStream.readObject());
            User.setUsers((ArrayList<User>) userInputStream.readObject());

            System.out.println("\nData successfully loaded from files.");
        } catch (FileNotFoundException e) {
            System.out.println("\nData files not found. Starting with empty data.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("\nError loading data: " + e.getMessage());
        }
    }

    public static void loadDummyData() {
        Agency agency1 = new Agency("Beyond Horizons");
        User user1 = new User("Miro", "miro@email.com", "+421 000 000");
        Trip trip1 = new BoatTrip("Trip to Paris", "France", 10, 100, "Liberty");
        agency1.addTrip(trip1);
        Trip trip2 = new FlightTrip("Trip to Warsaw", "Poland", 16, 120, "72D");
        agency1.addTrip(trip2);
        Reservation reservation1 = new Reservation(user1, agency1.getTrips().getFirst(), 6, false);
        agency1.addReservation(reservation1);

        Trip trip1_2 = new BoatTrip("Another Paris Adventure", "France", 5, 150, "Voyager");
        agency1.addTrip(trip1_2);
        Trip trip2_2 = new FlightTrip("Visit to Krakow", "Poland", 20, 130, "14B");
        agency1.addTrip(trip2_2);

        Agency agency2 = new Agency("Dream Ventures");
        User user2 = new User("Eva", "eva@email.com", "+421 111 111");
        Trip trip3 = new BoatTrip("Mediterranean Adventure", "Italy", 7, 200, "Queen");
        agency2.addTrip(trip3);
        Trip trip4 = new FlightTrip("City Break in Amsterdam", "Netherlands", 14, 150, "11A");
        agency2.addTrip(trip4);
        Reservation reservation2 = new Reservation(user2, agency2.getTrips().getFirst(), 4, true);
        agency2.addReservation(reservation2);

        Trip trip3_2 = new BoatTrip("Venetian Delights", "Italy", 10, 180, "Siren");
        agency2.addTrip(trip3_2);
        Trip trip4_2 = new FlightTrip("Weekend in Rotterdam", "Netherlands", 8, 140, "32C");
        agency2.addTrip(trip4_2);

        Agency agency3 = new Agency("Sky Explorers");
        User user3 = new User("Peter", "peter@email.com", "+421 222 222");
        Trip trip5 = new BoatTrip("Caribbean Escape", "Jamaica", 12, 250, "Oceanic");
        agency3.addTrip(trip5);
        Trip trip6 = new FlightTrip("Tokyo Tour", "Japan", 10, 180, "32F");
        agency3.addTrip(trip6);
        Reservation reservation3 = new Reservation(user3, agency3.getTrips().getFirst(), 8, false);
        agency3.addReservation(reservation3);

        Trip trip5_2 = new BoatTrip("Jamaican Paradise", "Jamaica", 14, 270, "Tropical");
        agency3.addTrip(trip5_2);
        Trip trip6_2 = new FlightTrip("Kyoto Experience", "Japan", 18, 200, "21D");
        agency3.addTrip(trip6_2);

        Reservation reservation4 = new Reservation(user2, agency2.getTrips().get(1), 2, true);
        agency2.addReservation(reservation4);
    }
}
