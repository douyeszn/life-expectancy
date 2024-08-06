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

            User user = null;
            switch (option) {
                case 1:
                    user = Menus.loginPage(scanner, Role.PATIENT);
                    break;
                case 2:
                    user = Menus.loginPage(scanner, Role.ADMIN);
                  break;

                case 3:
                    Menus.completeRegPage(scanner);
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    return;
            }


            if(user != null){
                Role loggedInRole = user.getRole();
                System.out.println("Role: " + loggedInRole.toString());
                if (loggedInRole == Role.ADMIN) {
                    System.out.println();
                    System.out.println("********** Admin Menu - Choose option **********");
                    System.out.println("1. New Registration");
                    System.out.println("*****************************************");
                    System.out.print("> ");

                    int adminOption = scanner.nextInt();
                    scanner.nextLine();

                    if (adminOption == 1) {
                        Menus.newRegistrationPage(scanner, (Admin) user);
                    } else {
                        System.out.println("Invalid option. Please try again.");
                    }
                } else if (loggedInRole == Role.PATIENT) {
                    System.out.println("Logged in as Patient. No additional options available.");
                }
            }



        }

        scanner.close();

    }
}