package Controller.Exceptions;

public class InvalidNumber extends Exception {
    private int id;
    public InvalidNumber(String message,int id) {
        super(message);
        this.id = id ;
    }

    public int getId() {
        return id;
    }
}
