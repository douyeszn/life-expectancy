import java.util.Scanner;
import java.io.Console;
public class Menus {
    public static void homePage() {
        Utils.clearScreen();
        System.out.println("************************* HIV Life Expectancy *************************");
        System.out.println("1. Login");
        System.out.println("2. Complete Registration");
        System.out.println("0. Quit");
        System.out.println("***********************************************************************");
        System.out.print("> ");
    }
    
    public static String loginPage(Scanner scanner){
        Utils.clearScreen();
        System.out.println("******************************** Login ********************************");
        System.out.print("Enter email > ");
        Credentials.email = scanner.nextLine();
        Console console = System.console();
        char[] passwordChars = console.readPassword("Enter password > ");
        Credentials.password = new String(passwordChars);
        System.out.println("***********************************************************************");
        String data = User.login(Credentials.email, Credentials.password);
        return data;
    }

    public static void newRegistrationPage(Scanner scanner, Admin admin) {
        Utils.clearScreen();
        System.out.println("************************** Register New User **************************");
        System.out.print("Enter New User email > ");
        Credentials.email = scanner.nextLine();
        admin.newPatientReg(Credentials.email);
        System.out.println("***********************************************************************");
    }

    public static void adminPage(Scanner scanner, Admin admin){
        String input;
        int choice;
        do{
            Utils.clearScreen();
            System.out.println("***************************** Admin Menu ******************************");
            System.out.println("1. Register New user");
            System.out.println("2. Register New Admin");
            System.out.println("3. Export data");
            System.out.println("0. Logout");
            System.out.println("***********************************************************************");
            System.out.print("> ");
            input = scanner.nextLine();
            choice = InputValidator.parseInteger(input);
            switch (choice) {
                case 1:
                    newRegistrationPage(scanner, admin);
                    break;
                case 2:
                    break;
                case 3:
                    exportDataPage(scanner, admin);
                    break;
                case 0:
                    System.out.println("Logging out...");
                    Utils.pause(1);
                    break;
                default:
                    System.out.println("Enter a valid option");
                    Utils.pause(1);
                    continue;
                }
            }while(choice != 0);
        }
        
        public static void patientPage(Scanner scanner, String data, Patient patient) {
            int choice;
            String input;
            do{
                Utils.clearScreen();
                patient.displayPatientInfo(data);
                input = scanner.nextLine();
                choice = InputValidator.parseInteger(input);
                switch (choice) {
                    case 1: 
                        patientEditPage(scanner, data, patient);
                        break;
                    case 0:
                        System.out.println("Logging out...");
                        Utils.pause(1);
                        break;
                    default:
                        System.out.println("Enter a valid option");
                        Utils.pause(1);
                        continue;
            }
        }while (choice != 0);
    }
    
    public static void patientEditPage(Scanner scanner, String data, Patient patient){
        String input;
        int choice;
        do{
            Utils.clearScreen();
            String format = "%-30s: %-30s%n";
            System.out.println("**************************** Edit data ****************************");
            System.out.printf(format, "1. Name", User.getDataField(data, DataStructure.firstName.getValue()) + " " + User.getDataField(data, DataStructure.lastName.getValue()));
            System.out.printf(format, "2. Date of Birth", User.getDataField(data, DataStructure.dateofBirth.getValue()));
            System.out.printf(format, "3. Country", User.getDataField(data, DataStructure.countryISO.getValue()));
            System.out.printf(format, "4. HIV Status and Date", 
                                User.getDataField(data, DataStructure.isHIVPositive.getValue()).equals("true") ? "Yes | " + 
                                User.getDataField(data, DataStructure.DiagnosisDate.getValue()): "No"
                                );
            System.out.printf(format, "5. ART Status and Date",
                                User.getDataField(data, DataStructure.onARTMed.getValue()).equals("true") ? "Yes | " +
                                User.getDataField(data, DataStructure.startARTDate.getValue()): "No");
            System.out.println("*******************************************************************");
            System.out.println("Select field to edit (1-5)\t0. Back");
            System.out.print("> ");
            input = scanner.nextLine();
            choice = InputValidator.parseInteger(input);
            
            switch (choice) {
                case 1:
                    editNamePage(scanner, patient);
                    break;
                case 2:
                    editDOBPage(scanner, patient);
                    break;
                case 3:
                    editCountryPage(scanner, patient);
                    break;
                case 4:
                    editHIVStatusPage(scanner, patient);
                    break;
                case 5:
                    editARTStatusPage(scanner, patient);
                    break;
                default:
                    System.out.println("invalid choice");
                    break;
            }
        } while (choice != 0);
    }

    public static void editNamePage(Scanner scanner, Patient patient){
        String firstname;
        String lastname;
        do{
            Utils.clearScreen();
            System.out.println("**************************** Edit Name ****************************");

            System.out.print("Firstname > ");
            firstname = scanner.nextLine();
            System.out.println("Lastname > ");
            lastname = scanner.nextLine();

            if(
                User.updateDataField("user-store.txt", patient.getUUID(), firstname, DataStructure.firstName.getValue()) &&
                User.updateDataField("user-store.txt", patient.getUUID(), lastname, DataStructure.lastName.getValue())
                ){
                System.out.println("Update successful");
                Utils.pause(1);
                break;
            }else{
                System.out.println("update failed");
                Utils.pause(1);
            }
        }while (true);
        
    }

    public static void editDOBPage(Scanner scanner, Patient patient){
        String input;
        do{
            Utils.clearScreen();
            System.out.println("***************************** Edit DOB *****************************");
            System.out.print("New date of Birth (YYYY-MM-DD) > ");
            input = scanner.nextLine();
            if ("0".equals(input)) {
                System.out.println("Exiting the update.");
                return;
            }
            String date = InputValidator.parseDate(input);
            if(User.updateDataField("user-store.txt", patient.getUUID(), date, DataStructure.dateofBirth.getValue())){
                System.out.println("Update successful");
                Utils.pause(1);
                break;
            }else{
                System.out.println("update failed");
                Utils.pause(1);
            }
        }while (true);
    }

    public static void editCountryPage(Scanner scanner, Patient patient){
        String input;
        do{
            Utils.clearScreen();
            System.out.println("*************************** Edit Country ***************************");
            System.out.print("New Country ISO (eg USA or US) > ");
            input = scanner.nextLine();
            if ("0".equals(input)) {
                System.out.println("Exiting the update.");
                return;
            }
            String iso = InputValidator.parseISO(input);
            if(User.updateDataField("user-store.txt", patient.getUUID(), iso, DataStructure.countryISO.getValue())){
                System.out.println("Update successful");
                Utils.pause(1);
                break;
            }else{
                System.out.println("update failed");
                Utils.pause(1);
            }
        }while (true);
    }

    public static void editHIVStatusPage(Scanner scanner, Patient patient){
        String input;
        do{
            Utils.clearScreen();
            System.out.println("************************** Edit HIV Status **************************");
            System.out.print("Are you HIV Positive? \n1. Yes\n2. No\n> ");
            input = scanner.nextLine();
            if ("0".equals(input)) {
                System.out.println("Exiting the update.");
                return;
            }
            int choice = InputValidator.parseInteger(input);
            if(choice == 1){
                if(User.updateDataField("user-store.txt", patient.getUUID(), "true", DataStructure.isHIVPositive.getValue())){
                    System.out.println("Update successful");
                    Utils.pause(1);
                    editHIVDatePage(scanner, patient);
                    break;
                }else{
                    System.out.println("update failed");
                    Utils.pause(1);
                }
            }else if (choice == 2) {
                if(User.updateDataField("user-store.txt", patient.getUUID(), "false", DataStructure.isHIVPositive.getValue())){
                    User.updateDataField("user-store.txt", patient.getUUID(), "", DataStructure.DiagnosisDate.getValue());
                    System.out.println("Update successful");
                    Utils.pause(1);
                    break;
                }else{
                    System.out.println("update failed");
                    Utils.pause(1);
                }
                
            }else{
                System.out.println("Enter a valid option");
            }
        }while (true);
    }

    public static void editARTStatusPage(Scanner scanner, Patient patient){
        String input;
        do{
            Utils.clearScreen();
            System.out.println("************************** Edit ART Status **************************");
            System.out.print("Are you on ART Medication? \n1. Yes\n2. No\n> ");
            input = scanner.nextLine();
            if ("0".equals(input)) {
                System.out.println("Exiting the update.");
                return;
            }
            int choice = InputValidator.parseInteger(input);
            if(choice == 1){
                if(User.updateDataField("user-store.txt", patient.getUUID(), "true", DataStructure.onARTMed.getValue())){
                    System.out.println("Update successful");
                    Utils.pause(1);
                    editARTDatePage(scanner, patient);
                    break;
                }else{
                    System.out.println("update failed");
                    Utils.pause(1);
                }
            }else if (choice == 2) {
                if(User.updateDataField("user-store.txt", patient.getUUID(), "false", DataStructure.onARTMed.getValue())){
                    User.updateDataField("user-store.txt", patient.getUUID(), "", DataStructure.startARTDate.getValue());
                    System.out.println("Update successful");
                    Utils.pause(1);
                    break;
                }else{
                    System.out.println("update failed");
                    Utils.pause(1);
                }
                
            }else{
                System.out.println("Enter a valid option");
            }
        }while (true);
    }

    public static void editHIVDatePage(Scanner scanner, Patient patient){
        String input;
        do{
            Utils.clearScreen();
            System.out.println("***************************** HIV Date *****************************");
            System.out.print("Enter HIV Diagnosis date (YYYY-MM-DD) > ");
            input = scanner.nextLine();
            if ("0".equals(input)) {
                System.out.println("Exiting the update.");
                return;
            }
            String date = InputValidator.parseDate(input);
            if(User.updateDataField("user-store.txt", patient.getUUID(), date, DataStructure.DiagnosisDate.getValue())){
                System.out.println("Update successful");
                Utils.pause(1);
                break;
            }else{
                System.out.println("update failed");
                Utils.pause(1);
            }
        }while (true);
    }

    public static void editARTDatePage(Scanner scanner, Patient patient){
        String input;
        do{
            Utils.clearScreen();
            System.out.println("***************************** ART Date *****************************");
            System.out.print("Enter ART Start date (YYYY-MM-DD) > ");
            input = scanner.nextLine();
            if ("0".equals(input)) {
                System.out.println("Exiting the update.");
                return;
            }
            String date = InputValidator.parseDate(input);
            if(User.updateDataField("user-store.txt", patient.getUUID(), date, DataStructure.startARTDate.getValue())){
                System.out.println("Update successful");
                Utils.pause(1);
                break;
            }else{
                System.out.println("update failed");
                Utils.pause(1);
            }
        }while (true);
    }

    public static boolean completeRegPage(Scanner scanner) {
        Utils.clearScreen();
        int option = -1;
        System.out.println("************************ Complete Registration ************************");

        System.out.print("Enter UUID > ");
        String uuid = scanner.nextLine();

        boolean isHIVPositive = false;
        boolean onARTMedication = false;

        String diagnosisDate = "";
        String startARTDate = "";

        Boolean isUser = User.findUser(uuid);
        if (isUser) {
            System.out.print("Enter Firstname > ");
            String firstName = scanner.nextLine();

            System.out.print("Enter Lastname > ");
            String lastName = scanner.nextLine();

            System.out.print("Enter Date of Birth (YYYY-MM-DD) > ");
            String dateOfBirth = scanner.nextLine();

            // Check for HIV
            do {
                System.out.print("Are you HIV Positive? Press (1. Yes - 2. No) > ");
                String isHIV = scanner.nextLine();
                try {
                    option = Integer.parseInt(isHIV);
                    if (option != 1 && option != 2) {
                        System.out.println("Invalid input. Please enter 1 for Yes and 2 for No.");
                    } else if (option == 1) {
                        isHIVPositive = true;
                    } else {
                        isHIVPositive = false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number. (1 or 2)");
                    option = -1; // Set to an invalid number to continue the loop
                }
            } while (option != 1 && option != 2);

            // Check for ART
            if (isHIVPositive) {
                System.out.print("Enter date diagnosed (YYYY-MM-DD) > ");
                diagnosisDate = scanner.nextLine();
                do{
                    System.out.print("Are you on ART Medication? Press (1. Yes - 2. No) > ");
                    String onART = scanner.nextLine();
                    try {
                        option = Integer.parseInt(onART);
                        if (option != 1 && option != 2) {
                            System.out.println("Invalid input. Please enter 1 for Yes and 2 for No.");
                        } else if (option == 1) {
                            onARTMedication = true;
                        } else {
                            onARTMedication = false;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number. (1 or 2)");
                        option = -1;
                    }
    
                    if (onARTMedication) {
                        System.out.print("Enter ART start date (YYYY-MM-DD) > ");
                        startARTDate = scanner.nextLine();
                    } else {
                        startARTDate = "";
                    }

                } while (option != 1 && option != 2);

            }else{
                diagnosisDate = "";
            }

            System.out.print("Enter Country ISO Code (ABC) > ");
            String countryISO = scanner.nextLine();

            Console console = System.console();
            char[] passwordChars;
            String password;
            do {
                // Prompt for the first password
                passwordChars = console.readPassword("Enter password > ");
                String password1 = new String(passwordChars);
                
                // Prompt for the second password
                passwordChars = console.readPassword("Enter password again > ");
                String password2 = new String(passwordChars);
                
                // Compare the two passwords
                if (password1.equals(password2)) {
                    password = password1;
                    System.out.println("Password successfully set.");
                    Utils.pause(1);
                    break;
                } else {
                    System.out.println("Password mismatch. Please try again.");
                }
                
            } while (true);

            boolean regStatus = Patient.completeRegistration(
                uuid, // UUID remains the same
                firstName,
                lastName,
                password,
                dateOfBirth,
                countryISO,
                isHIVPositive,
                diagnosisDate,
                onARTMedication,
                startARTDate
            );

            if(regStatus != true) {
                System.out.println("Registration failed");
                return false;
            }else{
                System.out.println("Registration complete!");
            }
        } else {
            System.out.println("User does not exist.");
            return false;
        }
        System.out.println("***********************************************************************");
        Utils.pause(1);
        return true;
    }

    public static void exportDataPage(Scanner scanner, Admin admin) {
        int choice = -1;
        do{
            Utils.clearScreen();
            System.out.println("************************** Export Data Menu ***************************");
            System.out.println("1. Export Users Data");
            System.out.println("2. Export User Analytics");
            System.out.println("0. Exit");
            System.out.println("***********************************************************************");
            System.out.print("> ");

            String input = scanner.nextLine();
            choice = InputValidator.parseInteger(input);
            switch (choice) {
                case 1:
                    admin.downloadUsers();
                    Utils.pause(2);
                    break;
                case 2:
                    admin.exportStats();
                    Utils.pause(2);
                    break;
                default:
                    System.out.println("Invalid option selected. Please try again.");
                    Utils.pause(1);
                    break;
            }

        } while(choice != 0);

    }

}
