package Controller.Exceptions;

public class LoginWithoutLogout extends Exception {
    private int id;

    public int getId() {
        return id;
    }

    public LoginWithoutLogout(String message,int id) {
        super(message);
        this.id = id;
    }
}
