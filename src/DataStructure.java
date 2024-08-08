public enum DataStructure {
    UUID(1),
    firstName(2),
    lastName(3),
    email(4),
    password(5),
    role(6),
    dateofBirth(7),
    countryISO(8),
    isHIVPositive(9),
    DiagnosisDate(10),
    onARTMed(11),
    startARTDate(12),
    daysToLive(13);

    private final int value;

    // Constructor to assign the integer value to each enum constant
    private DataStructure(int value) {
        this.value = value;
    }

    // Getter method to retrieve the integer value
    public int getValue() {
        return value;
    }
    // Static method to get an enum constant by its integer value
    public static DataStructure fromValue(int value) {
        for (DataStructure struct : DataStructure.values()) {
            if (struct.getValue() == value) {
                return struct;
            }
        }
        throw new IllegalArgumentException("No Status with value " + value);
    }
}
