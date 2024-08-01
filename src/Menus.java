import java.io.IOException;
import java.util.Scanner;

public class Menus {
    public static void homePage() {
        System.out.println("********** HIV Life Expectancy **********");
        System.out.println("1. Login");
        System.out.println("2. New Registration");
        System.out.println("3. Complete Registration");
        System.out.println("*****************************************");
        System.out.print("> ");
    }

    public static void loginPage(Scanner scanner) throws IOException, InterruptedException {
        System.out.println("**************** Login ****************");
        System.out.print("Enter email > ");
        Credentials.email = scanner.nextLine();
        System.out.println();
        System.out.print("Enter password > ");
        Credentials.password = scanner.nextLine();
        Patient patient = new Patient(Credentials.email, Credentials.password);
        patient.login();
        System.out.println("***************************************");
    }
}
