public abstract class User {
    private String email;
    private String password;
    private Role role;

    public User(String email, String password, Role role){
        this.email = email;
        this.password = password;
        this.role = role;
    }


}
