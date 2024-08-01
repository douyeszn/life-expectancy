import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

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
            String password) throws IOException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy\"");

        String[] userParams = new String[]{
                uuid,
                firstName,
                lastName,
                Role.PATIENT.toString(),
              dateOfBirth,
                Boolean.toString(isHIVPositive),
                Boolean.toString(onARTMedication),
                countryISO,
                startARTDate,
                password
        };

        UserManager.addUser(userParams);
    }


    public static String findUser(String email) throws IOException, InterruptedException {

        String[] cmd = new String[]{"resources/findUser.sh","victor"};
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
