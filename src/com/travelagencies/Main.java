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
            System.out.println("[1] Agency Menu");
            System.out.println("[2] User Menu");
            System.out.println("[3] Save Data");
            System.out.println("[4] Load Data");
            System.out.println("[5] Exit");
            System.out.print("Please choose an action: ");

            int chosenAction = Utils.readIntFromUser(1, 5);

            switch (chosenAction) {
                case 1 -> AgencyController.showAgencyMenu();
                case 2 -> UserController.showUserMenu();
                case 3 -> DataManager.saveData();
                case 4 -> DataManager.loadData();
                case 5 -> {
                    System.out.println("\nExiting the application. Goodbye!");
                    isRunning = false;
                }
            }
        }
    }
}
