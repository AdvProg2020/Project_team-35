package Controller.Exceptions;

public class NoMatchBetweenCategoryAndAttributes extends Exception {
    private int id;
    public NoMatchBetweenCategoryAndAttributes(String message,int id) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

