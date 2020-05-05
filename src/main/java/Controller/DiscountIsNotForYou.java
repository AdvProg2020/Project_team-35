package Controller;

public class DiscountIsNotForYou extends Exception {
    private int id;
    public DiscountIsNotForYou(int id,String message) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
