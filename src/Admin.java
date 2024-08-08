import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class Admin extends User {
    public Admin(String email, String password) {
        super(null, null, email, password);
    }

    public static void addUser(String[] userData) throws IOException {
    //   String[] cmd = new String[]{"resources/addUser.sh"};
        List<String> listOfArgs = new ArrayList<>();
        listOfArgs.add("resource/addUser.sh");

        listOfArgs.addAll(Arrays.asList(userData));
        String[] args = listOfArgs.toArray(new String[0]);
        ProcessBuilder pb = new ProcessBuilder(args);
        try{
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader( process.getInputStream()));
            String s;
            while((s=reader.readLine())!= null){
                System.out.println(s);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean newPatientReg(String email){
        try {
            UUID uuid = UUID.randomUUID();
            if(uuid != null){
                System.out.println("---------------------------------------");
                System.out.println("Patient UUID: " + uuid.toString());
                String[] data = new String[] {
                    uuid.toString(),
                    "null",
                    "null",
                    email,
                    "null",
                    Role.PATIENT.toString(),
                    "null",
                    "null",
                    "null",
                    "null",
                    "null",
                    "null",
                    "null"};
                Admin.addUser(data);
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

    public void downloadUsers(){
        String[] cmd = new String[]{"resource/exportUsers.sh"};
        ProcessBuilder pb = new ProcessBuilder(cmd);
//        pb.directory(new File("path/to/correct/directory"));
        try {
            Process process = pb.start();
            int exitCode = process.waitFor();

            BufferedReader stdOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String s;
            while ((s = stdOutput.readLine()) != null) {
                System.out.println(s);
            }

            if (exitCode != 0) {
                System.out.println("User export failed with");
                while ((s = stdError.readLine()) != null) {
                    System.err.println(s);
                }
            } else {
                System.out.println("Users have been exported successfully.");
            }

//            if (exitCode == 1) {
//                System.out.println("Users have been exported.");
//            }else{
//                System.out.println("User export failed.");
//            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}
