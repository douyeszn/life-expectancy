import java.io.IOException;
import java.util.Scanner;

public class Menus {
    public static void homePage() {
        System.out.println();
        System.out.println("********** HIV Life Expectancy **********");
        System.out.println("1. Login");
        System.out.println("2. New Registration");
        System.out.println("3. Complete Registration");
        System.out.println("*****************************************");
        System.out.print("> ");
    }
    
    public static void loginPage(Scanner scanner) throws IOException, InterruptedException {
        System.out.println();
        System.out.println("**************** Login ****************");
        System.out.print("Enter email > ");
        Credentials.email = scanner.nextLine();
        System.out.println();
        System.out.print("Enter password > ");
        Credentials.password = scanner.nextLine();
        UserManager.login(Credentials.email, Credentials.password);
        System.out.println("***************************************");
    }
    
    public static void newRegistrationPage(Scanner scanner) {
        System.out.println();
        System.out.println("********** Register New User **********");
        System.out.print("Enter New User email > ");
        Credentials.email = scanner.nextLine();
        System.out.println("***************************************");
    }

    public static void completeRegPage(Scanner scanner) {
        System.out.println();
        System.out.println("**************** Complete Registration ****************");
        System.out.print("Enter email > ");
        Credentials.email = scanner.nextLine();
        System.out.println();
        System.out.print("Enter UUID > ");
        Credentials.password = scanner.nextLine();
        // verify if email UUID correct
        System.out.println("***************************************");
    }
}
