import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Enter username and password to login:");

        //test
        String[] userItem = new String[]{"victor","miene","admin"};
//        UserManager.addUser(userItem);
        UserManager.findUser("a");
    }
    
        public static int homePage() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("********** HIV Life Expectancy **********");
        System.out.println("1. Login");
        System.out.println("2. New Registration");
        System.out.println("3. Complete Registration");
        System.out.println("*****************************************");
        int input = scanner.nextInt();
        scanner.close();
        return input;
    }

    public static void loginPage() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("**************** Login ****************");
        System.out.print("Enter email > ");
        Credentials.email = scanner.nextLine();
        System.out.println();
        System.out.print("Enter password > ");
        Credentials.password = scanner.nextLine();
        System.out.println("***************************************");
        scanner.close();
    }
}
