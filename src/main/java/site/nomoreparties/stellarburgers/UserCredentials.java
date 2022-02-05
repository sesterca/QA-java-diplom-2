package site.nomoreparties.stellarburgers;

public class UserCredentials {

    public String email;
    public String password;
    public String name;

    public UserCredentials(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public UserCredentials setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserCredentials setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserCredentials setName(String name) {
        this.name = name;
        return this;
    }

    public static UserCredentials from(User user){
        return new UserCredentials(user.getEmail(), user.getPassword());
    }
}
