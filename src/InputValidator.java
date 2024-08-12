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
}

