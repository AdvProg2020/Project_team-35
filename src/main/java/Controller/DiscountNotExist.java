package Controller;

public class DiscountNotExist extends Exception {
    private int id;
    public DiscountNotExist(int id,String message) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
