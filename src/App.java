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
                    Menus.completeRegPage(scanner);
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    return;
            }
        }
        scanner.close();
    }
}
