import java.util.HashMap;
import java.util.Map;

public class UserMap {
    private final Map<String, String> userMap;

    public UserMap(String csvString) {
        userMap = new HashMap<>();
        parseCSV(csvString);
    }

    private void parseCSV(String csvString) {
        String[] data = csvString.split(",");

        userMap.put("id", data[0]);
        userMap.put("firstName", data[1]);
        userMap.put("lastName", data[2]);
        userMap.put("email", data[3]);
        userMap.put("password", data[4]);
        userMap.put("role", data[5]);
        userMap.put("dateOfBirth", data[6]);
        userMap.put("countryISO", data[7]);
        userMap.put("isHIVPositive", data[8]);
        userMap.put("DiagnosisDate", data[9]);
        userMap.put("onARTMedication", data[10]);
        userMap.put("startARTDate", data[11]);
        userMap.put("daysToLive", data[12]);
    }

    public Map<String, String> getUserMap() {
        return userMap;
    }
}
