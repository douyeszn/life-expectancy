import java.util.Scanner;
public class InputValidator {
    public static int validateInteger(Scanner scanner){
        int input;
        do{
            try{
                input = scanner.nextInt();
                scanner.nextLine();
                return input;
            }catch(Exception e){
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            }
        }while(true);
    }
}
