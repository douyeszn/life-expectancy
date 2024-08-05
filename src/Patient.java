public class Patient extends User{
    private String dateOfBirth;
    private String countryISOcode;
    private boolean isHIVPositive;
    private String hivDiagnosisDate;
    private boolean onARTMedication;
    private String startARTDate;

    public Patient(String email, String password) {
        super(null,null, email, password, Role.PATIENT);
    }

    public Patient(
            String email,
            String password,
            String dateOfBirth,
            String countryISOcode,
            boolean isHIVPositive,
            String hivDiagnosisDate,
            boolean onARTMedication,
            String startARTDate) {
        super(null, null, email, password, Role.PATIENT);
        this.dateOfBirth = dateOfBirth;
        this.countryISOcode = countryISOcode;
        this.isHIVPositive = isHIVPositive;
        this.hivDiagnosisDate = hivDiagnosisDate;
        this.onARTMedication = onARTMedication;
        this.startARTDate = startARTDate;
    }

    public boolean login(){
         return UserManager.login(this.email, this.password, Role.PATIENT);
    }
}
