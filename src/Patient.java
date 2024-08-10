import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        // Validate that both date strings are provided
        if (startDateStr == null || endDateStr == null || dateFormat == null) {
            System.out.println("Error: One or more input values are null.");
            return 0;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        LocalDate startDate;
        LocalDate endDate;

        try {
            startDate = LocalDate.parse(startDateStr, formatter);
            endDate = LocalDate.parse(endDateStr, formatter);
        } catch (DateTimeParseException e) {
            // System.out.println("Error: Invalid date format or date value.");
            return 0;
        }

        if (startDate.isAfter(endDate)) {
            // System.out.println("Error: The end date must be after the start date.");
            return 0;
        }

        Period period = Period.between(startDate, endDate);
        return period.getYears();
    }

    public double getLifeExpectancy(String iso){
        double lifespanDouble = 0.0;
        String[] cmd = {
            "bash",
            "resource/getLifeExpectancy.sh",
            iso,
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
            lifespanDouble = Double.parseDouble(lifespan);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return lifespanDouble;
    }

    public int calculateLifeSpan() {
        String countryISO = this.getCountryISOcode();
        double lifeExpectancy = getLifeExpectancy(countryISO);
        boolean isHIVPositive = this.isHIVPositive();
        boolean onARTMed = this.onARTMed();
        String diagnosisDate = this.getHivDiagnosisDate();
        String startARTDate = this.getStartARTDate();

        String dateOfBirth = this.getDateOfBirth();
        LocalDate dob = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int age = Period.between(dob, LocalDate.now()).getYears();

        int yearsDelayedBeforeART = calculateYearsBetweenDates(diagnosisDate, startARTDate, "yyyy-MM-dd");

        if (!isHIVPositive) {
            return (int) lifeExpectancy - age;
        }

        if (isHIVPositive && onARTMed) {
            // Calculate the remaining lifespan if the person is on ART drugs
            LocalDate diagnosisLocalDate = LocalDate.parse(diagnosisDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate artStartDate = LocalDate.parse(startARTDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int yearsBetweenDiagnosisAndART = Period.between(diagnosisLocalDate, artStartDate).getYears();
            
            double remainingLifespan = (lifeExpectancy - age - yearsDelayedBeforeART) * 0.90;
            
            for (int i = 0; i < yearsDelayedBeforeART; i++) {
                remainingLifespan *= 0.90; // Reduce by 10% for each year of delay
            }

            return (int) remainingLifespan;
        } else if (isHIVPositive && !onARTMed) {
            return 5 - yearsDelayedBeforeART; // 5 years survival time after diagnosis
        }

        // Default return value (should not reach here)
        return 0;
    }

    public String getCountryISOcode() {
        return countryISOcode;
    }

    public boolean isHIVPositive() {
        return isHIVPositive;
    }
    
    public boolean onARTMed() {
        return onARTMedication;
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
        System.out.printf(format, "Years to live", this.calculateLifeSpan());
        System.out.println("**************************************");
        User.updateDataField("user-store.txt", User.getDataField(data, DataStructure.UUID.getValue()), data, DataStructure.daysToLive.getValue());
        System.out.println("0. Logout 1. Update data");
    }
}
