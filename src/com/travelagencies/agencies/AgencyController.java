package com.travelagencies.agencies;

import com.travelagencies.trips.*;
import com.travelagencies.users.User;
import com.travelagencies.reservations.Reservation;
import com.travelagencies.helpers.Utils;

import java.util.ArrayList;
import java.util.Scanner;

public class AgencyController {
    private static Scanner scanner = new Scanner(System.in);

    public static void showAgencyMenu() {
        boolean showMenu = true;

        while (showMenu) {
            System.out.println("\n==== Agency Menu ====");
            System.out.println("[1] Show all agencies");
            System.out.println("[2] Create an Agency");
            System.out.println("[3] Choose an Agency");
            System.out.println("[4] Back to Main Menu");
            System.out.print("Please choose an action: ");

            int chosenAction = Utils.readIntFromUser(1, 4);

            switch (chosenAction) {
                case 1 -> showAllAgencies();
                case 2 -> createAgency();
                case 3 -> chooseAgency();
                case 4 -> {
                    System.out.println("\nReturning to the Main Menu...");
                    showMenu = false;
                }
            }
        }
    }

    public static void showAllAgencies() {
        if (Agency.getAgencies().isEmpty()) {
            System.out.println("\nNo agencies available. Please create a agency first.");
            return;
        }

        Utils.renderSelectList("List of agencies", Agency.getAgencies());
    }

    public static void createAgency() {
        System.out.print("\nEnter the name of the new agency: ");
        String newAgencyName = scanner.nextLine();

        boolean emptyName = Utils.checkEmptyString(newAgencyName);

        while (emptyName) {
            System.out.print("\nAgency name cannot be empty:");
            newAgencyName = scanner.nextLine();

            emptyName = Utils.checkEmptyString(newAgencyName);
        }

        boolean agencyExists = Utils.agencyExistsWithName(newAgencyName, Agency.getAgencies());

        while (agencyExists) {
            System.out.print("\nAgency with this name already exists, try a different one: ");
            newAgencyName = scanner.nextLine();

            agencyExists = Utils.agencyExistsWithName(newAgencyName, Agency.getAgencies());
        }

        new Agency(newAgencyName);

        System.out.println("\nAgency \"" + newAgencyName + "\" has been created successfully.");
    }

    public static void chooseAgency() {
        if (Agency.getAgencies().isEmpty()) {
            System.out.println("\nNo agencies available. Please create an agency first.");
            return;
        }

        int chosenAgencyIndex = Utils.renderSelectListAndChoose("Select an Agency", Agency.getAgencies());
        Agency chosenAgency = Agency.getAgencies().get(chosenAgencyIndex);

        showAgencySubmenu(chosenAgency);
    }

    public static void showAgencySubmenu(Agency chosenAgency) {
        boolean showMenu = true;

        while (showMenu) {
            System.out.println("\n==== Agency Submenu: " + chosenAgency.getName() + " ====");
            System.out.println("[1] Create a Reservation");
            System.out.println("[2] Show Reservations");
            System.out.println("[3] Create a Trip");
            System.out.println("[4] Show Trips");
            System.out.println("[5] Back to Agency Menu");
            System.out.print("Please choose an action: ");

            int chosenAction = Utils.readIntFromUser(1, 5);

            switch (chosenAction) {
                case 1 -> createAgencyReservation(chosenAgency);
                case 2 -> showAgencyReservations(chosenAgency);
                case 3 -> createAgencyTrip(chosenAgency);
                case 4 -> showAgencyTrips(chosenAgency);
                case 5 -> {
                    System.out.println("\nReturning to the Agency Menu...");
                    showMenu = false;
                }
            }
        }
    }

    public static void createAgencyReservation(Agency chosenAgency) {
        ArrayList<Trip> tripsWithFreeSeats = chosenAgency.getTripsWithFreeSeats();

        if (tripsWithFreeSeats.isEmpty()) {
            System.out.println("\nNo trips with free seats available.");
            return;
        }

        int chosenUserIndex = Utils.renderSelectListAndChoose("Select a user to create reservation for", User.getUsers());
        User chosenUser = User.getUsers().get(chosenUserIndex);

        int chosenTripIndex = Utils.renderSelectListAndChoose("Select a Trip", tripsWithFreeSeats);
        Trip chosenTrip = tripsWithFreeSeats.get(chosenTripIndex);

        ArrayList<Reservation> usersReservations = chosenUser.getReservations();

        boolean userHasReservation = Utils.userAlreadyHasReservation(usersReservations, chosenTrip);

        while (userHasReservation) {
            System.out.print("\nUser already has a reservation for this trip, choose another one.\n");

            chosenTripIndex = Utils.renderSelectListAndChoose("Select a Trip", tripsWithFreeSeats);
            chosenTrip = tripsWithFreeSeats.get(chosenTripIndex);

            userHasReservation = Utils.userAlreadyHasReservation(usersReservations, chosenTrip);
        }

        System.out.print("Enter the number of passengers (Free seats: " + chosenTrip.getFreeSeats() + "): ");
        int numberOfPassengers = scanner.nextInt();
        scanner.nextLine();

        if (numberOfPassengers > chosenTrip.getFreeSeats()) {
            System.out.println("\nNot enough free seats available for this reservation.");
            return;
        }

        System.out.print("Would you like to pay now? (Y/n): ");
        String payNow = scanner.nextLine();
        boolean isReservationPaid = payNow.equalsIgnoreCase("Y");

        Reservation newReservation = new Reservation(chosenUser, chosenTrip, numberOfPassengers, isReservationPaid);
        chosenAgency.addReservation(newReservation);

        System.out.println("Reservation successfully created for \"" + chosenUser.getName() + "\".");
    }

    public static void showAgencyReservations(Agency chosenAgency) {
        System.out.println("\n==== Reservations for Agency: " + chosenAgency.getName() + " ====");
        Utils.renderSelectList("Reservations", chosenAgency.getReservations());
    }

    public static void createAgencyTrip(Agency chosenAgency) {
        System.out.println("\nSelect trip type: ");
        System.out.println("[1] Flight trip");
        System.out.println("[2] Boat trip");

        int tripType = scanner.nextInt();
        scanner.nextLine();

        if (tripType != 1 && tripType != 2) {
            System.out.println("\nInvalid trip type selected.");
            return;
        }

        System.out.print("\nEnter the trip name: ");
        String newTripName = scanner.nextLine();

        if (newTripName == null || newTripName.trim().isEmpty()) {
            System.out.println("\nTrip name cannot be empty.");
            return;
        }

        System.out.print("Enter the trip destination: ");
        String newTripDestination = scanner.nextLine();

        if (newTripDestination == null || newTripDestination.trim().isEmpty()) {
            System.out.println("\nTrip destination cannot be empty.");
            return;
        }

        System.out.print("Enter the number of available seats: ");
        int newTripSeats = scanner.nextInt();
        scanner.nextLine();

        if (newTripSeats <= 0) {
            System.out.println("\nNumber of seats must be greater than zero.");
            return;
        }

        System.out.print("Enter the price of the ticket: ");
        int newTripTicketPrice = scanner.nextInt();
        scanner.nextLine();

        if (newTripTicketPrice <= 0) {
            System.out.println("\nTicket price must be greater than zero.");
            return;
        }

        Trip newTrip = null;

        if (tripType == 1) {
            System.out.print("Enter the flight number: ");
            String newTripFlightNumber = scanner.nextLine();

            newTrip = new FlightTrip(newTripName, newTripDestination, newTripSeats, newTripTicketPrice, newTripFlightNumber);
        }

        if (tripType == 2) {
            System.out.print("Enter the boat name: ");
            String newTripBoatType = scanner.nextLine();

            newTrip = new BoatTrip(newTripName, newTripDestination, newTripSeats, newTripTicketPrice, newTripBoatType);
        }

        chosenAgency.addTrip(newTrip);

        System.out.println("Trip \"" + newTripName + "\" to " + newTripDestination + " has been created successfully.");
    }

    public static void showAgencyTrips(Agency chosenAgency) {
        if (chosenAgency.getTrips().isEmpty()) {
            System.out.println("\nNo trips available for this agency.");
            return;
        }

        System.out.println("\n==== Trips for Agency: " + chosenAgency.getName() + " ====");
        Utils.renderSelectList("Trips", chosenAgency.getTrips());
    }

    public static void quickReservation() {
        ArrayList<Trip> allTrips = new ArrayList<>();

        for (Agency agency : Agency.getAgencies()) {
            allTrips.addAll(agency.getTripsWithFreeSeats());
        }

        if (allTrips.isEmpty()) {
            System.out.println("\nNo trips available.");
            return;
        }

        ArrayList<String> destinations = new ArrayList<>();

        for (Trip trip: allTrips) {
            if (destinations.contains(trip.getDestination())) continue;
            destinations.add(trip.getDestination());
        }

        int destinationIndex = Utils.renderSelectListAndChoose("Choose destination you want to visit", destinations);
        String chosenDestination = destinations.get(destinationIndex);

        System.out.print("Enter your max price: ");
        int maxPriceRange = scanner.nextInt();
        scanner.nextLine();

        ArrayList<Trip> filteredTrips = new ArrayList<>();

        for (Trip trip : allTrips) {
            if (trip.getDestination().equals(chosenDestination) && trip.getTicketPrice() <= maxPriceRange) {
                filteredTrips.add(trip);
            }
        }

        int tripIndex = Utils.renderSelectListAndChoose("Select a trip", filteredTrips);
        Trip chosenTrip = filteredTrips.get(tripIndex);

        System.out.println(chosenTrip.getName());

       Agency chosenTripAgency = chosenTrip.getAgency();

        int chosenUserIndex = Utils.renderSelectListAndChoose("Select a user to create reservation for", User.getUsers());
        User chosenUser = User.getUsers().get(chosenUserIndex);

        ArrayList<Reservation> usersReservations = chosenUser.getReservations();

        boolean userHasReservation = Utils.userAlreadyHasReservation(usersReservations, chosenTrip);

        while (userHasReservation) {
            System.out.print("\nUser already has a reservation for this trip, choose another one.\n");

            tripIndex = Utils.renderSelectListAndChoose("Select a trip", filteredTrips);
            chosenTrip = allTrips.get(tripIndex);

            userHasReservation = Utils.userAlreadyHasReservation(usersReservations, chosenTrip);
        }

        System.out.print("Enter the number of passengers (Free seats: " + chosenTrip.getFreeSeats() + "): ");
        int numberOfPassengers = scanner.nextInt();
        scanner.nextLine();

        if (numberOfPassengers > chosenTrip.getFreeSeats()) {
            System.out.println("\nNot enough free seats available for this reservation.");
            return;
        }

        System.out.print("Would you like to pay now? (Y/n): ");
        String payNow = scanner.nextLine();
        boolean isReservationPaid = payNow.equalsIgnoreCase("Y");

        Reservation newReservation = new Reservation(chosenUser, chosenTrip, numberOfPassengers, isReservationPaid);
        chosenTripAgency.addReservation(newReservation);

        System.out.println("Reservation successfully created for \"" + chosenUser.getName() + "\".");
    }
}
