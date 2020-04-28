package Controller.Exceptions;

public class LoginWithoutLogout extends Exception {
    public LoginWithoutLogout(String message) {
        super(message);
    }
}
