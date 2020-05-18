package Controller.Exceptions;

public class CategoryNull extends Exception{
    private int id;

    public int getId() {
        return id;
    }

    public CategoryNull(String message, int id) {
        super(message);
        this.id = id;
    }
}
