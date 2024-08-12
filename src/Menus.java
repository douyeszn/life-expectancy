import java.util.Scanner;

public class Menus {
    public static void homePage() {
        Utils.clearScreen();
        System.out.println("********* HIV Life Expectancy *********");
        System.out.println("1. Login");
        System.out.println("2. Complete Registration");
        System.out.println("0. Quit");
        System.out.println("***************************************");
        System.out.print("> ");
    }
    
    public static String loginPage(Scanner scanner){
        Utils.clearScreen();
        System.out.println("**************** Login ****************");
        System.out.print("Enter email > ");
        Credentials.email = scanner.nextLine();
        System.out.print("Enter password > ");
        Credentials.password = scanner.nextLine();
        System.out.println("***************************************");
        String data = User.login(Credentials.email, Credentials.password);
        return data;
    }

    public static void newRegistrationPage(Scanner scanner, Admin admin) {
        Utils.clearScreen();
        System.out.println("********** Register New User **********");
        System.out.print("Enter New User email > ");
        Credentials.email = scanner.nextLine();
        admin.newPatientReg(Credentials.email);
        System.out.println("***************************************");
    }

    public static void adminPage(Scanner scanner, Admin admin){
        String input;
        int choice;
        do{
            Utils.clearScreen();
            System.out.println("************* Admin Menu **************");
            System.out.println("1. Register New user");
            System.out.println("2. Register New Admin");
            System.out.println("3. Export data");
            System.out.println("0. Logout");
            System.out.println("***************************************");
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
            String format = "%-20s: %-30s%n";
            System.out.println("********************************** Edit data **********************************");
            System.out.printf(format, "1. Name", User.getDataField(data, DataStructure.firstName.getValue()) + " " + User.getDataField(data, DataStructure.lastName.getValue()));
            System.out.printf(format, "2. Date of Birth", User.getDataField(data, DataStructure.dateofBirth.getValue()));
            System.out.printf(format, "3. Country", User.getDataField(data, DataStructure.countryISO.getValue()));
            System.out.printf(format, "4. HIV Positive", User.getDataField(data, DataStructure.isHIVPositive.getValue()).equals("true") ? "Yes" : "No");
            System.out.printf(format, "5. Diagnosis Date", User.getDataField(data, DataStructure.DiagnosisDate.getValue()));
            System.out.printf(format, "6. On ART Medication", User.getDataField(data, DataStructure.onARTMed.getValue()).equals("true") ? "Yes" : "No");
            System.out.printf(format, "7. Start ART Date", User.getDataField(data, DataStructure.startARTDate.getValue()));
            System.out.println("*******************************************************************************");
            System.out.println("0. Back");
            System.out.print("> ");
            input = scanner.nextLine();
            choice = InputValidator.parseInteger(input);
            
        } while (choice != 0);
    }

    public static boolean completeRegPage(Scanner scanner) {
        Utils.clearScreen();
        int option = -1;
        System.out.println("********************************** Complete Registration **********************************");

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
                System.out.print("Are you HIV Positive? Press (1. Yes - 0. No) > ");
                String isHIV = scanner.nextLine();
                try {
                    option = Integer.parseInt(isHIV);
                    if (option != 1 && option != 0) {
                        System.out.println("Invalid input. Please enter 1 for Yes and 0 for No.");
                    } else if (option == 1) {
                        isHIVPositive = true;
                    } else {
                        isHIVPositive = false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number. (0 or 1)");
                    option = -1; // Set to an invalid number to continue the loop
                }
            } while (option != 1 && option != 0);

            // Check for ART
            if (isHIVPositive) {
                System.out.print("Enter date diagnosed (YYYY-MM-DD) > ");
                diagnosisDate = scanner.nextLine();
                do{
                    System.out.print("Are you on ART Medication? Press (1. Yes - 0. No) > ");
                    String onART = scanner.nextLine();
                    try {
                        option = Integer.parseInt(onART);
                        if (option != 1 && option != 0) {
                            System.out.println("Invalid input. Please enter 1 for Yes and 0 for No.");
                        } else if (option == 1) {
                            onARTMedication = true;
                        } else {
                            onARTMedication = false;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number. (0 or 1)");
                        option = -1;
                    }
    
                    if (onARTMedication) {
                        System.out.print("Enter ART start date (YYYY-MM-DD) > ");
                        startARTDate = scanner.nextLine();
                    } else {
                        startARTDate = "";
                    }

                } while (option != 1 && option != 0);

            }else{
                diagnosisDate = "";
            }

            System.out.print("Enter Country ISO Code (ABC) > ");
            String countryISO = scanner.nextLine();

            System.out.print("Enter new password > ");
            String password = scanner.nextLine();

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
        System.out.println("(\"*******************************************************************************");
        return true;
    }

    public static void exportDataPage(Scanner scanner, Admin admin){
        Utils.clearScreen();
        admin.downloadUsers();
        Utils.pause(1);
    }
}
