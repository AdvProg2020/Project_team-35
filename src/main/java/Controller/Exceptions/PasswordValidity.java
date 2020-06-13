package Controller.Exceptions;

public class PasswordValidity extends Exception {
    private int id;

    public int getId() {
        return id;
    }

    public PasswordValidity(String message,int id) {
        super(message);
        this.id = id;
    }
}
