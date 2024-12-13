package com.travelagencies;

import com.travelagencies.helpers.*;
import com.travelagencies.agencies.AgencyController;
import com.travelagencies.users.UserController;

public class Main {
    public static void main(String[] args) {
        DataManager.loadDummyData();
        startApp();
    }

    public static void startApp() {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n==== Main Menu ====");
            System.out.println("[1] List all trips");
            System.out.println("[2] Agency Menu");
            System.out.println("[3] User Menu");
            System.out.println("[4] Save Data");
            System.out.println("[5] Load Data");
            System.out.println("[6] Exit");
            System.out.print("Please choose an action: ");

            int chosenAction = Utils.readIntFromUser(1, 6);

            switch (chosenAction) {
                case 1 -> AgencyController.showAllTrips();
                case 2 -> AgencyController.showAgencyMenu();
                case 3 -> UserController.showUserMenu();
                case 4 -> DataManager.saveData();
                case 5 -> DataManager.loadData();
                case 6-> {
                    System.out.println("\nExiting the application. Goodbye!");
                    isRunning = false;
                }
            }
        }
    }
}
