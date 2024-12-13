package com.travelagencies.users;

import com.travelagencies.reservations.Reservation;
import com.travelagencies.helpers.Utils;

import java.util.Scanner;

public class UserController {
    private static Scanner scanner = new Scanner(System.in);

    public static void showUserMenu() {
        boolean showMenu = true;

        while (showMenu) {
            System.out.println("\n==== User Menu ====");
            System.out.println("[1] Show all users");
            System.out.println("[2] Create a User");
            System.out.println("[3] Choose a User");
            System.out.println("[4] Back to Main Menu");
            System.out.print("Please choose an action: ");

            int chosenAction = Utils.readIntFromUser(1, 4);

            switch (chosenAction) {
                case 1 -> showAllUsers();
                case 2 -> createUser();
                case 3 -> chooseUser();
                case 4 -> {
                    System.out.println("Returning to the Main Menu...");
                    showMenu = false;
                }
            }
        }
    }

    public static void showAllUsers() {
        if (User.getUsers().isEmpty()) {
            System.out.println("\nNo users available. Please create a user first.");
            return;
        }

        Utils.renderSelectList("List of users", User.getUsers());
    }

    public static void createUser() {
        System.out.print("\nEnter the name of the new user: ");
        String newUsername = scanner.nextLine();

        if (newUsername == null || newUsername.trim().isEmpty()) {
            System.out.println("\nUser name cannot be empty.");
            return;
        }

        System.out.print("\nEnter the email of the new user: ");
        String newUserEmail = scanner.nextLine();

        if (newUserEmail == null || newUserEmail.trim().isEmpty()) {
            System.out.println("\nUser email cannot be empty.");
            return;
        }

        boolean userExists = Utils.userExistsWithEmail(newUserEmail, User.getUsers());

        while (userExists) {
            System.out.print("\nUser with this email already exists, try a different one: ");
            newUserEmail = scanner.nextLine();

            userExists = Utils.userExistsWithEmail(newUserEmail, User.getUsers());
        }

        System.out.print("\nEnter the phone number of the new user: ");
        String newUserPhone = scanner.nextLine();

        new User(newUsername, newUserEmail, newUserPhone);

        System.out.println("User \"" + newUsername + "\" has been created successfully.");
    }

    public static void chooseUser() {
        if (User.getUsers().isEmpty()) {
            System.out.println("\nNo users available. Please create a user first.");
            return;
        }

        int chosenUserIndex = Utils.renderSelectListAndChoose("Select a User", User.getUsers());
        User chosenUser = User.getUsers().get(chosenUserIndex);

        showUserSubmenu(chosenUser);
    }

    public static void showUserSubmenu(User chosenUser) {
        boolean showMenu = true;

        while (showMenu) {
            System.out.println("\n==== User Submenu: " + chosenUser.getName() + " ====");
            System.out.println("[1] Show Reservations");
            System.out.println("[2] Pay a Reservation");
            System.out.println("[3] Cancel a Reservation");
            System.out.println("[4] Back to User Menu");
            System.out.print("Please choose an action: ");

            int chosenAction = Utils.readIntFromUser(1, 4);

            switch (chosenAction) {
                case 1 -> showUserReservations(chosenUser);
                case 2 -> payUserReservation(chosenUser);
                case 3 -> cancelUserReservation(chosenUser);
                case 4 -> {
                    System.out.println("Returning to the User Menu...");
                    showMenu = false;
                }
            }
        }
    }

    public static void showUserReservations(User chosenUser) {
        if (chosenUser.getReservations().isEmpty()) {
            System.out.println("\nNo reservations found for this user.");
            return;
        }

        System.out.println("\n==== Reservations for User: " + chosenUser.getName() + " ====");

        for (Reservation reservation : chosenUser.getReservations()) {
            System.out.println(reservation);
        }
    }

    public static void payUserReservation(User chosenUser) {
        if (chosenUser.getReservations().isEmpty()) {
            System.out.println("\nNo reservations found for this user.");
            return;
        }

        int chosenReservationIndex = Utils.renderSelectListAndChoose("Select a Reservation to Pay", chosenUser.getReservations());
        Reservation chosenReservation = chosenUser.getReservations().get(chosenReservationIndex);

        chosenReservation.setPaid(true);
        System.out.println("\nReservation has been marked as paid.");
    }

    public static void cancelUserReservation(User chosenUser) {
        if (chosenUser.getReservations().isEmpty()) {
            System.out.println("\nNo reservations found for this user.");
            return;
        }

        int chosenReservationIndex = Utils.renderSelectListAndChoose("Select a Reservation to Cancel", chosenUser.getReservations());
        Reservation chosenReservation = chosenUser.getReservations().get(chosenReservationIndex);

        chosenReservation.getAgency().removeReservation(chosenReservation);
        chosenUser.removeReservation(chosenReservation);

        System.out.println("\nReservation has been canceled successfully.");
    }
}
