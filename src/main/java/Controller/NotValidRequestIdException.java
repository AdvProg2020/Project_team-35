package Controller;

public class NotValidRequestIdException extends Exception {
    public NotValidRequestIdException(String message) {
        super(message);
    }
}
