import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
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
                    String userData;
                    Role role;
                    userData = Menus.loginPage(scanner);
                    role = User.getRole(userData);
                    if(role.equals(Role.ADMIN)){
                        Admin admin = new Admin(
                            User.getDataField(userData, DataStructure.email.getValue()),
                            User.getDataField(userData, DataStructure.password.getValue())
                            );
                        // User.updateDataField("user-store.txt", "001", "hiro", 3);
                        Menus.adminPage(scanner, admin);
                    }else if(role.equals(Role.PATIENT)){

                    }
                    break;
                case 2:
                    if(Menus.completeRegPage(scanner) == true) {
                        Menus.loginPage(scanner);
                    }else{
                        Menus.completeRegPage(scanner);
                    }

                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    return;
            }
        }
        scanner.close();
    }
}
