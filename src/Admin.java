import java.util.UUID;


public class Admin extends User {
    public Admin(String email, String password) {
        super(null, null, email, password, Role.ADMIN);
    }

    public void initiateReg(String email)  {
        try {
            UUID uuid = UUID.randomUUID();
            System.out.println("Patient UUID: " + uuid.toString());
            String[] user = new String[]{uuid.toString(), " ", " ",email};
            UserManager.addUser(user);
        } catch (Exception e) {

            System.err.println("Registration failed" + e.getMessage());
        }
    }

    public void login(){
        UserManager.login(this.email, this.password, Role.ADMIN);
    }


}
