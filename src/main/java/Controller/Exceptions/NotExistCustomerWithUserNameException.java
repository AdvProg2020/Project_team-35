package Controller.Exceptions;

public class NotExistCustomerWithUserNameException extends Exception {
    public NotExistCustomerWithUserNameException(String message) {
        super(message);
    }
}
