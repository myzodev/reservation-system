import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;

    public static void main(String[] args) {
        initData();
        startApp();
    }

    private static void initData() {
        Agency agency1 = new Agency("Beyond Horizons");
        Agency agency2 = new Agency("Velvet Voyages");

        agency1.createTrip("Trip to Paris", "France", 10);
        agency1.createTrip("Trip to Krakow", "Poland", 12);

        agency1.createReservation(new User("Miro"), agency1.getAllTrips().getFirst(), 6, false);

        new User("Stefan");
        new User("Milan");

        scanner = new Scanner(System.in);
    }

    private static int readIntFromUser(int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine(); // consume newline
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // consume invalid input
            }
        }
    }

    public static void startApp() {
        boolean isRunning = true;

        while(isRunning) {
            System.out.println("--[ Choose an action ]--");
            System.out.println("[1] Agency menu");
            System.out.println("[2] User menu");
            System.out.println("[3] Exit");

            int chosenAction = readIntFromUser(1, 3);

            switch (chosenAction) {
                case 1 -> showAgencyMenu();
                case 2 -> showUserMenu();
                case 3 -> isRunning = false;
            }
        }
    }

    /**
     * Agency menu
     */
    public static void showAgencyMenu() {
        boolean showMenu = true;

        while(showMenu) {
            System.out.println("--[ Choose an action ]--");
            System.out.println("[1] Create an agency");
            System.out.println("[2] Choose an agency");
            System.out.println("[3] Back to main menu");

            int chosenAction = readIntFromUser(1, 3);

            switch (chosenAction) {
                case 1 -> createAgency();
                case 2 -> chooseAgency();
                case 3 -> showMenu = false;
            }
        }
    }

    public static void createAgency() {
        System.out.println("--[ Name of an agency: ]--");
        String newAgencyName = scanner.nextLine();
        new Agency(newAgencyName);
    }

    public static void chooseAgency() {
        ArrayList<Agency> allAgencies = Agency.getAllAgencies();

        System.out.println("--[ Choose an agency ]--");

        for (int i = 0; i < allAgencies.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + allAgencies.get(i).getName());
        }

        int chosenAgencyIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        Agency chosenAgency = allAgencies.get(chosenAgencyIndex);

        showAgencySubmenu(chosenAgency);
    }

    public static void showAgencySubmenu(Agency chosenAgency) {
        boolean showMenu = true;

        while(showMenu) {
            System.out.println("--[ Choose an action for " + chosenAgency.getName() + " ]--");
            System.out.println("[1] Create a reservation");
            System.out.println("[2] Show reservations");
            System.out.println("[3] Create a trip");
            System.out.println("[4] Show trips");
            System.out.println("[5] Back to agency menu");

            int chosenAction = readIntFromUser(1, 5);

            switch (chosenAction) {
                case 1 -> createAgencyReservation(chosenAgency);
                case 2 -> showAgencyReservations(chosenAgency);
                case 3 -> createAgencyTrip(chosenAgency);
                case 4 -> showAgencyTrips(chosenAgency);
                case 5 -> showMenu = false;
            }
        }
    }

    public static void createAgencyReservation(Agency chosenAgency) {
        ArrayList<User> allUsers = User.getAllUsers();
        ArrayList<Trip> allTrips = chosenAgency.getAllTripsWithFreeSeats();

        System.out.println("--[ Choose a user ]--");

        // Choose a user
        for (int i = 0; i < allUsers.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + allUsers.get(i).getName());
        }

        int chosenUserIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        User chosenUser = allUsers.get(chosenUserIndex);

        // Choose a trip
        System.out.println("--[ Choose a trip from " + chosenAgency.getName() + " ]--");

        for (int i = 0; i < allTrips.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + allTrips.get(i).getName());
        }

        int chosenTripIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        Trip chosenTrip = allTrips.get(chosenTripIndex);

        // Choose amount of passengers
        System.out.println("--[ How many of you are going? (Free seats: " + chosenTrip.getFreeSeats() + " ]--");

        int numberOfPassengers = scanner.nextInt();
        scanner.nextLine();

        // Choose a trip
        System.out.println("--[ Would you like to pay now? (Y/n) ]--");
        String payNow = scanner.nextLine();

        boolean isReservationPaid = payNow.equals("Y");

        chosenAgency.createReservation(chosenUser, chosenTrip, numberOfPassengers, isReservationPaid);
    }

    public static void showAgencyReservations(Agency chosenAgency) {
        System.out.println("--[ Reservations from " + chosenAgency.getName() + " agency ]--");

        for (Reservation reservation : chosenAgency.getAllReservations()) {
            System.out.println(reservation);
        }
    }

    public static void createAgencyTrip(Agency chosenAgency) {
        // Name
        System.out.println("--[ Trip name ]--");
        String newTripName = scanner.nextLine();

        // Destination
        System.out.println("--[ Trip destination ]--");
        String newTripDestination = scanner.nextLine();

        // Free seats
        System.out.println("--[ How many free seats ]--");
        int newTripSeats = scanner.nextInt();
        scanner.nextLine();

        chosenAgency.createTrip(newTripName, newTripDestination, newTripSeats);
    }

    public static void showAgencyTrips(Agency chosenAgency) {
        System.out.println("--[ Trips from " + chosenAgency.getName() + " agency ]--");

        for (Trip trip : chosenAgency.getAllTrips()) {
            System.out.println(trip);
        }
    }

    /**
     * User Menu
     */
    public static void showUserMenu() {
        boolean showMenu = true;

        while(showMenu) {
            System.out.println("--[ Choose an action ]--");
            System.out.println("[1] Create a user");
            System.out.println("[2] Choose a user");
            System.out.println("[3] Back to main menu");

            int chosenAction = readIntFromUser(1, 3);

            switch (chosenAction) {
                case 1 -> createUser();
                case 2 -> chooseUser();
                case 3 -> showMenu = false;
            }
        }
    }

    public static void createUser() {
        System.out.println("--[ User name: ]--");
        String newUsername = scanner.nextLine();
        new User(newUsername);
    }

    public static void chooseUser() {
        ArrayList<User> allUsers = User.getAllUsers();

        System.out.println("--[ Choose a user ]--");

        for (int i = 0; i < allUsers.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + allUsers.get(i).getName());
        }

        int chosenUserIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        User chosenUser = allUsers.get(chosenUserIndex);

        showUserSubmenu(chosenUser);
    }

    public static void showUserSubmenu(User chosenUser) {
        boolean showMenu = true;

        while(showMenu) {
            System.out.println("--[ Choose an action for " + chosenUser.getName() + " ]--");
            System.out.println("[1] Show reservations");
            System.out.println("[2] Pay a reservation");
            System.out.println("[3] Cancel a reservation");
            System.out.println("[4] Back to user menu");

            int chosenAction = readIntFromUser(1, 4);

            switch (chosenAction) {
                case 1 -> showUserReservations(chosenUser);
                case 2 -> payUserReservations(chosenUser);
                case 3 -> cancelUserReservations(chosenUser);
                case 4 -> showMenu = false;
            }
        }
    }

    public static void showUserReservations(User chosenUser) {
        for (Reservation reservation : chosenUser.getReservations()) {
            System.out.println(reservation);
        }
    }

    public static void payUserReservations(User chosenUser) {
        ArrayList<Reservation> userReservations = chosenUser.getReservations();

        System.out.println("--[ Choose a reservation you want to pay ]--");

        for (int i = 0; i < userReservations.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + userReservations.get(i));
        }

        int chosenReservationIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        Reservation chosenReservation = userReservations.get(chosenReservationIndex);
        chosenReservation.setPaid(true);
    }

    public static void cancelUserReservations(User chosenUser) {
        ArrayList<Reservation> userReservations = chosenUser.getReservations();

        System.out.println("--[ Choose a reservation you want to cancel ]--");

        for (int i = 0; i < userReservations.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + userReservations.get(i));
        }

        int chosenReservationIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        Reservation chosenReservation = userReservations.get(chosenReservationIndex);
        chosenUser.removeReservation(chosenReservation);
    }
}
