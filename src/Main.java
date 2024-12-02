import trips.Trip;
import trips.BoatTrip;
import trips.FlightTrip;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner scanner;
    private static ArrayList<Agency> agencies = new ArrayList<>();
    private static ArrayList<User> users = new ArrayList<>();

    private static final String AGENCY_FILE = "agencies.dat";
    private static final String USER_FILE = "users.dat";

    public static void main(String[] args) {
        initData();
        startApp();
    }

    private static void initData() {
        Agency agency1 = new Agency("Beyond Horizons");
        agencies.add(agency1);

        User user1 = new User("Miro", "miro@email.com", "+421 000 000");
        users.add(user1);

        Trip trip1 = new BoatTrip("Trip to Paris", "France", 10, 100, "Liberty");
        agency1.addTrip(trip1);

        Trip trip2 = new FlightTrip("Trip to Warsaw", "Poland", 16, 120, "72D");
        agency1.addTrip(trip2);

        Reservation reservation1 = new Reservation(user1, agency1.getTrips().getFirst(), 6, false);
        agency1.addReservation(reservation1);

        scanner = new Scanner(System.in);
    }

    private static void saveData() {
        try (ObjectOutputStream agencyOutputStream = new ObjectOutputStream(new FileOutputStream(AGENCY_FILE));
             ObjectOutputStream userOutputStream = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {

            agencyOutputStream.writeObject(agencies);
            userOutputStream.writeObject(users);

            System.out.println("Data successfully saved to files.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private static void loadData() {
        try (ObjectInputStream agencyInputStream = new ObjectInputStream(new FileInputStream(AGENCY_FILE));
             ObjectInputStream userInputStream = new ObjectInputStream(new FileInputStream(USER_FILE))) {

            agencies = (ArrayList<Agency>) agencyInputStream.readObject();
            users = (ArrayList<User>) userInputStream.readObject();

            System.out.println("Data successfully loaded from files.");
        } catch (FileNotFoundException e) {
            System.out.println("Data files not found. Starting with empty data.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    public static void startApp() {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n==== Main Menu ====");
            System.out.println("[1] Agency Menu");
            System.out.println("[2] User Menu");
            System.out.println("[3] Save Data");
            System.out.println("[4] Load Data");
            System.out.println("[5] Exit");
            System.out.print("Please choose an action: ");

            int chosenAction = Utils.readIntFromUser(1, 5);

            switch (chosenAction) {
                case 1 -> showAgencyMenu();
                case 2 -> showUserMenu();
                case 3 -> saveData();
                case 4 -> loadData();
                case 5 -> {
                    System.out.println("Exiting the application. Goodbye!");
                    isRunning = false;
                }
            }
        }
    }

    public static void showAgencyMenu() {
        boolean showMenu = true;

        while (showMenu) {
            System.out.println("\n==== Agency Menu ====");
            System.out.println("[1] Create an Agency");
            System.out.println("[2] Choose an Agency");
            System.out.println("[3] Back to Main Menu");
            System.out.print("Please choose an action: ");

            int chosenAction = Utils.readIntFromUser(1, 3);

            switch (chosenAction) {
                case 1 -> createAgency();
                case 2 -> chooseAgency();
                case 3 -> {
                    System.out.println("Returning to the Main Menu...");
                    showMenu = false;
                }
            }
        }
    }

    public static void createAgency() {
        System.out.print("\nEnter the name of the new agency: ");
        String newAgencyName = scanner.nextLine();

        if (newAgencyName == null || newAgencyName.trim().isEmpty()) {
            System.out.println("\nAgency name cannot be empty.");
            return;
        }

        boolean agencyExists = Utils.agencyExistsWithName(newAgencyName, agencies);

        while (agencyExists) {
            System.out.print("\nAgency with this name already exists, try a different one: ");
            newAgencyName = scanner.nextLine();

            agencyExists = Utils.agencyExistsWithName(newAgencyName, agencies);
        }

        Agency newAgency = new Agency(newAgencyName);
        agencies.add(newAgency);

        System.out.println("Agency \"" + newAgencyName + "\" has been created successfully.");
    }

    public static void chooseAgency() {
        if (agencies.isEmpty()) {
            System.out.println("\nNo agencies available. Please create an agency first.");
            return;
        }

        int chosenAgencyIndex = Utils.renderSelectListAndChoose("Select an Agency", agencies);
        Agency chosenAgency = agencies.get(chosenAgencyIndex);

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
                    System.out.println("Returning to the Agency Menu...");
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

        System.out.println("\n==== Create a Reservation ====");
        int chosenUserIndex = Utils.renderSelectListAndChoose("Select a User", users);
        User chosenUser = users.get(chosenUserIndex);

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
            System.out.print("Enter the boat type: ");
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

    public static void showUserMenu() {
        boolean showMenu = true;

        while (showMenu) {
            System.out.println("\n==== User Menu ====");
            System.out.println("[1] Create a User");
            System.out.println("[2] Choose a User");
            System.out.println("[3] Back to Main Menu");
            System.out.print("Please choose an action: ");

            int chosenAction = Utils.readIntFromUser(1, 3);

            switch (chosenAction) {
                case 1 -> createUser();
                case 2 -> chooseUser();
                case 3 -> {
                    System.out.println("Returning to the Main Menu...");
                    showMenu = false;
                }
            }
        }
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

        boolean userExists = Utils.userExistsWithEmail(newUserEmail, users);

        while (userExists) {
            System.out.print("\nUser with this email already exists, try a different one: ");
            newUserEmail = scanner.nextLine();

            userExists = Utils.userExistsWithEmail(newUserEmail, users);
        }

        System.out.print("\nEnter the phone number of the new user: ");
        String newUserPhone = scanner.nextLine();

        User newUser = new User(newUsername, newUserEmail, newUserPhone);
        users.add(newUser);

        System.out.println("User \"" + newUsername + "\" has been created successfully.");
    }

    public static void chooseUser() {
        if (users.isEmpty()) {
            System.out.println("\nNo users available. Please create a user first.");
            return;
        }

        int chosenUserIndex = Utils.renderSelectListAndChoose("Select a User", users);
        User chosenUser = users.get(chosenUserIndex);

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

        System.out.println("\n==== Pay a Reservation ====");
        int chosenReservationIndex = Utils.renderSelectListAndChoose("Select a Reservation to Pay", chosenUser.getReservations());
        Reservation chosenReservation = chosenUser.getReservations().get(chosenReservationIndex);

        chosenReservation.setPaid(true);
        System.out.println("Reservation has been marked as paid.");
    }

    public static void cancelUserReservation(User chosenUser) {
        if (chosenUser.getReservations().isEmpty()) {
            System.out.println("\nNo reservations found for this user.");
            return;
        }

        System.out.println("\n==== Cancel a Reservation ====");
        int chosenReservationIndex = Utils.renderSelectListAndChoose("Select a Reservation to Cancel", chosenUser.getReservations());
        Reservation chosenReservation = chosenUser.getReservations().get(chosenReservationIndex);

        chosenReservation.getAgency().removeReservation(chosenReservation);
        chosenUser.removeReservation(chosenReservation);

        System.out.println("Reservation has been canceled successfully.");
    }
}
