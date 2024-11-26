import java.util.ArrayList;
import java.util.Scanner;

public class Utils {
    public static Scanner scanner = new Scanner(System.in);

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
}
