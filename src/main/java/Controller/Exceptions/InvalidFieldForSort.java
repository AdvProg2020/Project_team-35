package Controller.Exceptions;

public class InvalidFieldForSort extends Exception {
    private int id;

    public InvalidFieldForSort(String message, int id) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
