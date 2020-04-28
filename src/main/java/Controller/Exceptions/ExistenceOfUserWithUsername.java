package Controller.Exceptions;

public class ExistenceOfUserWithUsername extends Exception {
    public ExistenceOfUserWithUsername(String message) {
        super(message);
    }
}
