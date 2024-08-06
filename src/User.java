import java.io.BufferedReader;
import java.io.InputStreamReader;

public abstract class User {
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;
    protected Role role;

    public User(String firstName, String lastName, String email, String password, Role role){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public User(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public static String login(String email, String password) {
        String[] cmd = new String[]{"resource/login.sh", email, password};
        ProcessBuilder pb = new ProcessBuilder(cmd);
        String userData = "";

        try {
            Process process = pb.start();
            int exitCode = process.waitFor();

            // if (exitCode == 1) {
            //     System.out.println("login failed");
            //     break;
            // }
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            if((userData = reader.readLine()) != null){
                return userData;
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        return userData;
    }
    // get role from login info
    public static Role getRole(String user){
        String role = "";
        String[] cmd = new String[]{"resource/getUserRole.sh", user};
        ProcessBuilder pb = new ProcessBuilder(cmd);
        try {
            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode == 1) {
                System.out.println("Failed to get user role");
            }else{
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                role = reader.readLine();
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        String roleString = role.toUpperCase();
    
        if (roleString.equals(Role.ADMIN.toString())) {
            return Role.ADMIN;
        } else if (roleString.equals("PATIENT")) {
            return Role.PATIENT;
        } else {
            System.out.println(Role.ADMIN.toString());
            System.out.println("Invalid role found: " + role);
            return null;  // or throw an exception or handle the error as appropriate
        }
    }

    // public Role getRole(){
    //     return this.role;
    // }
}
