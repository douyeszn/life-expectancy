public class Patient extends User{
    public Patient(String email, String password) {
        super(null, null, email, password);
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
    }
    
    
    

}
