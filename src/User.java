import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class User {
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;
    protected Role role;

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
            // int exitCode = process.waitFor();

            // if (exitCode == 1) {
            //     System.out.println("login failed");
            // }
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            if((userData = reader.readLine()) == null){
                return null;
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            Utils.pause(1);
        }
        return userData;
    }
    
    public static String getDataField(String data, int field){
        String userData = "";
        String[] cmd = new String[]{"resource/getUserData.sh", data, Integer.toString(field)};
        ProcessBuilder pb = new ProcessBuilder(cmd);
        try {
            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode == 1) {
                System.out.println("Failed to get user role");
                Utils.pause(1);
            }else{
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                userData = reader.readLine();
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
            Utils.pause(1);
        }
        return userData;
    }

    // get role from login info
    public static Role getRole(String data) {
        String role = getDataField(data, DataStructure.role.getValue());
    
        
        String roleString = role.toUpperCase();
        
        if (roleString.equals(Role.ADMIN.toString())) {
            return Role.ADMIN;
        } else if (roleString.equals(Role.PATIENT.toString())) {
            return Role.PATIENT;
        }else{
            return null;
        }
    }

    public static boolean updateDataField(String file, String uuid, String newContent, int position) {
        try {
            // Define the command to call the shell script
            String[] cmd = new String[]{"resource/updateUserData.sh", file, uuid, newContent, Integer.toString(position)};
            ProcessBuilder pb = new ProcessBuilder(cmd);

            // Start the process
            Process process = pb.start();

            // Read the output and error streams
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.err.println(errorLine);
            }

            // Wait for the process to complete and get the exit code
            int exitCode = process.waitFor();

            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            System.err.println("Error executing script: " + e.getMessage());
            Utils.pause(1);
            return false;
        }
    }

    public static Boolean findUser(String UUID){
        String user = null;
        try {
            String[] cmd = new String[]{"resource/findUser.sh", UUID};
            ProcessBuilder pb = new ProcessBuilder(cmd);
            Process process = pb.start();

            process.waitFor();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            user = reader.readLine();  // Read the first (and only) line of output

            if (user != null) {
                return true;
            }

            return false;
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            Utils.pause(1);
        }
        return false;
    }
}
