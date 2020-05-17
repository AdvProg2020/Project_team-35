package Controller.Exceptions;

public class ThereIsNotCategoryWithNameException extends Exception {
    private int id;
    public ThereIsNotCategoryWithNameException(String message,int id) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
