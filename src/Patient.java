public class Patient extends User{
    private String dateOfBirth;
    private String countryISOcode;
    private boolean isHIVPositive;
    private String hivDiagnosisDate;
    private boolean onARTMedication;
    private String startARTDate;

    // public Patient(String email, String password) {
    //     super(null,null, email, password);
    // }

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
            // Skip UUID as it's typically not updated
            if (field == DataStructure.UUID) continue;

            // Retrieve the position and corresponding data
            int position = field.getValue();
            String data = dataFields[position - 1]; // dataFields is zero-based

            // Update the field in the CSV file
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

}
