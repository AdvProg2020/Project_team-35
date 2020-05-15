package Controller.Exceptions;

public class ThisAttributeIsNotForThisProduct extends  Exception {
    private int id;
    public ThisAttributeIsNotForThisProduct(String message,int id) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
