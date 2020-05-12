package Controller.Exceptions;

public class InputStringExceptNumber extends Exception {
    private int id;
    public InputStringExceptNumber(String message,int id) {
        super(message);
        this.id = id ;
    }

    public int getId() {
        return id;
    }
}
