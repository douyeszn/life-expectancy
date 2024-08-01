public abstract class User {
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;
    protected Role role;

    public User(String firstName, String lastName, String email, String password, Role role){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Role getRole(){
        return this.role;
    }

    public abstract void login();
}
