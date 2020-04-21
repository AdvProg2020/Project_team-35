package Model;

public class AddProductRequest extends Request {
    private Product product;

    public AddProductRequest(StringBuilder request, RequestTypes requestTypes) {
        super(request, requestTypes);
    }

    public StringBuilder getDetails() {
        return null;
    }
    public void execute() {

    }
}
