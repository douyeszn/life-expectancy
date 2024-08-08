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
            String diagnosisDate){
                try{
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
                }catch (Exception e) {
                    System.err.println("An error occurred: " + e.getMessage());
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
        }

        return false;
    }

    public static void addUser(String[] userString) throws IOException {
      String[] cmd = new String[]{"resources/addUser.sh"};
        List<String> listOfArgs = new ArrayList<>();
        listOfArgs.add("resource/addUser.sh");

        listOfArgs.addAll(Arrays.asList(userString));
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

}
