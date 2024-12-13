package com.travelagencies.helpers;

import com.travelagencies.agencies.Agency;
import com.travelagencies.users.User;
import com.travelagencies.trips.Trip;
import com.travelagencies.reservations.Reservation;

import java.util.ArrayList;
import java.util.Scanner;

public class Utils {
    public static Scanner scanner = new Scanner(System.in);

    // User
    public static int readIntFromUser(int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine();

                if (input >= min && input <= max) {
                    return input;
                }

                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    public static boolean userExistsWithEmail(String emailToCheck, ArrayList<User> users) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(emailToCheck)) {
                return true;
            }
        }

        return false;
    }

    public static boolean userAlreadyHasReservation(ArrayList<Reservation> usersReservations, Trip chosenTrip) {
        for (Reservation reservation : usersReservations) {
            if (reservation.getTrip().equals(chosenTrip)) {
                return true;
            }
        }

        return false;
    }

    // Agency
    public static boolean agencyExistsWithName(String nameToCheck, ArrayList<Agency> agencies) {
        for (Agency agency : agencies) {
            if (agency.getName().equalsIgnoreCase(nameToCheck)) {
                return true;
            }
        }

        return false;
    }

    // Menu
    public static <T> void renderSelectList(String text, ArrayList<T> options) {
        System.out.println("\n==== " + text + " ====");

        for (int i = 0; i < options.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + options.get(i));
        }
    }

    public static <T> int renderSelectListAndChoose(String text, ArrayList<T> options) {
        renderSelectList(text, options);

        int chosenIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        return chosenIndex;
    }

    public static boolean checkEmptyString(String text) {
        return text.trim().isEmpty();
    }
}
