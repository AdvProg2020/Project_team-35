package Controller;

public class CantRemoveYourAccountException extends Exception {
    public CantRemoveYourAccountException(String message) {
        super(message);
    }
}
