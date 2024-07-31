import java.util.UUID;

public class Admin extends User {
    public Admin(String email, String password, String role) {
        super(email, password, role);
    }

    public String initiateReg(String email){
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
    }

    public void login(String email, String password) {

    }


}
