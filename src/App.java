import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("running");


        while (true) {
            Menus.homePage();

            String input = scanner.nextLine();
            if (input.equals("q")) {
                break;
            }

            int option;
            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (option) {
                case 1:
                    Menus.loginPage(scanner);
                    break;
                case 2:
                    // Handle new registration
                    System.out.println("New registration feature not implemented yet.");
                    break;
                case 3:
                    // Handle complete registration
                    System.out.println("Complete registration feature not implemented yet.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }

        scanner.close();

    }
}