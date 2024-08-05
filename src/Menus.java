import java.io.IOException;
import java.util.Scanner;

public class Menus {
    public static void homePage() {
        System.out.println();
        System.out.println("********** HIV Life Expectancy **********");
        System.out.println("1. Login as Patient");
        System.out.println("2. Login as Admin");
        System.out.println("3. Complete Registration");
        System.out.println("*****************************************");
        System.out.print("> ");
    }
    
    public static User loginPage(Scanner scanner, Role role) throws IOException, InterruptedException {
        System.out.println();
        System.out.println("**************** Login ****************");
        System.out.print("Enter email > ");
        Credentials.email = scanner.nextLine();
        System.out.println();
        System.out.print("Enter password > ");
        Credentials.password = scanner.nextLine();
        User user;
        if(Role.ADMIN.equals(role)){
            Admin admin = new Admin(Credentials.email, Credentials.password);
            boolean isLoggedIn = admin.login();
            if(!isLoggedIn){
                return null;
            }
            user = admin;
        }else {
            Patient patient = new Patient(Credentials.email, Credentials.password);
            boolean isLoggedIn = patient.login();
            if(!isLoggedIn){
                return null;
            }
            user = patient;

        }
        System.out.println("***************************************");
        return user;
    }

    public static void newRegistrationPage(Scanner scanner, Admin admin) {
        System.out.println();
        System.out.println("********** Register New User **********");
        System.out.print("Enter New User email > ");
        Credentials.email = scanner.nextLine();
        admin.initiateReg(Credentials.email);

        System.out.println("***************************************");
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
