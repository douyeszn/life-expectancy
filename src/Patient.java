import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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

    public int calculateYearsBetweenDates(String startDateStr, String endDateStr, String dateFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);

        Period period = Period.between(startDate, endDate);
        return period.getYears();
    }

    public int calculateLifeSpan(){
        String countryISO = this.getCountryISOcode();
        boolean isHIVPositive = this.isHIVPositive();
        String diagnosisDate = this.getHivDiagnosisDate();
        String startARTDate = this.getStartARTDate();

        String dateOfBirth = this.getDateOfBirth();
        LocalDate dob = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int age = Period.between(dob, LocalDate.now()).getYears();

        int yearsDelayedBeforeART = calculateYearsBetweenDates(diagnosisDate, startARTDate, "yyyy-MM-dd");

        String[] cmd = {
                "bash",
                "resource/calculateLifeSpan.sh",
                String.valueOf(isHIVPositive),
                String.valueOf(age),
                String.valueOf(yearsDelayedBeforeART),
                countryISO,
        };
        ProcessBuilder pb = new ProcessBuilder(cmd);
        try {
            Process process = pb.start();
            int exitCode = process.waitFor();

            BufferedReader stdOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            StringBuilder outputBuilder = new StringBuilder();
            String line;
            while ((line = stdOutput.readLine()) != null) {
                outputBuilder.append(line);
            }

            if (exitCode != 0) {
                while ((line = stdError.readLine()) != null) {
                    System.err.println(line);
                }
                throw new RuntimeException("Script execution failed with exit code " + exitCode);
            }

            String lifespan = outputBuilder.toString().trim();
            double lifespanDouble = Double.parseDouble(lifespan);
            return (int) Math.round(lifespanDouble);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return 0;
    }

    public String getCountryISOcode() {
        return countryISOcode;
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
