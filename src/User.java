public abstract class User {
    protected String email;
    protected String password;
    protected Role role;

    public User(String email, String password, Role role){
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public abstract void login();
}
