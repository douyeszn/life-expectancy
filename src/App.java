import java.io.IOException;
import java.util.Map;
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
                    if(role == null) {
                        System.out.println("user not found");
                        Menus.loginPage(scanner);
                    }
                    else if(role.equals(Role.ADMIN)){
                        Admin admin = new Admin(
                            User.getDataField(userData, DataStructure.email.getValue()),
                            User.getDataField(userData, DataStructure.password.getValue())
                            );
                        Menus.adminPage(scanner, admin);
                    }else if(role.equals(Role.PATIENT)){
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
                    }else{
                        System.out.println("============+++++");
                    }
                    break;
                case 2:
                    if(Menus.completeRegPage(scanner) == true) {
                        Menus.loginPage(scanner);
                    }else{
                        System.out.println("Registrations failed, try again");
                        Menus.completeRegPage(scanner);
                    }
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    Menus.loginPage(scanner);
                    return;
            }
        }
        scanner.close();
    }
}
