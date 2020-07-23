package Controller;

public class NotEnoughMoney extends Exception{
    public NotEnoughMoney(String message) {
        super(message);
    }
}
