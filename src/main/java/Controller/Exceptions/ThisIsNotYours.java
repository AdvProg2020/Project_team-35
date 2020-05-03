package Controller.Exceptions;

public class ThisIsNotYours extends Exception {
    private int id;
    public ThisIsNotYours(String message,int id) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
