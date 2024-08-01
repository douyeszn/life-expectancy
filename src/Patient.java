public class Patient extends User{
    public Patient(String email, String password) {
        super(null,null, email, password, Role.PATIENT);
    }

    public void login(){
        UserManager.login(this.email, this.password);
    }
}
