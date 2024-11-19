package zeebe.cloud;

import java.util.Scanner;

public class Utility {

    public static void waitUntilSystemInput(String exitCommand) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase(exitCommand)) {
                break;
            }
        }
    }
}
