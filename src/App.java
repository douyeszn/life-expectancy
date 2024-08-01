import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Enter username and password to login:");

        //test
          String[] userItem = new String[]{"victor","miene","admin"};
          UserManager.completeRegistration(
                  "4174000",
                  "John",
                  "Doe",
                  "1990-01-01",
                  true,
                  false,
                  "US",
                  "2024-01-01",
                  "securePassword123"
          );
        //
        //

//        UserManager.findUser("a");
    }
}
