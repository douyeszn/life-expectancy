import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManager {

    public static void appendToFile(String[] userString){
        String[] cmd = new String[]{"resources/test.sh"};
        List<String> listOfArgs = new ArrayList<>();
        listOfArgs.add("resources/test.sh");

        listOfArgs.addAll(Arrays.asList(userString));
        String[] args = listOfArgs.toArray(new String[0]);
        ProcessBuilder pb = new ProcessBuilder(args);
        try{
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader( p.getInputStream()));
            String s = null;
            while((s=reader.readLine())!= null) {
                System.out.println(s);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
