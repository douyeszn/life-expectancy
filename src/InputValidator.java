import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputValidator {
    public static int parseInteger(String input) {
        int option = -1;
        try {
            option = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
            Utils.pause(1);
        }
        return option;
    }

    public static String parseDate(String date) {
        LocalDate date_ok;
        String dateStr = "";
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date_ok = LocalDate.parse(date, formatter);
            dateStr = date_ok.toString();
            if(date_ok.isAfter(LocalDate.now())){
                System.out.println("Date can't be a future date");
            }
        } catch (DateTimeParseException e){
            System.out.println("Invalid Date format, try YYYY-MM-DD" + e);
        }
        return dateStr;
    }
}

