package Controller.Exceptions;

public class SellerShouldJustBe extends Exception {
    private int  id ;

    public SellerShouldJustBe(String message, int id) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
