import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Enter username and password to login:");

        //test
        String[] userItem = new String[]{"victor","miene","admin"};
//        UserManager.addUser(userItem);
        UserManager.findUser("a");
    }
}
