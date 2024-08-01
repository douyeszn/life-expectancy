import java.io.IOException;
import java.util.UUID;

public class Admin extends User {
    public Admin(String email, String password) {
        super(null, null, email, password, Role.ADMIN);
    }

    public void initiateReg(String email) throws IOException {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
        String[] user = new String[]{uuid.toString()};
        UserManager.addUser(user);
    }

    public void login(){
        UserManager.login(this.email, this.password, Role.ADMIN);
    }


}
