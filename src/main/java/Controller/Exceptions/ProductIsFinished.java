package Controller.Exceptions;

public class ProductIsFinished extends Exception {
    private int id;
    public ProductIsFinished(int id,String message) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
