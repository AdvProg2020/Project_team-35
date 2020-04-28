package Model;

public class AddOffRequest extends Request {
    private Off off;

    public AddOffRequest(Seller seller, Off off) {
        super(seller);
        this.off = off;
    }

    public String getDetails() {
        return null;
    }
    public void execute() {

    }
    public String getRequestInfo() {
        return null;
    }
}
