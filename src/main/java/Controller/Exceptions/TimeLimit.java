package Controller.Exceptions;

public class TimeLimit extends Exception {
    private int id;
    public TimeLimit(int id,String message) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}