import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Patient extends User{
    private String dateOfBirth;
    private String countryISOcode;
    private boolean isHIVPositive;
    private String hivDiagnosisDate;
    private boolean onARTMedication;
    private String startARTDate;

     public Patient(String email, String password) {
         super(null,null, email, password);
     }

    public Patient(
            String email,
            String password,
            String dateOfBirth,
            String countryISOcode,
            boolean isHIVPositive,
            String hivDiagnosisDate,
            boolean onARTMedication,
            String startARTDate){
        super(null, null, email, password);
        this.dateOfBirth = dateOfBirth;
        this.countryISOcode = countryISOcode;
        this.isHIVPositive = isHIVPositive;
        this.hivDiagnosisDate = hivDiagnosisDate;
        this.onARTMedication = onARTMedication;
        this.startARTDate = startARTDate;
    }

    public int calculateLifeSpan(){
//        double countryLifeSpan = getCountryLifeSpan(this.countryISOcode);
        String[] cmd = new String[]{"resource/calculateLifeSpan.sh"};
        ProcessBuilder pb = new ProcessBuilder(cmd);
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
//                System.out.println("User export failed with");
                while ((s = stdError.readLine()) != null) {
                    System.err.println(s);
                }
            } else {
                System.out.println("----------." + s);
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return 10;
    }

    public String getCountryISOcode() {
        return countryISOcode;
    }

    public void setCountryISOcode(String countryISOcode) {
        this.countryISOcode = countryISOcode;
    }

    public boolean isHIVPositive() {
        return isHIVPositive;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getHivDiagnosisDate() {
        return hivDiagnosisDate;
    }

    public String getStartARTDate() {
        return startARTDate;
    }

    public boolean isOnARTMedication() {
        return onARTMedication;
    }
}
