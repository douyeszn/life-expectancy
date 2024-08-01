import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UserManager {
    public static String findUser(String email) throws IOException, InterruptedException {

        String[] cmd = new String[]{"resources/findUser.sh","victor"};

        ProcessBuilder pb = new ProcessBuilder(cmd);
        Process process = pb.start();
        System.out.println("Got here");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String user = reader.readLine();
        System.out.println("user: " + user);
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
            String s = null;
            while((s=reader.readLine())!= null) {
                System.out.println(s);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
