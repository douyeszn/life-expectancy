import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.UUID;


public class Admin extends User {
    public Admin(String email, String password) {
        super(null, null, email, password);
    }

    public boolean newPatientReg(){
        try {
            UUID uuid = UUID.randomUUID();
            if(uuid != null){
                System.out.println("---------------------------------------");
                System.out.println("Patient UUID: " + uuid.toString());
                return true;
            }
        } catch (Exception e) {
            System.err.println("Registration failed" + e.getMessage());
            return false;
        }
        return false;
    }

    public void addDataField(String data, int field){
        String[] cmd = new String[]{"resource/addUserData.sh", data, Integer.toString(field)};
        ProcessBuilder pb = new ProcessBuilder(cmd);
        try {
            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode == 1) {
                System.out.println("Failed to add data");
            }else{
                System.out.println("Added data successfully");
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}
