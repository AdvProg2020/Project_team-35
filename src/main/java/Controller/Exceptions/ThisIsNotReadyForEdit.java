package Controller.Exceptions;

public class ThisIsNotReadyForEdit extends Exception{
    private int id;


    public ThisIsNotReadyForEdit(String message, int id) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
