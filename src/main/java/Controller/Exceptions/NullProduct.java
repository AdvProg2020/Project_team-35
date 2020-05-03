package Controller.Exceptions;

public class NullProduct extends Exception {
    private  int id;
    public NullProduct(String message,int id) {
        super(message);
        this.id= id;
    }

    public int getId() {
        return id;
    }
}
