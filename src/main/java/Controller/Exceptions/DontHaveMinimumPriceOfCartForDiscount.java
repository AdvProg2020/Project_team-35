package Controller.Exceptions;

public class DontHaveMinimumPriceOfCartForDiscount extends Exception {
    public DontHaveMinimumPriceOfCartForDiscount(String message) {
        super(message);
    }
}
