import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserManager {
    public static void completeRegistration(
            String uuid,
            String firstName,
            String lastName,
            String dateOfBirth,
            boolean isHIVPositive,
            boolean onARTMedication,
            String countryISO,
            String startARTDate,
            String email,
            String password,
            String diagnosisDate) throws IOException {

    String[] userDetails = new String[]{
                uuid,
                firstName,
                lastName,
                email,
                password,
                Role.PATIENT.toString(),
                dateOfBirth,
                countryISO,
                Boolean.toString(isHIVPositive),
                diagnosisDate,
                Boolean.toString(onARTMedication),
                startARTDate
        };

        UserManager.addUser(userDetails);
    }

    public static void login(String email, String password) throws IOException, InterruptedException {
        String[] cmd = new String[]{"resources/login.sh","admin@andrew.cmu.edu", "12345"};
        ProcessBuilder pb = new ProcessBuilder(cmd);
        Process process = pb.start();

        process.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String user;
        while((user=reader.readLine())!= null) {
            System.out.println(user);
        }
    }


    public static String findUser(String email) throws IOException, InterruptedException {

        String[] cmd = new String[]{"resources/findUser.sh","Doe"};
        ProcessBuilder pb = new ProcessBuilder(cmd);
        Process process = pb.start();

        process.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String user;
        while((user=reader.readLine())!= null) {
            System.out.println(user);
        }
        return user;
    }

    public static void addUser(String[] userString) throws IOException {
      String[] cmd = new String[]{"resources/addUser.sh"};
        List<String> listOfArgs = new ArrayList<>();
        listOfArgs.add("resources/addUser.sh");

        listOfArgs.addAll(Arrays.asList(userString));
        String[] args = listOfArgs.toArray(new String[0]);
        ProcessBuilder pb = new ProcessBuilder(args);
        try{
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader( process.getInputStream()));
            String s;
            while((s=reader.readLine())!= null) {
                System.out.println(s);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
