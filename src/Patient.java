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

}
