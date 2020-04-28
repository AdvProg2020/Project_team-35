package Model;

public class AddProductRequest extends Request {
    private Product product;

    public AddProductRequest(Seller seller, Product product) {
        super(seller);
        this.product = product;
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
