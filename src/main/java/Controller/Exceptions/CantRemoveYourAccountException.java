package Controller.Exceptions;

public class CantRemoveYourAccountException extends Exception {
    public CantRemoveYourAccountException(String message) {
        super(message);
    }
}
