package Controller;

public class NotValidUserNameException extends Exception {
    public NotValidUserNameException(String message) {
        super(message);
    }
}
