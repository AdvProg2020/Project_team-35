package Model;

public class AddOffRequest extends Request {
    private Off off;

    public AddOffRequest(StringBuilder request, RequestTypes requestTypes, Account requester, Off off) {
        super(request, requestTypes, requester);
        this.off = off;
    }

    public StringBuilder getDetails() {
        return null;
    }
    public void execute() {

    }
}
