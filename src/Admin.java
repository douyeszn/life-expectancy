import java.io.IOException;
import java.util.UUID;

public class Admin extends User {
    public Admin(String firstName, String lastName, String email, String password, Role role) {
        super(firstName, lastName, email, password, role);
    }

    public void initiateReg(String email) throws IOException {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
        String[] user = new String[]{uuid.toString()};
        UserManager.addUser(user);
    }

    public void login() {

    }


}
