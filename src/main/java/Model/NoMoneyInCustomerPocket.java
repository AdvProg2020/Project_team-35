package Model;

public class NoMoneyInCustomerPocket extends Exception {
    public NoMoneyInCustomerPocket(String message) {
        super(message);
    }
}
