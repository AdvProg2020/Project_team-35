package Controller;

public class JustOneOffForEveryProduct extends Exception {
    private int id;

    public JustOneOffForEveryProduct(String message, int id) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
