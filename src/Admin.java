import java.util.UUID;

public class Admin extends User {
    public Admin(String email, String password, Role role) {
        super(email, password, role);
    }

    public void initiateReg(String email){
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
    }

    public void login(String email, String password) {

    }


}
