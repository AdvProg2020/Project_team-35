package Controller.Exceptions;

public class ThisOffNotExist extends Exception{
    private int id;
    public ThisOffNotExist(String message,int id) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
