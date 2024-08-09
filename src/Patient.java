import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

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

    public static boolean completeRegistration(
            String uuid,
            String firstName,
            String lastName,
            String password,
            String dateOfBirth,
            String countryISO,
            boolean isHIVPositive,
            String diagnosisDate,
            boolean onARTMed,
            String startARTDate) {

        try {
            // Define the data to be updated
            String[] dataFields = {
                    uuid, // UUID remains the same
                    firstName,
                    lastName,
                    "null",
                    password,
                    "null",
                    dateOfBirth,
                    countryISO,
                    Boolean.toString(isHIVPositive),
                    diagnosisDate,
                    Boolean.toString(onARTMed),
                    startARTDate,
                    "null",
            };

            // Update each field using DataStructure enum
            for (DataStructure field : DataStructure.values()) {
                // Skip UUID
                if (field == DataStructure.UUID) continue;

                // Retrieve the position and corresponding data
                int position = field.getValue();
                String data = dataFields[position - 1];

                boolean success = User.updateDataField("user-store.txt", uuid, data, position);
                if (!success) {
                    System.err.println("Failed to update field at position " + position);
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            System.err.println("An error occurred during registration: " + e.getMessage());
            return false;
        }
    }

    public void displayPatientInfo(String data) {
        String format = "%-20s: %-30s%n";

        // Assuming User is the class that contains the static method getDataField
        System.out.println("********** Patient Details **********");
        System.out.printf(format, "UUID", User.getDataField(data, DataStructure.UUID.getValue()));
        System.out.printf(format, "Name", User.getDataField(data, DataStructure.firstName.getValue()) + " " + User.getDataField(data, DataStructure.lastName.getValue()));
        System.out.printf(format, "Date of Birth", User.getDataField(data, DataStructure.dateofBirth.getValue()));
        System.out.printf(format, "Country", User.getDataField(data, DataStructure.countryISO.getValue()));
        System.out.printf(format, "HIV Positive", User.getDataField(data, DataStructure.isHIVPositive.getValue()).equals("true") ? "Yes" : "No");
        System.out.printf(format, "Diagnosis Date", User.getDataField(data, DataStructure.DiagnosisDate.getValue()));
        System.out.printf(format, "On ART Medication", User.getDataField(data, DataStructure.onARTMed.getValue()).equals("true") ? "Yes" : "No");
        System.out.printf(format, "Start ART Date", User.getDataField(data, DataStructure.startARTDate.getValue()));
        System.out.printf(format, "Days to live", User.getDataField(data, DataStructure.daysToLive.getValue()));
        System.out.println("**************************************");
        System.out.println("0. Logout 1. Update data");
    }

}
