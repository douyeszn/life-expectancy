import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int option = -1;
    
        while (option != 0) {
            Menus.homePage();
            String input = scanner.nextLine();
    
            try {
                option = Integer.parseInt(input);
                switch (option) {
                    case 1:
                        handleLogin(scanner);
                        break;
                    case 2:
                        handleRegistration(scanner);
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        Utils.pause(1);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        Utils.pause(1);
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                Utils.pause(1);
            }
        }
    
        scanner.close();
    }
    
    private static void handleLogin(Scanner scanner) {
        String userData = Menus.loginPage(scanner);
        Role role = User.getRole(userData);
    
        if (role == null) {
            System.out.println("User not found");
            Utils.pause(1);
        } else if (role.equals(Role.ADMIN)) {
            Admin admin = new Admin(
                User.getDataField(userData, DataStructure.email.getValue()),
                User.getDataField(userData, DataStructure.password.getValue())
            );
            Menus.adminPage(scanner, admin);
        } else if (role.equals(Role.PATIENT)) {
            Map<String, String> userMap = new UserMap(userData).getUserMap();
            Patient patient = new Patient(
                userMap.get("email"),
                userMap.get("password"),
                userMap.get("dateOfBirth"),
                userMap.get("countryISO"),
                Boolean.parseBoolean(userMap.get("isHIVPositive")),
                userMap.get("DiagnosisDate"),
                Boolean.parseBoolean(userMap.get("onARTMedication")),
                userMap.get("startARTDate")
            );
            Menus.patientPage(scanner, userData, patient);
        } else {
            System.out.println("Invalid User");
        }
    }
    
    private static void handleRegistration(Scanner scanner) {
        if (!Menus.completeRegPage(scanner)) {
            System.out.println("Registration failed, try again.");
            Utils.pause(1);
        }
    }
}