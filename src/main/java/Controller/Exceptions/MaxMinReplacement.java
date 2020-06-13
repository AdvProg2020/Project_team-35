package Controller.Exceptions;

public class MaxMinReplacement extends Exception {
    private int id;

    public int getId() {
        return id;
    }

    public MaxMinReplacement(String message, int id) {
        super(message);
        this.id = id;
    }
}
