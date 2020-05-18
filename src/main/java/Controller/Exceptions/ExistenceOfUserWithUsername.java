package Controller.Exceptions;

public class ExistenceOfUserWithUsername extends Exception {
    private int id;

    public int getId() {
        return id;
    }

    public ExistenceOfUserWithUsername(String message, int id) {
        super(message);
        this.id = id;
    }
}
