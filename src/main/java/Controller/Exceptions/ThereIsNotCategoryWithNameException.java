package Controller.Exceptions;

public class ThereIsNotCategoryWithNameException extends Exception {
    public ThereIsNotCategoryWithNameException(String message) {
        super(message);
    }
}
