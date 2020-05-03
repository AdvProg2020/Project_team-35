package Controller.Exceptions;

public class SoldProductsCanNotHaveChange extends Exception {
    private int id;
    public SoldProductsCanNotHaveChange(String message,int id) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
