package Model;

public class AddProductRequest extends Request {
    private Product product;

    public AddProductRequest(StringBuilder request, RequestTypes requestTypes, Account requester) {
        super(request, requestTypes, requester);
    }

    public StringBuilder getDetails() {
        return null;
    }
    public void execute() {

    }
}
