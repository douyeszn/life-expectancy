import java.io.IOException;
import java.util.Scanner;

public class Menus {
    public static void homePage() {
        System.out.println();
        System.out.println("********** HIV Life Expectancy **********");
        System.out.println("1. Login");
        System.out.println("2. Complete Registration");
        System.out.println("*****************************************");
        System.out.print("> ");
    }
    
    public static Role loginPage(Scanner scanner) throws IOException, InterruptedException {
        System.out.println();
        System.out.println("**************** Login ****************");
        System.out.print("Enter email > ");
        Credentials.email = scanner.nextLine();
        System.out.print("Enter password > ");
        Credentials.password = scanner.nextLine();
        System.out.println("***************************************");
        String data = User.login(Credentials.email, Credentials.password);
        System.out.println(data);
        Role role = User.getRole(data);
        return role;
    }

    public static void newRegistrationPage(Scanner scanner, Admin admin) {
        // Admin admin = new Admin(null, null);
        System.out.println();
        System.out.println("********** Register New User **********");
        System.out.print("Enter New User email > ");
        Credentials.email = scanner.nextLine();
        // admin.initiateReg(Credentials.email);
        admin.newPatientReg();
        System.out.println("***************************************");
    }
    public static void adminPage(Scanner scanner, Admin admin){
        System.out.println();
        System.out.println("********** Admin Menu - Choose option **********");
        System.out.println("1. Register New user");
        System.out.println("2. Register New Admin");
        System.out.println("3. Export data");
        System.out.println("************************************************");
        System.out.print("> ");
        int adminOption = scanner.nextInt();
        scanner.nextLine();
        switch (adminOption) {
            case 1:
                newRegistrationPage(scanner, admin);
                break;
            case 2:
                break;
            case 3:
                // export user date, export admindata
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
}
    public static void completeRegPage(Scanner scanner) {
        System.out.println();
        System.out.println("**************** Complete Registration ****************");
        System.out.print("Enter UUID > ");
        String uuid = scanner.nextLine();
        Boolean isUser = UserManager.findUser(uuid);
        if (isUser) {
            System.out.print("Enter Date of Birth (YYYY-MM-DD) > ");
            String dateOfBirth = scanner.nextLine();

            System.out.print("Is the user HIV Positive? (true/false) > ");
            boolean isHIVPositive = Boolean.parseBoolean(scanner.nextLine());

            String diagnosisDate = "";
            if (isHIVPositive) {
                System.out.print("Enter Diagnosis Date (YYYY-MM-DD) > ");
                diagnosisDate = scanner.nextLine();
            }

            System.out.print("Is the user on ART Medication? (true/false) > ");
            boolean onARTMedication = Boolean.parseBoolean(scanner.nextLine());

            String startARTDate = null;
            if (onARTMedication) {
                System.out.print("Enter Start ART Date (YYYY-MM-DD) > ");
                startARTDate = scanner.nextLine();
            }

            System.out.print("Enter Country ISO Code > ");
            String countryISO = scanner.nextLine();

            UserManager.completeRegistration(
                    uuid,
                    "John",
                    "Doe",
                    dateOfBirth,
                    isHIVPositive,
                    onARTMedication,
                    countryISO,
                    startARTDate,
                    Credentials.email,
                    "1234",
                    diagnosisDate

            );

            System.out.println("Registration complete!");
        } else {
            System.out.println("User does not exist.");
        }

        System.out.println("***************************************");
    }
}
